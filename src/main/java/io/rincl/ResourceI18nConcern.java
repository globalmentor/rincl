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
 * The concern for internationalization of resources.
 * <p>
 * Once this concern is acquired, it provides access to a specific set of {@link Resources} for some <dfn>context class</dfn>, e.g. the class of the instance
 * requesting resources.
 * </p>
 * @author Garret Wilson
 * @see Csar
 */
public interface ResourceI18nConcern extends Concern {

	/**
	 * Retrieves the configured locale for the given category.
	 * <p>
	 * If no category has been configured for this concern instance, this method delegates to {@link Locale#getDefault(Locale.Category)}.
	 * </p>
	 * @param category The category of locale to return.
	 * @throws NullPointerException if the given category is <code>null</code>.
	 * @return The the configured locale for the given category.
	 * @see #setLocale(Locale.Category, Locale)
	 * @see Locale#getDefault(Locale.Category)
	 */
	public Locale getLocale(@Nonnull Locale.Category category);

	/**
	 * Configures the locale for this concern instance for the given locale category. Future calls to {@link #getLocale(Locale.Category)} will return the value
	 * set here.
	 * <p>
	 * This method does not modify the default JVM locale.
	 * </p>
	 * @param category The category for which the locale should be set.
	 * @param locale The new locale value.
	 * @throws NullPointerException if the given category and/or new locale is <code>null</code>.
	 * @see #getLocale(Locale.Category)
	 */
	public void setLocale(@Nonnull Locale.Category category, @Nonnull Locale locale);

	/**
	 * Configures the locale for this concern instance for all locale categories. Future calls to {@link #getLocale(Locale.Category)} will return the value set
	 * here.
	 * <p>
	 * This is a convenience method to set all locale categories.
	 * </p>
	 * <p>
	 * This method does not modify the default JVM locale.
	 * </p>
	 * @param locale The new locale value.
	 * @throws NullPointerException if the given category and/or new locale is <code>null</code>.
	 * @see #setLocale(Locale.Category, Locale)
	 * @see #getLocale(Locale.Category)
	 */
	public default void setLocale(@Nonnull Locale locale) {
		for(final Locale.Category category : Locale.Category.values()) {
			setLocale(category, locale);
		}
	}

	/**
	 * Retrieves resources for the given context.
	 * <p>
	 * The context class returned by {@link Resources#getContextClass()} may not necessarily be the context class provided here.
	 * </p>
	 * @param contextClass The context with which these resources are related; usually the class of the object requesting the resource.
	 * @return Access to configured resources for the given context class.
	 * @throws NullPointerException if the given context class is <code>null</code>.
	 * @throws ResourceConfigurationException if there is a configuration error.
	 */
	public Resources getResources(@Nonnull final Class<?> contextClass) throws ResourceConfigurationException;

}
