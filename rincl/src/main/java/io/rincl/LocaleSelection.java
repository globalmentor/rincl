/*
 * Copyright © 2016 GlobalMentor, Inc. <https://www.globalmentor.com/>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.rincl;

import java.lang.reflect.Array;
import java.util.*;

import javax.annotation.*;

/**
 * Storage implementation for all available locale categories.
 * @author Garret Wilson
 */
public class LocaleSelection implements LocaleSelectable {

	@SuppressWarnings("unchecked")
	private final Optional<Locale>[] locales = (Optional<Locale>[])Array.newInstance(Optional.class, Locale.Category.values().length);

	@Override
	public Locale getLocale(@Nonnull Locale.Category category) {
		return locales[category.ordinal()].orElse(Locale.getDefault(category));
	}

	@Override
	public void setLocale(@Nonnull Locale.Category category, @Nonnull Locale locale) {
		locales[category.ordinal()] = Optional.of(locale);
	}

	/** Constructor. */
	public LocaleSelection() {
		Arrays.fill(locales, Optional.empty());
	}

}
