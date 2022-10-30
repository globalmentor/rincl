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

import java.util.*;

import javax.annotation.*;

import io.confound.config.ConfigurationException;
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
public interface ResourceI18nConcern extends Concern, LocaleSelectable, ResourcesFactory {

	@Override
	public default Class<ResourceI18nConcern> getConcernType() {
		return ResourceI18nConcern.class;
	}

	/**
	 * Retrieves resources for the given context.
	 * <p>
	 * The context class returned by {@link Resources#getContextClass()} may not necessarily reflect the class of the context provided here.
	 * </p>
	 * <p>
	 * The default implementation uses the locale returned by {@link #getLocale(Locale.Category)} for {@link Locale.Category#DISPLAY}. Implementations of this
	 * interface should if at all possible implement the class-based resource lookup method variations and leave these default delegating method implementations
	 * so as to provide the most flexibility to consumers.
	 * </p>
	 * @param context The context with which these resources are related; usually the object requesting the resource.
	 * @return Access to configured resources for the given context.
	 * @throws NullPointerException if the given context is <code>null</code>.
	 * @throws ConfigurationException if there is a configuration error.
	 * @see #getResources(Class)
	 * @see #getResources(Object, Locale)
	 */
	public default @Nonnull Resources getResources(@Nonnull final Object context) throws ConfigurationException {
		return getResources(context, getLocale(Locale.Category.DISPLAY));
	}

	/**
	 * Retrieves resources for the given context class.
	 * <p>
	 * The context class returned by {@link Resources#getContextClass()} may not necessarily be the context class provided here.
	 * </p>
	 * <p>
	 * The default implementation uses the locale returned by {@link #getLocale(Locale.Category)} for {@link Locale.Category#DISPLAY}.
	 * </p>
	 * @param contextClass The context with which these resources are related; usually the class of the object requesting the resource.
	 * @return Access to configured resources for the given context class.
	 * @throws NullPointerException if the given context class is <code>null</code>.
	 * @throws ConfigurationException if there is a configuration error.
	 * @see #getResources(Class, Locale)
	 */
	public default @Nonnull Resources getResources(@Nonnull final Class<?> contextClass) throws ConfigurationException {
		return getResources(contextClass, getLocale(Locale.Category.DISPLAY));
	}

}
