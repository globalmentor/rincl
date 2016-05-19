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

	//TODO finish to first look up from Csar
	//TODO implement setLocale(Locale) using Csar
	public static Locale getLocale() {
		//TODO see MessageFormat Locale lookup by category
		return Locale.getDefault(); //TODO fix
	}

	/**
	 * Returns the default resource i18n concern.
	 * @return The default resource i18n concern.
	 * @see Csar#getDefaultConcern(Class)
	 */
	public static ResourceI18nConcern getDefaultResourceI18nConcern() {
		return Csar.getDefaultConcern(ResourceI18nConcern.class);
	}

	/**
	 * Sets the default resource i18n concern.
	 * @param resourceI18nConcern The default resource i18n concern to set.
	 * @return The previous concern, or <code>null</code> if there was no previous concern.
	 * @throws NullPointerException if the given concern is <code>null</code>.
	 * @see Csar#registerDefaultConcern(Class, Concern)
	 */
	public static ResourceI18nConcern setDefaultResourceI18nConcern(@Nonnull final ResourceI18nConcern resourceI18nConcern) {
		return Csar.registerDefaultConcern(ResourceI18nConcern.class, resourceI18nConcern);
	}

	/**
	 * Returns the configured resource i18n concern for the current context.
	 * @return The configured resource i18n concern for the current context.
	 * @see Csar#getConcern(Class)
	 */
	public static ResourceI18nConcern getResourceI18nConcern() {
		return Csar.getConcern(ResourceI18nConcern.class);
	}

}
