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

import java.util.Locale;

import javax.annotation.*;

/**
 * Allows selection of locale for locale category.
 * @author Garret Wilson
 */
public interface LocaleSelectable {

	/**
	 * Retrieves the configured locale for the given category.
	 * @param category The category of locale to return.
	 * @throws NullPointerException if the given category is <code>null</code>.
	 * @return The the configured locale for the given category.
	 * @see #setLocale(Locale.Category, Locale)
	 * @see Locale#getDefault(Locale.Category)
	 */
	public Locale getLocale(@Nonnull Locale.Category category);

	/**
	 * Configures the locale for the given locale category. Future calls to {@link #getLocale(Locale.Category)} will return the value set here.
	 * @param category The category for which the locale should be set.
	 * @param locale The new locale value.
	 * @throws NullPointerException if the given category and/or new locale is <code>null</code>.
	 * @see #getLocale(Locale.Category)
	 */
	public void setLocale(@Nonnull Locale.Category category, @Nonnull Locale locale);

	/**
	 * Configures the locale for all locale categories.
	 * <p>
	 * This is a convenience method to set all locale categories.
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

}
