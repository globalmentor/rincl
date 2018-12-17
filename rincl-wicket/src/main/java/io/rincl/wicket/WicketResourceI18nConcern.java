/*
 * Copyright © 2016 GlobalMentor, Inc. <http://www.globalmentor.com/>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.rincl.wicket;

import static java.util.Objects.*;

import java.util.*;
import java.util.Locale.Category;

import javax.annotation.Nonnull;

import org.apache.wicket.*;

import io.confound.config.ConfigurationException;
import io.rincl.*;

/**
 * Resource internationalization concern that returns resources from a Wicket {@link Localizer}, with fallback to resources backed by a {@link ResourceBundle}.
 * <p>
 * This implementation uses the Wicket session locale and thus does not distinguish between {@link Category}. Because of limits in Wicket's locale storage, the
 * locale category will therefore be ignored when retrieving or setting a locale.
 * </p>
 * <p>
 * Retrieving and setting the current locale will adjust the Wicket session locale using {@link Session#getLocale()} and {@link Session#setLocale(Locale)}; the
 * current {@link Component} context, if any, will be ignored. However retrieving resources providing the context of a {@link Component} will provide resources
 * for locale of that component <em>at the time the resources were retrieved</em>. Following Rincl's design of fixed-locale {@link Resources}, the resources
 * locale will not dynamically change if the underlying {@link Component} changes its locale. Instead resources should be retrieved as needed rather than
 * keeping them for the life of the component.
 * </p>
 * @author Garret Wilson
 * @see WicketResources
 */
public class WicketResourceI18nConcern extends AbstractResourceI18nConcern {

	/** The current Wicket application, which will be empty if the application should be retrieved dynamically. */
	private final Optional<Application> application;

	/**
	 * Default constructor.
	 * <p>
	 * The Wicket application will be determined dynamically.
	 * </p>
	 */
	public WicketResourceI18nConcern() {
		this(ResourcesFactory.NONE);
	}

	/**
	 * Application constructor.
	 * @param application The Wicket application.
	 * @throws NullPointerException if the given application is <code>null</code>.
	 */
	public WicketResourceI18nConcern(@Nonnull final Application application) {
		this(ResourcesFactory.NONE, application);
	}

	/**
	 * Parent resources factory constructor.
	 * <p>
	 * The Wicket application will be determined dynamically.
	 * </p>
	 * @param parentResourcesFactory The strategy for creating parent resources for a particular context and locale.
	 * @throws NullPointerException if the given parent resources factory is <code>null</code>.
	 */
	public WicketResourceI18nConcern(@Nonnull final ResourcesFactory parentResourcesFactory) {
		super(parentResourcesFactory);
		application = Optional.empty();
	}

	/**
	 * Parent resources factory and application constructor.
	 * @param parentResourcesFactory The strategy for creating parent resources for a particular context and locale.
	 * @param application The Wicket application.
	 * @throws NullPointerException if the given parent resources factory and/or application is <code>null</code>.
	 */
	public WicketResourceI18nConcern(@Nonnull final ResourcesFactory parentResourcesFactory, @Nonnull final Application application) {
		super(parentResourcesFactory);
		this.application = Optional.of(application);
	}

	/**
	 * Retrieves the Wicket application.
	 * <p>
	 * If an application was provided in the constructor, that application will be given. Otherwise the application will be determined automatically.
	 * </p>
	 * @return The Wicket application.
	 * @see Application#get()
	 */
	protected Application getApplication() {
		return application.orElseGet(Application::get);
	}

	/** @return The current Wicket application localizer. */
	protected Localizer getLocalizer() {
		return getApplication().getResourceSettings().getLocalizer();
	}

	/**
	 * {@inheritDoc}
	 * <p>
	 * This implementation uses {@link Session#getLocale()} and therefore ignores the category passed.
	 * </p>
	 * @see Session#get()
	 */
	@Override
	public Locale getLocale(final Category category) {
		requireNonNull(category);
		return Session.get().getLocale();
	}

	/**
	 * {@inheritDoc}
	 * <p>
	 * This implementation uses {@link Session#setLocale(Locale)} and therefore ignores the category passed.
	 * </p>
	 * @see Session#get()
	 */
	@Override
	public void setLocale(final Category category, final Locale locale) {
		requireNonNull(category);
		Session.get().setLocale(locale);
	}

	/**
	 * {@inheritDoc}
	 * <p>
	 * This version uses the component's locale if a Wicket {@link Component} is supplied as the context.
	 * </p>
	 */
	@Override
	public Resources getResources(final Object context) throws ConfigurationException {
		if(context instanceof Component) { //if a Wicket Component is supplied, use its locale
			return getResources(context, ((Component)context).getLocale()); //return resources using the component's locale
		}
		return super.getResources(context); //otherwise retrieve the resources normally
	}

	/**
	 * {@inheritDoc}
	 * <p>
	 * If a Wicket {@link Component} is supplied as the context, this version passes it along to the resources.
	 * </p>
	 */
	@Override
	public Optional<Resources> getOptionalResources(final Object context, final Locale locale) throws ConfigurationException {
		if(context instanceof Component) { //if a Wicket Component is supplied, pass it to the resources
			return Optional.of(Resources.withFallback(new WicketResources((Component)context, getLocalizer(), locale),
					getParentResourcesFactory().getOptionalResources(context, locale)));
		}
		return super.getOptionalResources(context, locale); //otherwise retrieve the resources normally
	}

	@Override
	public Optional<Resources> getOptionalResources(final Class<?> contextClass, final Locale locale) throws ConfigurationException {
		return Optional.of(Resources.withFallback(new WicketResources(contextClass, getLocalizer(), locale),
				getParentResourcesFactory().getOptionalResources(contextClass, locale)));
	}

}
