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

package io.rincl.resourcebundle;

import static java.util.Objects.*;

import java.util.*;

import javax.annotation.Nonnull;

import io.confound.config.ConfigurationException;
import io.rincl.*;

/**
 * Access to i18n resources stored in a {@link ResourceBundle}.
 * @author Garret Wilson
 */
public class ResourceBundleResources extends AbstractStringResources {

	private final ResourceBundle resourceBundle;

	/** @return The resource bundle serving as the source of resources. */
	public ResourceBundle getResourceBundle() {
		return resourceBundle;
	}

	/**
	 * Context class constructor.
	 * @param contextClass The context with which these resources are related; usually the class the instance of which is requesting the resource.
	 * @param resourceBundle The resource bundle for which this object is an adaptor.
	 * @throws NullPointerException if the given context class and/or resource bundle is <code>null</code>.
	 */
	public ResourceBundleResources(@Nonnull final Class<?> contextClass, @Nonnull final ResourceBundle resourceBundle) {
		this(contextClass, Optional.empty(), resourceBundle);
	}

	/**
	 * Context class and parent resources constructor.
	 * @param contextClass The context with which these resources are related; usually the class of the object requesting the resource.
	 * @param parentResources The parent resources for fallback lookup.
	 * @param resourceBundle The resource bundle for which this object is an adaptor.
	 * @throws NullPointerException if the given context class, parent resources, and/or resource bundle is <code>null</code>.
	 */
	public ResourceBundleResources(@Nonnull final Class<?> contextClass, @Nonnull final Resources parentResources, @Nonnull final ResourceBundle resourceBundle) {
		this(contextClass, Optional.of(parentResources), resourceBundle);
	}

	/**
	 * Context class constructor.
	 * @param contextClass The context with which these resources are related; usually the class of the object requesting the resource.
	 * @param parentResources The parent resources for fallback lookup, or <code>null</code> if there is no parent resources.
	 * @param resourceBundle The resource bundle for which this object is an adaptor.
	 * @throws NullPointerException if the given context class, parent resources, and/or resource bundle is <code>null</code>.
	 */
	public ResourceBundleResources(@Nonnull final Class<?> contextClass, @Nonnull final Optional<Resources> parentResources,
			@Nonnull final ResourceBundle resourceBundle) {
		super(contextClass, parentResources);
		this.resourceBundle = requireNonNull(resourceBundle);
	}

	/**
	 * {@inheritDoc}
	 * <p>
	 * This implementation checks the resource bundle directly using {@link ResourceBundle#containsKey(String)}.
	 * </p>
	 */
	@Override
	public boolean hasConfigurationValue(String key) throws ConfigurationException {
		//check the resource bundle directly
		if(getResourceBundle().containsKey(key)) {
			return true;
		}
		//if the resource bundle doesn't have the resource, delegate the parent resources if any
		return getParentResources().map(parentResources -> parentResources.hasConfigurationValue(key)).orElse(false);
	}

	/**
	 * {@inheritDoc}
	 * @implSpec This implementation delegates to {@link ResourceBundle#getObject(String)}. If the returned object is a string, it is dereferenced.
	 * @implNote This implementation performs much of the same functionality as {@link #findConfigurationValueImpl(String)}, but is written to retrieve objects
	 *           directly from the resource bundle. With this implementation this method must perform its own fallback to any parent, as it does not rely on
	 *           {@link #findConfigurationValue(String)}.
	 * @implNote This implementation is somewhat incongruent with other "object" retrieval methods, all of which assume there the value is stored as a string to
	 *           be converted, making no allowance for retrieving the object directly.
	 * @see #dereferenceString(String)
	 */
	@Override
	public <T> Optional<T> getOptionalObject(final String key) throws ConfigurationException {
		final String normalizedKey = normalizeKey(key);
		final ResourceBundle resourceBundle = getResourceBundle();
		//See if the resource bundle contains the key;
		//otherwise, catching the exception and filling in the stack trace every time we need
		//simply to delegate to the parent resources afterwards causes too much overhead.
		if(resourceBundle.containsKey(normalizedKey)) {
			try {
				Object object = resourceBundle.getObject(normalizedKey); //get the object
				if(object instanceof String) { //if the object is a string, dereference it
					object = dereferenceString((String)object);
				}
				@SuppressWarnings("unchecked")
				final T typedObject = (T)object;
				return Optional.of(typedObject);
			} catch(final MissingResourceException missingResourceException) { //we don't expect this (because we checked up front)...
				//...but it may not be impossible, and perhaps means the resource has been removed; this is benign, so just fall back as normal (below)
			}
		}
		return getParentResources().flatMap(resources -> resources.getOptionalObject(key));
	}

	/**
	 * {@inheritDoc}
	 * @implSpec This implementation delegates to {@link ResourceBundle#getObject(String)}.
	 * @throws ResourceConfigurationException if the requested resource is not an instance of {@link String}.
	 */
	@Override
	protected Optional<String> findConfigurationValueImpl(final String key) throws ConfigurationException {
		final ResourceBundle resourceBundle = getResourceBundle();
		//See if the resource bundle contains the key;
		//otherwise, catching the exception and filling in the stack trace every time we need
		//simply to delegate to the parent resources afterwards causes too much overhead.
		if(!resourceBundle.containsKey(key)) {
			return Optional.empty();
		}
		try {
			//ResourceBundle.getString(String) merely delegates to the ResourceBundle.getObject(String) version,
			//so we'll do the same---but throw a better exception if the resource is not a string
			final Object object = resourceBundle.getObject(key); //get the object
			if(!(object instanceof String)) {
				throw new ResourceConfigurationException(String.format("Resource with key %s is not a string.", key));
			}
			return Optional.of((String)object);
		} catch(final MissingResourceException missingResourceException) { //we don't expect this...
			return Optional.empty(); //...but it may not be impossible
		}
	}

}
