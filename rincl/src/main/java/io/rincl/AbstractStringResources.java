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
import java.nio.file.Path;
import java.text.MessageFormat;
import java.util.Locale;
import java.util.Optional;

import javax.annotation.*;

import io.confound.config.*;

/**
 * Abstract implementation of access to i18n resources for which the underlying storage is based on strings.
 * <p>
 * As with the parent class, an implementing subclass must override {@link #findConfigurationValueImpl(String)} for local raw string retrieval. This class
 * retrieves all values as stored in string format accessed via {@link #findConfigurationValueImpl(String)}, and afterwards dereferenced using
 * {@link #dereferenceString(String)}.
 * </p>
 * <p>
 * In addition this class overrides the {@link Optional}-returning access methods of {@link AbstractStringConfiguration} and, if no value is present, delegates
 * to {@link #getParentResources()} in order to implement fallback.
 * </p>
 * @author Garret Wilson
 */
public abstract class AbstractStringResources extends AbstractStringConfiguration implements Resources {

	private final Optional<Resources> parentResources;

	@Override
	public Optional<Resources> getParentResources() {
		return parentResources;
	}

	private final Class<?> contextClass;

	@Override
	public Class<?> getContextClass() {
		return contextClass;
	}

	/**
	 * Context class constructor.
	 * @param contextClass The context with which these resources are related; usually the class of the object requesting the resource.
	 * @param parentResources The parent resources for fallback lookup.
	 * @throws NullPointerException if the given context class and/or parent resources is <code>null</code>.
	 */
	public AbstractStringResources(@Nonnull final Class<?> contextClass, @Nonnull final Optional<Resources> parentResources) {
		this.contextClass = requireNonNull(contextClass);
		this.parentResources = requireNonNull(parentResources);
	}

	/**
	 * {@inheritDoc}
	 * <p>
	 * This implementation delegates to {@link #getOptionalStringImpl(String)}.
	 * </p>
	 */
	/*TODO delete
	@Override
	public boolean hasResource(@Nonnull final String key) throws ResourceConfigurationException {
		return getOptionalStringImpl(key).isPresent();
	}
	*/

	/**
	 * {@inheritDoc}
	 * @implSpec This implementation falls back to {@link #getParentResources()}, if any, if the requested value is not present.
	 */
	@Override
	public <P> Optional<P> getOptionalObject(String key) throws ConfigurationException {
		return or(super.getOptionalObject(key), () -> getParentResources().flatMap(resources -> resources.getOptionalObject(key)));
	}

	/**
	 * {@inheritDoc}
	 * @implSpec This implementation falls back to {@link #getParentResources()}, if any, if the requested value is not present.
	 */
	@Override
	public Optional<Boolean> getOptionalBoolean(final String key) throws ResourceConfigurationException {
		return or(super.getOptionalBoolean(key), () -> getParentResources().flatMap(resources -> resources.getOptionalBoolean(key)));
	}

	/**
	 * {@inheritDoc}
	 * @implSpec This implementation falls back to {@link #getParentResources()}, if any, if the requested value is not present.
	 */
	@Override
	public Optional<Double> getOptionalDouble(final String key) throws ResourceConfigurationException {
		return or(super.getOptionalDouble(key), () -> getParentResources().flatMap(resources -> resources.getOptionalDouble(key)));
	}

	/**
	 * {@inheritDoc}
	 * @implSpec This implementation falls back to {@link #getParentResources()}, if any, if the requested value is not present.
	 */
	@Override
	public Optional<Integer> getOptionalInt(final String key) throws ResourceConfigurationException {
		return or(super.getOptionalInt(key), () -> getParentResources().flatMap(resources -> resources.getOptionalInt(key)));
	}

	/**
	 * {@inheritDoc}
	 * @implSpec This implementation falls back to {@link #getParentResources()}, if any, if the requested value is not present.
	 */
	@Override
	public Optional<Long> getOptionalLong(final String key) throws ResourceConfigurationException {
		return or(super.getOptionalLong(key), () -> getParentResources().flatMap(resources -> resources.getOptionalLong(key)));
	}

	/**
	 * {@inheritDoc}
	 * @implSpec This implementation falls back to {@link #getParentResources()}, if any, if the requested value is not present.
	 */
	@Override
	public Optional<Path> getOptionalPath(final String key) throws ResourceConfigurationException {
		return or(super.getOptionalPath(key), () -> getParentResources().flatMap(resources -> resources.getOptionalPath(key)));
	}

	/**
	 * {@inheritDoc}
	 * @implSpec This implementation falls back to {@link #getParentResources()}, if any, if the requested value is not present.
	 */
	@Override
	public final Optional<String> getOptionalString(String key) throws ConfigurationException {
		return or(super.getOptionalString(key), () -> getParentResources().flatMap(resources -> resources.getOptionalString(key)));
	}

	/**
	 * {@inheritDoc}
	 * @implSpec This implementation formats the value, if any, retrieved from {@link #findConfigurationValue(String)}.
	 * @see #findConfigurationValue(String)
	 */
	@Override
	public final Optional<String> getOptionalString(final String key, final Object... arguments) throws ResourceConfigurationException { //TODO add tests, maybe in subclass
		Optional<String> string = findConfigurationValue(normalizeKey(key)); //get the dereferenced string
		if(string.isPresent()) { //if there is a string
			if(arguments.length > 0) { //if there are arguments, format the string
				//TODO improve source of MessageFormat; maybe use ThreadLocal
				//TODO switch to using ICU4J
				string = Optional.of(new MessageFormat(string.get(), Rincl.getLocale(Locale.Category.FORMAT)).format(arguments));
			}
		} else { //if there is no string, delegate to the parent resources
			string = getParentResources().flatMap(resources -> resources.getOptionalString(key, arguments));
		}
		return string;
	}

	/**
	 * {@inheritDoc}
	 * @implSpec This implementation falls back to {@link #getParentResources()}, if any, if the requested value is not present.
	 */
	@Override
	public Optional<URI> getOptionalUri(final String key) throws ResourceConfigurationException {
		return or(super.getOptionalUri(key), () -> getParentResources().flatMap(resources -> resources.getOptionalUri(key)));
	}

}
