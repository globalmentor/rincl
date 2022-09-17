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

import javax.annotation.*;

import org.apache.wicket.Component;
import org.apache.wicket.Localizer;
import org.apache.wicket.model.IModel;

import io.confound.config.ConfigurationException;
import io.confound.config.Section;
import io.rincl.*;

/**
 * Access to i18n resources using a Wicket {@link Localizer}.
 * <p>
 * Even though {@link Localizer} provides access to resources from arbitrary locales, in the Rincl model {@link Resources} provides resources to a single
 * locale. Thus this implementation uses its stored locale via {@link #getLocale()} to determine the resources to retrieve from the {@link Localizer}. Consumers
 * of this class should therefore retrieve new resources as needed as the {@link Resources} implementation will not change locale as dynamically.
 * </p>
 * @author Garret Wilson
 */
public class WicketResources extends AbstractStringResources {

	private final Localizer localizer;

	/** @return The Wicket localizer serving as the source of resources. */
	public Localizer getLocalizer() {
		return localizer;
	}

	private final Locale locale;

	/** @return The locale for which resources will be returned. */
	public Locale getLocale() {
		return locale;
	}

	private final Optional<Component> contextComponent;

	/** @return The Wicket component for which resources were requested. */
	public Optional<Component> getContextComponent() {
		return contextComponent;
	}

	/**
	 * Context component constructor.
	 * @param contextComponent The component with which these resources are related; usually the component which is requesting the resource.
	 * @param localizer The Wicket localizer serving as the source of resources for which this object is an adapter.
	 * @param locale The locale these resources are for.
	 * @throws NullPointerException if the given context component, localizer, and/or locale is <code>null</code>.
	 */
	public WicketResources(@Nonnull final Component contextComponent, @Nonnull final Localizer localizer, @Nonnull final Locale locale) {
		this(Optional.of(contextComponent), contextComponent.getClass(), localizer, locale);
	}

	/**
	 * Context class constructor.
	 * @param contextClass The context with which these resources are related; usually the class the instance of which is requesting the resource.
	 * @param localizer The Wicket localizer serving as the source of resources for which this object is an adapter.
	 * @param locale The locale these resources are for.
	 * @throws NullPointerException if the given context class, localizer, and/or locale is <code>null</code>.
	 */
	public WicketResources(@Nonnull final Class<?> contextClass, @Nonnull final Localizer localizer, @Nonnull final Locale locale) {
		this(Optional.empty(), contextClass, localizer, locale);
	}

	/**
	 * Context component and context class constructor.
	 * @param contextComponent The component with which these resources are related; usually the component which is requesting the resource.
	 * @param contextClass The context with which these resources are related; usually the class of the object requesting the resource.
	 * @param localizer The Wicket localizer serving as the source of resources for which this object is an adapter.
	 * @param locale The locale these resources are for.
	 * @throws NullPointerException if the given context component, context class, localizer, and/or locale is <code>null</code>.
	 */
	protected WicketResources(@Nonnull final Optional<Component> contextComponent, @Nonnull final Class<?> contextClass, @Nonnull final Localizer localizer,
			@Nonnull final Locale locale) {
		super(contextClass);
		this.localizer = requireNonNull(localizer);
		this.locale = requireNonNull(locale);
		this.contextComponent = requireNonNull(contextComponent);
	}

	/**
	 * {@inheritDoc}
	 * @implSpec This implementation always returns {@link Optional#empty()}, as the Wicket localizer does not support sections.
	 */
	@Override
	public Optional<Section> findSection(final String key) throws ConfigurationException {
		return Optional.empty();
	}

	/**
	 * {@inheritDoc}
	 * <p>
	 * This implementation delegates to {@link Localizer#getStringIgnoreSettings(String, Component, IModel, Locale, String, String)}.
	 * </p>
	 * @throws ConfigurationException if the requested resource is not an instance of {@link String}.
	 */
	@Override
	protected Optional<String> findConfigurationValueImpl(String key) throws ConfigurationException {
		return Optional.ofNullable(getLocalizer().getStringIgnoreSettings(requireNonNull(key), getContextComponent().orElse(null), null, getLocale(), null, null));
	}

}
