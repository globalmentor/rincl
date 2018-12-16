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

import io.confound.config.ConfigurationException;

/**
 * Creates resources for a given context and locale.
 * @author Garret Wilson
 */
@FunctionalInterface
public interface ResourcesFactory {

	/** Resources factory that returns no resources, and when resources are required returns an empty resources instance. */
	public static final ResourcesFactory NONE = (contextClass, locale) -> Optional.empty();

	/**
	 * Retrieves resources related to a specified locale for the given context.
	 * <p>
	 * The context class returned by {@link Resources#getContextClass()} may not necessarily reflect the class of the context provided here.
	 * </p>
	 * <p>
	 * The default implementation delegates to {@link #getOptionalResources(Object, Locale)}.
	 * </p>
	 * <p>
	 * If no resources are available, empty resources will be returned.
	 * </p>
	 * @param context The context with which these resources are related; usually the object requesting the resource.
	 * @param locale The locale for which resources should be returned for the given context.
	 * @return Access to configured resources for the given context.
	 * @throws NullPointerException if the given context and/or locale is <code>null</code>.
	 * @throws ConfigurationException if there is a configuration error.
	 * @see #getResources(Class, Locale)
	 */
	public default @Nonnull Resources getResources(@Nonnull final Object context, @Nonnull final Locale locale) throws ConfigurationException {
		return getOptionalResources(context, locale).orElseGet(() -> new EmptyResources(context.getClass()));
	}

	/**
	 * Retrieves resources related to a specified locale for the given context class.
	 * <p>
	 * The context class returned by {@link Resources#getContextClass()} may not necessarily be the context class provided here.
	 * </p>
	 * <p>
	 * If no resources are available, empty resources will be returned.
	 * </p>
	 * @param contextClass The context with which these resources are related; usually the class of the object requesting the resource.
	 * @param locale The locale for which resources should be returned for the given context class.
	 * @return Access to configured resources for the given context class.
	 * @throws NullPointerException if the given context class and/or locale is <code>null</code>.
	 * @throws ConfigurationException if there is a configuration error.
	 */
	public default @Nonnull Resources getResources(@Nonnull final Class<?> contextClass, @Nonnull final Locale locale) throws ConfigurationException {
		return getOptionalResources(contextClass, locale).orElseGet(() -> new EmptyResources(contextClass));
	}

	/**
	 * Retrieves resources related to a specified locale for the given context.
	 * <p>
	 * The context class returned by {@link Resources#getContextClass()} may not necessarily reflect the class of the context provided here.
	 * </p>
	 * <p>
	 * The default implementation delegates to {@link #getOptionalResources(Class, Locale)} providing the class of the given instance. Implementations of this
	 * interface should if at all possible implement the class-based resource lookup method variations and leave these default delegating method implementations
	 * so as to provide the most flexibility to consumers.
	 * </p>
	 * @param context The context with which these resources are related; usually the object requesting the resource.
	 * @param locale The locale for which resources should be returned for the given context.
	 * @return Access to configured resources for the given context.
	 * @throws NullPointerException if the given context and/or locale is <code>null</code>.
	 * @throws ConfigurationException if there is a configuration error.
	 * @see #getResources(Class, Locale)
	 */
	public default @Nonnull Optional<Resources> getOptionalResources(@Nonnull final Object context, @Nonnull final Locale locale) throws ConfigurationException {
		return getOptionalResources(context.getClass(), locale);
	}

	/**
	 * Retrieves resources related to a specified locale for the given context class.
	 * <p>
	 * The context class returned by {@link Resources#getContextClass()} may not necessarily be the context class provided here.
	 * </p>
	 * @param contextClass The context with which these resources are related; usually the class of the object requesting the resource.
	 * @param locale The locale for which resources should be returned for the given context class.
	 * @return Access to configured resources for the given context class.
	 * @throws NullPointerException if the given context class and/or locale is <code>null</code>.
	 * @throws ConfigurationException if there is a configuration error.
	 */
	public @Nonnull Optional<Resources> getOptionalResources(@Nonnull final Class<?> contextClass, @Nonnull final Locale locale) throws ConfigurationException;

}
