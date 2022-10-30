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

package io.rincl.resourcebundle;

import static java.util.Objects.*;

import java.util.*;

import javax.annotation.Nonnull;

import io.confound.config.ConfigurationException;
import io.confound.config.Section;
import io.rincl.*;

/**
 * Access to i18n resources stored in a {@link ResourceBundle}.
 * @implSpec This implementation only supports resource bundles that natively store string objects.
 * @author Garret Wilson
 */
public class ResourceBundleResources extends AbstractStringResources {

	private final ResourceBundle resourceBundle;

	/** @return The resource bundle serving as the source of resources. */
	public ResourceBundle getResourceBundle() {
		return resourceBundle;
	}

	/**
	 * Context class and resource bundle constructor.
	 * @param contextClass The context with which these resources are related; usually the class the instance of which is requesting the resource.
	 * @param resourceBundle The resource bundle for which this object is an adaptor.
	 * @throws NullPointerException if the given context class and/or resource bundle is <code>null</code>.
	 */
	public ResourceBundleResources(@Nonnull final Class<?> contextClass, @Nonnull final ResourceBundle resourceBundle) {
		super(contextClass);
		this.resourceBundle = requireNonNull(resourceBundle);
	}

	/**
	 * {@inheritDoc}
	 * @implSpec This implementation always returns {@link Optional#empty()}, as resource bundles do not support sections.
	 */
	@Override
	public Optional<Section> findSection(final String key) throws ConfigurationException {
		return Optional.empty();
	}

	/**
	 * {@inheritDoc}
	 * @implSpec This implementation checks the resource bundle directly using {@link ResourceBundle#containsKey(String)}.
	 */
	@Override
	protected boolean hasConfigurationValueImpl(String key) throws ConfigurationException {
		return getResourceBundle().containsKey(key); //check the resource bundle directly
	}

	/**
	 * {@inheritDoc}
	 * @implSpec This implementation delegates to {@link ResourceBundle#getObject(String)}.
	 * @throws ConfigurationException if the requested resource is not an instance of {@link String}.
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
				throw new ConfigurationException(String.format("Resource with key %s is not a string.", key));
			}
			return Optional.of((String)object);
		} catch(final MissingResourceException missingResourceException) { //we don't expect this...
			return Optional.empty(); //...but it may not be impossible
		}
	}

}
