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
import java.util.Locale.Category;

import javax.annotation.*;

/**
 * Abstract base class to facilitate implementing the concern for internationalization of resources.
 * <p>
 * This class provides an implementation of {@link #getLocale(Locale.Category)} and {@link #setLocale(Locale)}.
 * </p>
 * @author Garret Wilson
 */
public abstract class BaseResourceI18nConcern extends AbstractResourceI18nConcern {

	private final LocaleSelection localeSelection = new LocaleSelection();

	/**
	 * Constructor.
	 * @param parentResourcesFactory The strategy for creating parent resources for a particular context and locale.
	 * @throws NullPointerException if the given parent resources factory is <code>null</code>.
	 */
	public BaseResourceI18nConcern(@Nonnull final ResourcesFactory parentResourcesFactory) {
		super(parentResourcesFactory);
	}

	@Override
	public Locale getLocale(final Category category) {
		return localeSelection.getLocale(category);
	}

	@Override
	public void setLocale(final Category category, final Locale locale) {
		localeSelection.setLocale(category, locale);
	}

}
