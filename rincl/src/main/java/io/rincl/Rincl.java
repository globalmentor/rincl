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

package io.rincl;

import java.util.*;

import javax.annotation.*;

import io.csar.*;
import io.rincl.Resources;

/**
 * The Resource I18n Concern Library (Rincl) facilitates internationalization by providing access to localization {@link Resources} via {@link Csar}.
 * <p>
 * Rincl uses the Csar {@link ConcernProvider} mechanism, so that an application can have access to a globally configured default Rincl implementation simply by
 * including that implementation's dependency. For example merely including the dependency
 * <code>io.rincl:rincl-resourcebundle-provider:<var>x</var>.<var>x</var>.<var>x</var></code> will automatically provide resources from resource bundle property
 * file lookup. Classes desiring resource access may then then implement {@link Rincled} for simplified retrieval of {@link Resources}.
 * </p>
 * <p>
 * More complex configuration may be done by manual configuration using {@link Rincl#setDefaultResourceI18nConcern(ResourceI18nConcern)} with the concern of
 * choice, as in the following example:
 * </p>
 * 
 * <pre>
 * {@code
 * Rincl.setDefaultResourceI18nConcern(new MyResourceI18nConcern());
 * }
 * </pre>
 * 
 * @author Garret Wilson
 * @see Csar
 */
public class Rincl {

	/**
	 * Returns the default resource i18n concern.
	 * @return The default resource i18n concern.
	 * @see Csar#getDefaultConcern(Class)
	 */
	public static Optional<ResourceI18nConcern> getDefaultResourceI18nConcern() {
		return Csar.getDefaultConcern(ResourceI18nConcern.class);
	}

	/**
	 * Sets the default resource i18n concern.
	 * @param resourceI18nConcern The default resource i18n concern to set.
	 * @return The previous concern, or <code>null</code> if there was no previous concern.
	 * @throws NullPointerException if the given concern is <code>null</code>.
	 * @see Csar#registerDefaultConcern(Class, Concern)
	 */
	public static Optional<ResourceI18nConcern> setDefaultResourceI18nConcern(@Nonnull final ResourceI18nConcern resourceI18nConcern) {
		return Csar.registerDefaultConcern(ResourceI18nConcern.class, resourceI18nConcern);
	}

	/**
	 * Returns the configured resource i18n concern for the current context.
	 * <p>
	 * If no resource i18n concern is registered for the current context, and no default resource i18n concern concern is registered, a resource i18n concern will
	 * be returned that provides empty resources.
	 * </p>
	 * @return The configured resource i18n concern for the current context.
	 * @see Csar#getConcern(Class)
	 * @see EmptyResourceI18nConcern#INSTANCE
	 */
	public static @Nonnull ResourceI18nConcern getResourceI18nConcern() {
		return Csar.getOptionalConcern(ResourceI18nConcern.class).orElse(EmptyResourceI18nConcern.INSTANCE);
	}

	/**
	 * Retrieves resources for the current context for the given context.
	 * <p>
	 * The context class returned by {@link Resources#getContextClass()} may not necessarily reflect the class of the context provided here.
	 * </p>
	 * <p>
	 * This is a convenience method that requests resources from the current context resource i18n concern.
	 * </p>
	 * @param context The context with which these resources are related; usually the object requesting the resource.
	 * @return Access to configured resources for the given context.
	 * @throws NullPointerException if the given context is <code>null</code>.
	 * @throws ResourceConfigurationException if there is a configuration error.
	 * @see #getResourceI18nConcern()
	 * @see ResourceI18nConcern#getResources(Object)
	 */
	public static @Nonnull Resources getResources(@Nonnull final Object context) throws ResourceConfigurationException {
		return getResourceI18nConcern().getResources(context);
	}

	/**
	 * Retrieves resources related to a specified locale for the current context for the given context.
	 * <p>
	 * The context class returned by {@link Resources#getContextClass()} may not necessarily reflect the class of the context provided here.
	 * </p>
	 * <p>
	 * This is a convenience method that requests resources from the current context resource i18n concern.
	 * </p>
	 * @param context The context with which these resources are related; usually the object requesting the resource.
	 * @param locale The locale for which resources should be returned for the given context.
	 * @return Access to configured resources for the given context.
	 * @throws NullPointerException if the given context and/or locale is <code>null</code>.
	 * @throws ResourceConfigurationException if there is a configuration error.
	 * @see #getResourceI18nConcern()
	 * @see ResourceI18nConcern#getResources(Object, Locale)
	 */
	public static @Nonnull Resources getResources(@Nonnull final Object context, @Nonnull final Locale locale) throws ResourceConfigurationException {
		return getResourceI18nConcern().getResources(context, locale);
	}

	/**
	 * Retrieves resources for the current context for the given context class.
	 * <p>
	 * The context class returned by {@link Resources#getContextClass()} may not necessarily be the context class provided here.
	 * </p>
	 * <p>
	 * This is a convenience method that requests resources from the current context resource i18n concern.
	 * </p>
	 * @param contextClass The context with which these resources are related; usually the class of the object requesting the resource.
	 * @return Access to configured resources for the given context class.
	 * @throws NullPointerException if the given context class is <code>null</code>.
	 * @throws ResourceConfigurationException if there is a configuration error.
	 * @see #getResourceI18nConcern()
	 * @see ResourceI18nConcern#getResources(Class)
	 */
	public static @Nonnull Resources getResources(@Nonnull final Class<?> contextClass) throws ResourceConfigurationException {
		return getResourceI18nConcern().getResources(contextClass);
	}

	/**
	 * Retrieves resources related to a specified locale for the current context for the given context class.
	 * <p>
	 * The context class returned by {@link Resources#getContextClass()} may not necessarily be the context class provided here.
	 * </p>
	 * <p>
	 * This is a convenience method that requests resources from the current context resource i18n concern.
	 * </p>
	 * @param contextClass The context with which these resources are related; usually the class of the object requesting the resource.
	 * @param locale The locale for which resources should be returned for the given context class.
	 * @return Access to configured resources for the given context class.
	 * @throws NullPointerException if the given context class and/or locale is <code>null</code>.
	 * @throws ResourceConfigurationException if there is a configuration error.
	 * @see #getResourceI18nConcern()
	 * @see ResourceI18nConcern#getResources(Class)
	 */
	public static @Nonnull Resources getResources(@Nonnull final Class<?> contextClass, @Nonnull final Locale locale) throws ResourceConfigurationException {
		return getResourceI18nConcern().getResources(contextClass, locale);
	}

	/**
	 * Retrieves the configured locale for the given category in the current context.
	 * <p>
	 * If no category has been configured for the concern context, or there is no configured {@link ResourceI18nConcern}, this method delegates to
	 * {@link Locale#getDefault(Locale.Category)}.
	 * </p>
	 * <p>
	 * This method may safely be used without configuring Rincl.
	 * </p>
	 * @param category The category of locale to return.
	 * @throws NullPointerException if the given category is <code>null</code>.
	 * @return The the configured locale for the given category.
	 * @see #getResourceI18nConcern()
	 * @see ResourceI18nConcern#getLocale(Locale.Category)
	 * @see Locale#getDefault(Locale.Category)
	 */
	public static Locale getLocale(@Nonnull Locale.Category category) {
		return Csar.getOptionalConcern(ResourceI18nConcern.class) //get the registered concern
				.map(concern -> concern.getLocale(category)) //return its locale
				.orElseGet(() -> Locale.getDefault(category)); //if there is no registered concern, return the default locale
	}

	/**
	 * Configures the locale for the current context for the given locale category. Future calls to {@link #getLocale(Locale.Category)} will return the value set
	 * here.
	 * <p>
	 * If the current context {@link ResourceI18nConcern} is the default, or there is no configured {@link ResourceI18nConcern}, this method updates the JVM
	 * default locale for the category using {@link Locale#setDefault(Locale.Category, Locale)}.
	 * </p>
	 * <p>
	 * This method may safely be used without configuring Rincl.
	 * </p>
	 * @param category The category for which the locale should be set.
	 * @param locale The new locale value.
	 * @throws SecurityException if a security manager exists and its {@link SecurityManager#checkPermission(java.security.Permission)} method doesn't allow the
	 *           operation.
	 * @throws NullPointerException if the given category and/or new locale is <code>null</code>.
	 * @see SecurityManager#checkPermission(java.security.Permission)
	 * @see PropertyPermission
	 * @see Locale#setDefault(Locale.Category, Locale)
	 * @see #getLocale(Locale.Category)
	 * 
	 */
	public static void setLocale(@Nonnull Locale.Category category, @Nonnull Locale locale) {
		final boolean setDefault; //whether we should also update the JVM default locale
		final Optional<ResourceI18nConcern> registeredResourceI18nConcern = Csar.getOptionalConcern(ResourceI18nConcern.class);
		if(registeredResourceI18nConcern.isPresent()) {
			final ResourceI18nConcern resourceI18nConcern = registeredResourceI18nConcern.get();
			resourceI18nConcern.setLocale(category, locale); //set the context locale
			//if the context concern is that registered as the default, update the JVM default locale as well
			final Optional<ResourceI18nConcern> registeredDefaultResourceI18nConcern = getDefaultResourceI18nConcern();
			setDefault = registeredDefaultResourceI18nConcern.isPresent() && registeredDefaultResourceI18nConcern.get() == resourceI18nConcern;
		} else {
			setDefault = true; //if Rincl isn't configured, updating the JVM default locale is all we can do
		}
		if(setDefault) {
			Locale.setDefault(category, locale);
		}
	}

	/**
	 * Configures the locale for the current context for all locale categories. Future calls to {@link #getLocale(Locale.Category)} will return the value set
	 * here.
	 * <p>
	 * If the current context {@link ResourceI18nConcern} is the default, or there is no configured {@link ResourceI18nConcern}, this method updates the JVM
	 * default locale using {@link Locale#setDefault(Locale)}.
	 * </p>
	 * <p>
	 * This method may safely be used without configuring Rincl.
	 * </p>
	 * @param locale The new locale value.
	 * @throws SecurityException if a security manager exists and its {@link SecurityManager#checkPermission(java.security.Permission)} method doesn't allow the
	 *           operation.
	 * @throws NullPointerException if the given category and/or new locale is <code>null</code>.
	 * @see SecurityManager#checkPermission(java.security.Permission)
	 * @see PropertyPermission
	 * @see Locale#setDefault(Locale)
	 * @see #getLocale(Locale.Category)
	 * 
	 */
	public static void setLocale(@Nonnull Locale locale) {
		final boolean setDefault; //whether we should also update the JVM default locale
		final Optional<ResourceI18nConcern> registeredResourceI18nConcern = Csar.getOptionalConcern(ResourceI18nConcern.class);
		if(registeredResourceI18nConcern.isPresent()) {
			final ResourceI18nConcern resourceI18nConcern = registeredResourceI18nConcern.get();
			resourceI18nConcern.setLocale(locale); //set the context locale
			//if the context concern is that registered as the default, update the JVM default locale as well
			final Optional<ResourceI18nConcern> registeredDefaultResourceI18nConcern = getDefaultResourceI18nConcern();
			setDefault = registeredDefaultResourceI18nConcern.isPresent() && registeredDefaultResourceI18nConcern.get() == resourceI18nConcern;
		} else {
			setDefault = true; //if Rincl isn't configured, updating the JVM default locale is all we can do
		}
		if(setDefault) {
			Locale.setDefault(locale);
		}
	}

}
