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

import java.text.MessageFormat;
import java.util.*;

import javax.annotation.*;

import io.confound.config.Configuration;

/**
 * Access to i18n resources.
 * @author Garret Wilson
 */
public interface Resources extends Configuration {

	/** @return The context with which these resources are related; usually the class of the object requesting the resource. */
	public @Nonnull Class<?> getContextClass();

	/**
	 * Determines whether a resource of some type exists for the given resource key.
	 * <p>
	 * This method searches the parent resources hierarchy if no resource is available in this instance.
	 * </p>
	 * @param key The resource key.
	 * @return <code>true</code> if a resource of type type could be retrieved from these resources using the given key.
	 * @throws NullPointerException if the given key is <code>null</code>.
	 * @throws ResourceConfigurationException if there is a resource value stored in an invalid format.
	 */
	@Deprecated
	public default boolean hasResource(@Nonnull final String key) throws ResourceConfigurationException {
		return hasConfigurationValue(key);
	}

	/**
	 * Retrieves a general resource.
	 * @param <T> The type of resource expected.
	 * @param key The resource key.
	 * @return The value of the resource associated with the given key.
	 * @throws NullPointerException if the given key is <code>null</code>.
	 * @throws MissingResourceException if no resource is associated with the given key.
	 * @throws ResourceConfigurationException if there is a resource value stored in an invalid format.
	 */
	//	@Deprecated
	//	public default @Nonnull <T> T getResource(@Nonnull final String key) throws MissingResourceException, ResourceConfigurationException {
	//		return requireResource(getOptionalResource(key), key);
	//	}

	/**
	 * Retrieves a general resource that may not be present.
	 * @param <T> The type of resource expected.
	 * @param key The resource key.
	 * @return The optional value of the resource associated with the given key.
	 * @throws NullPointerException if the given key is <code>null</code>.
	 * @throws ResourceConfigurationException if there is a resource value stored in an invalid format.
	 */
	//	@Deprecated
	//	public default <T> Optional<T> getOptionalResource(@Nonnull final String key) throws ResourceConfigurationException {
	//		return getOptionalObject(key);
	//	}

	//String

	/**
	 * Retrieves a string resource.
	 * <p>
	 * TODO discuss dereferencing
	 * </p>
	 * <p>
	 * If arguments are provided, the string if present will be considered a template and formatted applying the given arguments. Formatting takes place after
	 * replacement of all internal resource references. The {@link MessageFormat} formatting rules will be used.
	 * </p>
	 * @param key The resource key.
	 * @param arguments The arguments for formatting, if any.
	 * @return The value of the resource associated with the given key.
	 * @throws NullPointerException if the given key is <code>null</code>.
	 * @throws MissingResourceException if no resource is associated with the given key.
	 * @throws ResourceConfigurationException if there is a resource value stored in an invalid format.
	 * @see MessageFormat#format(Object)
	 */
	public default @Nonnull String getString(@Nonnull final String key, @Nonnull final Object... arguments)
			throws MissingResourceException, ResourceConfigurationException {
		return requireConfiguration(getOptionalString(key, arguments), key);
	}

	/**
	 * Retrieves a string resource that may not be present.
	 * <p>
	 * TODO discuss dereferencing
	 * </p>
	 * <p>
	 * If arguments are provided, the string if present will be considered a template and formatted applying the given arguments. Formatting takes place after
	 * replacement of all internal resource references. The {@link MessageFormat} formatting rules will be used.
	 * </p>
	 * @param key The resource key.
	 * @param arguments The arguments for formatting, if any.
	 * @return The optional value of the resource associated with the given key.
	 * @throws NullPointerException if the given key is <code>null</code>.
	 * @throws ResourceConfigurationException if there is a resource value stored in an invalid format.
	 * @see MessageFormat#format(Object)
	 */
	public Optional<String> getOptionalString(@Nonnull final String key, @Nonnull final Object... arguments) throws ResourceConfigurationException;

	/**
	 * Return resources equivalent to these resources but that will fall back to optional parent resources if a value is not present. These resources will remain
	 * unmodified.
	 * @param fallbackResources The optional fallback resources.
	 * @return A version of these resources that uses fallback lookup or, if no fallback is present, these resources.
	 * @throws NullPointerException if the given optional fallback resources is <code>null</code>.
	 */
	public default Resources withFallbackResources(@Nonnull final Optional<Resources> fallbackResources) {
		return fallbackResources.isPresent() ? withFallbackResources(fallbackResources.get()) : this;
	}

	/**
	 * Returns resources equivalent to these resources but that will fall back to a specified parent resources if a value is not present. These resources will
	 * remain unmodified.
	 * @param fallbackResources The fallback resources.
	 * @return A version of these resources that uses fallback lookup.
	 * @throws NullPointerException if the given fallback resources is <code>null</code>.
	 */
	public default Resources withFallbackResources(@Nonnull final Resources fallbackResources) {
		return new ChildResourcesDecorator(this, fallbackResources);
	}

}
