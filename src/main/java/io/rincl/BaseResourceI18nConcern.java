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

import static java.util.Objects.*;

import java.util.*;

import javax.annotation.*;

/**
 * Abstract base class to facilitate implementing the concern for internationalization of resources.
 * <p>
 * This class provides an implementation of {@link #getLocale(Locale.Category)} and {@link #setLocale(Locale)}.
 * </p>
 * @author Garret Wilson
 */
public abstract class BaseResourceI18nConcern implements ResourceI18nConcern {

	@SuppressWarnings("unchecked")
	private final Optional<Locale>[] locales = new Optional[Locale.Category.values().length];

	@Override
	public Locale getLocale(@Nonnull Locale.Category category) {
		return locales[category.ordinal()].orElse(Locale.getDefault(category));
	}

	private final ResourcesFactory parentResourcesFactory;

	/** @return The strategy for creating parent resources for a particular context and locale. */
	protected ResourcesFactory getParentResourcesFactory() {
		return parentResourcesFactory;
	}

	/**
	 * Constructor.
	 * @param parentResourcesFactory The strategy for creating parent resources for a particular context and locale.
	 * @throws NullPointerException if the given parent resources factory is <code>null</code>.
	 */
	public BaseResourceI18nConcern(@Nonnull final ResourcesFactory parentResourcesFactory) {
		Arrays.fill(locales, Optional.empty());
		this.parentResourcesFactory = requireNonNull(parentResourcesFactory);
	}

	/**
	 * Configures the locale for this concern instance for the given locale category. Future calls to {@link #getLocale(Locale.Category)} will return the value
	 * set here.
	 * <p>
	 * This method does not modify the default JVM locale.
	 * </p>
	 * @param category The category for which the locale should be set..
	 * @param locale The new locale value.
	 * @throws NullPointerException if the given category and/or new locale is <code>null</code>.
	 * @see #getLocale(Locale.Category)
	 */
	@Override
	public void setLocale(@Nonnull Locale.Category category, @Nonnull Locale locale) {
		locales[category.ordinal()] = Optional.of(locale);
	}

}
