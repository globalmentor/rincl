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

import java.net.URI;
import java.text.MessageFormat;
import java.util.*;

import javax.annotation.*;

/**
 * Access to i18n resources.
 * <p>
 * Each resource lookup method such as {@link #getString(String, Object...)} is expected to attempt to look up resources in the {@link #getParentResources()}
 * (if any) if the resource is not found in this resources instance.
 * </p>
 * @author Garret Wilson
 */
public interface Resources {

	/** @return The parent resources for fallback lookup. */
	public Optional<Resources> getParentResources();

	/** @return The context with which these resources are related; usually the class of the object requesting the resource. */
	public @Nonnull Class<?> getContextClass();

	/**
	 * Retrieves a required resource from an {@link Optional}, throwing a {@link MissingResourceException} if the resource not present.
	 * <p>
	 * This method is primarily used to check the result of a resource lookup call for the non-optional convenience resource lookup versions.
	 * </p>
	 * @param <T> The type of resource to check.
	 * @param resource The retrieved resource.
	 * @param key The resource key.
	 * @return The retrieved resource.
	 * @throws MissingResourceException if the given resource is not present.
	 * @see Optional#isPresent()
	 */
	public default <T> T requireResource(@Nonnull final Optional<T> resource, @Nonnull final String key) throws MissingResourceException {
		return resource.orElseThrow(() -> new MissingResourceException(String.format("Missing resource for key %s of class %s.", key, getContextClass().getName()),
				getContextClass().getName(), requireNonNull(key)));
	}

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
	public boolean hasResource(@Nonnull final String key) throws ResourceConfigurationException;

	/**
	 * Retrieves a general resource.
	 * @param <T> The type of resource expected.
	 * @param key The resource key.
	 * @return The value of the resource associated with the given key.
	 * @throws NullPointerException if the given key is <code>null</code>.
	 * @throws MissingResourceException if no resource is associated with the given key.
	 * @throws ResourceConfigurationException if there is a resource value stored in an invalid format.
	 */
	public default @Nonnull <T> T getResource(@Nonnull final String key) throws MissingResourceException, ResourceConfigurationException {
		return requireResource(getOptionalResource(key), key);
	}

	/**
	 * Retrieves a general resource that may not be present.
	 * @param <T> The type of resource expected.
	 * @param key The resource key.
	 * @return The optional value of the resource associated with the given key.
	 * @throws NullPointerException if the given key is <code>null</code>.
	 * @throws ResourceConfigurationException if there is a resource value stored in an invalid format.
	 */
	public <T> Optional<T> getOptionalResource(@Nonnull final String key) throws ResourceConfigurationException;

	//boolean

	/**
	 * Retrieves a Boolean resource.
	 * @param key The resource key.
	 * @return The value of the resource associated with the given key.
	 * @throws NullPointerException if the given key is <code>null</code>.
	 * @throws MissingResourceException if no resource is associated with the given key.
	 * @throws ResourceConfigurationException if there is a resource value stored in an invalid format.
	 */
	public default @Nonnull boolean getBoolean(@Nonnull final String key) throws MissingResourceException, ResourceConfigurationException {
		return requireResource(getOptionalBoolean(key), key).booleanValue();
	}

	/**
	 * Retrieves a Boolean resource that may not be present.
	 * @param key The resource key.
	 * @return The optional value of the resource associated with the given key.
	 * @throws NullPointerException if the given key is <code>null</code>.
	 * @throws ResourceConfigurationException if there is a resource value stored in an invalid format.
	 */
	public Optional<Boolean> getOptionalBoolean(@Nonnull final String key) throws ResourceConfigurationException;

	//double

	/**
	 * Retrieves a floating point resource.
	 * @param key The resource key.
	 * @return The value of the resource associated with the given key.
	 * @throws NullPointerException if the given key is <code>null</code>.
	 * @throws MissingResourceException if no resource is associated with the given key.
	 * @throws ResourceConfigurationException if there is a resource value stored in an invalid format.
	 */
	public default @Nonnull double getDouble(@Nonnull final String key) throws MissingResourceException, ResourceConfigurationException {
		return requireResource(getOptionalDouble(key), key).doubleValue();
	}

	/**
	 * Retrieves a floating point resource that may not be present.
	 * @param key The resource key.
	 * @return The optional value of the resource associated with the given key.
	 * @throws NullPointerException if the given key is <code>null</code>.
	 * @throws ResourceConfigurationException if there is a resource value stored in an invalid format.
	 */
	public Optional<Double> getOptionalDouble(@Nonnull final String key) throws ResourceConfigurationException;

	//int

	/**
	 * Retrieves an integer resource.
	 * @param key The resource key.
	 * @return The value of the resource associated with the given key.
	 * @throws NullPointerException if the given key is <code>null</code>.
	 * @throws MissingResourceException if no resource is associated with the given key.
	 * @throws ResourceConfigurationException if there is a resource value stored in an invalid format.
	 */
	public default @Nonnull int getInt(@Nonnull final String key) throws MissingResourceException, ResourceConfigurationException {
		return requireResource(getOptionalInt(key), key).intValue();
	}

	/**
	 * Retrieves an integer resource that may not be present.
	 * @param key The resource key.
	 * @return The optional value of the resource associated with the given key.
	 * @throws NullPointerException if the given key is <code>null</code>.
	 * @throws ResourceConfigurationException if there is a resource value stored in an invalid format.
	 */
	public Optional<Integer> getOptionalInt(@Nonnull final String key) throws ResourceConfigurationException;

	//long

	/**
	 * Retrieves a long integer resource.
	 * @param key The resource key.
	 * @return The value of the resource associated with the given key.
	 * @throws NullPointerException if the given key is <code>null</code>.
	 * @throws MissingResourceException if no resource is associated with the given key.
	 * @throws ResourceConfigurationException if there is a resource value stored in an invalid format.
	 */
	public default @Nonnull int getLong(@Nonnull final String key) throws MissingResourceException, ResourceConfigurationException {
		return requireResource(getOptionalLong(key), key).intValue();
	}

	/**
	 * Retrieves a long integer resource that may not be present.
	 * <p>
	 * The default implementation delegates to {@link #getOptionalInt(String)}.
	 * </p>
	 * @param key The resource key.
	 * @return The optional value of the resource associated with the given key.
	 * @throws NullPointerException if the given key is <code>null</code>.
	 * @throws ResourceConfigurationException if there is a resource value stored in an invalid format.
	 */
	public default Optional<Long> getOptionalLong(@Nonnull final String key) throws ResourceConfigurationException {
		return getOptionalInt(key).map(Integer::intValue).map(Long::valueOf); //this apparently uses auto-unboxing and autoboxing  
	}

	//TODO Path

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
		return requireResource(getOptionalString(key, arguments), key);
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

	//URI

	/**
	 * Retrieves a URI resource.
	 * @param key The resource key.
	 * @return The value of the resource associated with the given key.
	 * @throws NullPointerException if the given key is <code>null</code>.
	 * @throws MissingResourceException if no resource is associated with the given key.
	 * @throws ResourceConfigurationException if there is a resource value stored in an invalid format.
	 */
	public default @Nonnull URI getUri(@Nonnull final String key) throws MissingResourceException, ResourceConfigurationException {
		return requireResource(getOptionalUri(key), key);
	}

	/**
	 * Retrieves a URI resource that may not be present.
	 * @param key The resource key.
	 * @return The optional value of the resource associated with the given key.
	 * @throws NullPointerException if the given key is <code>null</code>.
	 * @throws ResourceConfigurationException if there is a resource value stored in an invalid format.
	 */
	public Optional<URI> getOptionalUri(@Nonnull final String key) throws ResourceConfigurationException;

}
