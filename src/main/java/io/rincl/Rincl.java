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

/**
 * The Resource I18n Concern Library (Rincl) facilitates internationalization by providing access to localization {@link Resources} via {@link Csar}.
 * <p>
 * Applications wishing to use Rincl should first configure a {@link ResourceI18nConcern}. The easiest way to do this is to configure a global fallback concern
 * using {@link #setDefaultResourceI18nConcern(ResourceI18nConcern)} with the concern of choice, as in the following example:
 * </p>
 * 
 * <pre>
 * {@code
 * Rincl.setDefaultResourceI18nConcern(ResourceBundleResourceI18nConcern.DEFAULT);
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
	 * @return The configured resource i18n concern for the current context.
	 * @throws ConcernNotFoundException if no resource i18n concern is configured.
	 * @see Csar#getConcern(Class)
	 */
	public static ResourceI18nConcern getResourceI18nConcern() {
		return Csar.getConcern(ResourceI18nConcern.class);
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
		try {
			return getResourceI18nConcern().getLocale(category);
		} catch(final ConcernNotFoundException concernNotFoundException) {
			return Locale.getDefault(category);
		}
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
		ResourceI18nConcern resourceI18nConcern = null;
		boolean setDefault = false; //whether we should also update the JVM default locale
		try {
			resourceI18nConcern = getResourceI18nConcern();
			//if the context concern is that registered as the default, update the JVM default locale as well
			final Optional<ResourceI18nConcern> defaultResourceI18nConcern = getDefaultResourceI18nConcern();
			setDefault = defaultResourceI18nConcern.isPresent() && defaultResourceI18nConcern.get() == resourceI18nConcern;
		} catch(final ConcernNotFoundException concernNotFoundException) {
			setDefault = true; //if Rincl isn't configured, updating the JVM default locale is all we can do
		}
		if(resourceI18nConcern != null) {
			resourceI18nConcern.setLocale(category, locale); //set the context locale
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
		ResourceI18nConcern resourceI18nConcern = null;
		boolean setDefault = false; //whether we should also update the JVM default locale
		try {
			resourceI18nConcern = getResourceI18nConcern();
			//if the context concern is that registered as the default, update the JVM default locale as well
			final Optional<ResourceI18nConcern> defaultResourceI18nConcern = getDefaultResourceI18nConcern();
			setDefault = defaultResourceI18nConcern.isPresent() && defaultResourceI18nConcern.get() == resourceI18nConcern;
		} catch(final ConcernNotFoundException concernNotFoundException) {
			setDefault = true; //if Rincl isn't configured, updating the JVM default locale is all we can do
		}
		if(resourceI18nConcern != null) {
			resourceI18nConcern.setLocale(locale); //set the context locale
		}
		if(setDefault) {
			Locale.setDefault(locale);
		}
	}

}
