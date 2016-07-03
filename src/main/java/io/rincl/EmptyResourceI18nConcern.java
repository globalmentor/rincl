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
import java.util.Locale.Category;

/**
 * Resource internationalization concern that returns an empty set of resources.
 * <p>
 * The locale retrieval and setting methods merely delegate to the JVM defaults.
 * </p>
 * <p>
 * This implementation is useful for use in an unconfigured context in which all calling code provides default resources in the case of a missing resource.
 * </p>
 * @author Garret Wilson
 * @see EmptyResources
 * @see Locale#getDefault(Category)
 * @see Locale#setDefault(Category, Locale)
 */
public class EmptyResourceI18nConcern implements ResourceI18nConcern {

	/** Singleton instance of the resource i18n concern returning empty resources. */
	public static final EmptyResourceI18nConcern INSTANCE = new EmptyResourceI18nConcern();

	/** This class cannot be publicly instantiated. */
	private EmptyResourceI18nConcern() {
	}

	/**
	 * {@inheritDoc}
	 * <p>
	 * This implementation delegates to {@link Locale#getDefault(Category)}.
	 * </p>
	 */
	@Override
	public Locale getLocale(final Category category) {
		return Locale.getDefault(category);
	}

	/**
	 * {@inheritDoc}
	 * <p>
	 * This implementation delegates to {@link Locale#setDefault(Category, Locale)}.
	 * </p>
	 */
	@Override
	public void setLocale(final Category category, final Locale locale) {
		Locale.setDefault(category, locale);
	}

	/**
	 * {@inheritDoc}
	 * <p>
	 * This implementation returns an instance of {@link EmptyResources}.
	 * </p>
	 * <p>
	 * TODO implement resources caching
	 * </p>
	 * @see EmptyResources
	 */
	@Override
	public Resources getResources(final Class<?> contextClass) throws ResourceConfigurationException {
		return new EmptyResources(contextClass);
	}

}
