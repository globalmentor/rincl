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

import java.text.MessageFormat;
import java.util.*;

import javax.annotation.*;

import io.confound.config.Configuration;
import io.confound.config.ConfigurationException;

/**
 * Access to i18n resources.
 * @implSpec For those methods that throw an exception if a resource is missing, the implementations in this interface throw a
 *           {@link MissingResourceKeyException}.
 * @author Garret Wilson
 * @see MissingResourceKeyException
 */
public interface Resources extends Configuration {

	@Override
	public default MissingResourceKeyException createMissingConfigurationKeyException(@Nonnull final String key) {
		return new MissingResourceKeyException(String.format("Missing resource for key %s.", key), requireNonNull(key));
	}

	/** @return The context with which these resources are related; usually the class of the object requesting the resource. */
	public @Nonnull Class<?> getContextClass();

	/**
	 * Determines whether a resource of some type exists for the given resource key.
	 * <p>
	 * This method should normally not be overridden or decorated.
	 * </p>
	 * @implSpec The default implementation delegates to {@link #hasConfigurationValue(String)}.
	 * @param key The resource key.
	 * @return <code>true</code> if a resource of type type could be retrieved from these resources using the given key.
	 * @throws NullPointerException if the given key is <code>null</code>.
	 * @throws ConfigurationException if there is a resource value stored in an invalid format.
	 */
	public default boolean hasResource(@Nonnull final String key) throws ConfigurationException {
		return hasConfigurationValue(key);
	}

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
	 * @throws MissingResourceKeyException if no resource is associated with the given key.
	 * @throws ConfigurationException if there is a resource value stored in an invalid format.
	 * @see MessageFormat#format(Object)
	 */
	public default @Nonnull String getString(@Nonnull final String key, @Nonnull final Object... arguments)
			throws MissingResourceKeyException, ConfigurationException {
		return requireConfiguration(findString(key, arguments), key);
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
	 * <p>
	 * This method should normally not be overridden or decorated.
	 * </p>
	 * @implSpec This implementation formats the value, if any, retrieved from {@link #findString(String)}.
	 * @param key The resource key.
	 * @param arguments The arguments for formatting, if any.
	 * @return The optional value of the resource associated with the given key.
	 * @throws NullPointerException if the given key is <code>null</code>.
	 * @throws ConfigurationException if there is a resource value stored in an invalid format.
	 * @see #findString(String)
	 * @see MessageFormat#format(Object)
	 */
	public default Optional<String> findString(@Nonnull final String key, @Nonnull final Object... arguments) throws ConfigurationException { //TODO add tests
		Optional<String> string = findString(key); //get the dereferenced string
		if(string.isPresent()) { //if there is a string
			if(arguments.length > 0) { //if there are arguments, format the string
				//TODO improve source of MessageFormat; maybe use ThreadLocal
				//TODO switch to using ICU4J
				string = Optional.of(new MessageFormat(string.get(), Rincl.getLocale(Locale.Category.FORMAT)).format(arguments));
			}
		}
		return string;
	}

	/**
	 * Returns resources equivalent to these resources but that will fall back to a specified parent resources if a value is not present. These resources will
	 * remain unmodified.
	 * @param fallbackResources The fallback resources.
	 * @return A version of these resources that uses fallback lookup.
	 * @throws NullPointerException if the given fallback resources is <code>null</code>.
	 */
	public default Resources withFallback(@Nonnull final Resources fallbackResources) {
		return new ChildResourcesDecorator(this, fallbackResources);
	}

	/**
	 * Utility method that return resources equivalent to the given resources but that will fall back to optional parent resources if a value is not present. The
	 * given resources will remain
	 * @param resources The resource to optionally be given a fallback.
	 * @param fallbackResources The optional fallback resources.
	 * @return A version of the resources that uses fallback lookup or, if no fallback is present, the given resources.
	 * @throws NullPointerException if the given resources and/or optional fallback resources is <code>null</code>.
	 * @see #withFallback(Resources)
	 */
	public static Resources withFallback(@Nonnull final Resources resources, @Nonnull final Optional<Resources> fallbackResources) {
		return fallbackResources.map(resources::withFallback).orElse(resources);
	}

}
