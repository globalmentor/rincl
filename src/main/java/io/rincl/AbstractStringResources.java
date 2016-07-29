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

import static com.globalmentor.util.Optionals.*;

import java.net.URI;
import java.util.Optional;

import javax.annotation.*;

/**
 * Abstract implementation of access to i18n resources for which the underlying storage is based on strings.
 * <p>
 * This class retrieves all resources as stored in string format based upon {@link #getOptionalStringImpl(String)}.
 * </p>
 * @author Garret Wilson
 */
public abstract class AbstractStringResources extends BaseResources {

	/**
	 * Context class constructor.
	 * @param contextClass The context with which these resources are related; usually the class of the object requesting the resource.
	 * @param parentResources The parent resources for fallback lookup.
	 * @throws NullPointerException if the given context class and/or parent resources is <code>null</code>.
	 */
	public AbstractStringResources(@Nonnull final Class<?> contextClass, @Nonnull final Optional<Resources> parentResources) {
		super(contextClass, parentResources);
	}

	/**
	 * {@inheritDoc}
	 * <p>
	 * This implementation delegates to {@link #getOptionalStringImpl(String)}.
	 * </p>
	 */
	@Override
	public boolean hasResource(@Nonnull final String key) throws ResourceConfigurationException {
		return getOptionalStringImpl(key).isPresent();
	}

	/**
	 * {@inheritDoc}
	 * <p>
	 * This implementation parses the value using {@link Boolean#valueOf(String)}.
	 * </p>
	 */
	@Override
	public Optional<Boolean> getOptionalBoolean(final String key) throws ResourceConfigurationException {
		return or(getOptionalDereferencedString(key).map(Boolean::valueOf), () -> getParentResources().flatMap(resources -> resources.getOptionalBoolean(key)));
	}

	/**
	 * {@inheritDoc}
	 * <p>
	 * This implementation parses the value using {@link Double#valueOf(String)}.
	 * </p>
	 */
	@Override
	public Optional<Double> getOptionalDouble(final String key) throws ResourceConfigurationException {
		try {
			return or(getOptionalDereferencedString(key).map(Double::valueOf), () -> getParentResources().flatMap(resources -> resources.getOptionalDouble(key)));
		} catch(final NumberFormatException numberFormatException) {
			throw new ResourceConfigurationException(numberFormatException);
		}
	}

	/**
	 * {@inheritDoc}
	 * <p>
	 * This implementation parses the value using {@link Integer#valueOf(String)}.
	 * </p>
	 */
	@Override
	public Optional<Integer> getOptionalInt(final String key) throws ResourceConfigurationException {
		try {
			return or(getOptionalDereferencedString(key).map(Integer::valueOf), () -> getParentResources().flatMap(resources -> resources.getOptionalInt(key)));
		} catch(final NumberFormatException numberFormatException) {
			throw new ResourceConfigurationException(numberFormatException);
		}
	}

	/**
	 * {@inheritDoc}
	 * <p>
	 * This implementation parses the value using {@link Long#valueOf(long)}.
	 * </p>
	 */
	@Override
	public Optional<Long> getOptionalLong(final String key) throws ResourceConfigurationException {
		try {
			return or(getOptionalDereferencedString(key).map(Long::valueOf), () -> getParentResources().flatMap(resources -> resources.getOptionalLong(key)));
		} catch(final NumberFormatException numberFormatException) {
			throw new ResourceConfigurationException(numberFormatException);
		}
	}

	/**
	 * {@inheritDoc}
	 * <p>
	 * This implementation parses the value using {@link URI#create(String)}.
	 * </p>
	 */
	@Override
	public Optional<URI> getOptionalUri(final String key) throws ResourceConfigurationException {
		try {
			return or(getOptionalDereferencedString(key).map(URI::create), () -> getParentResources().flatMap(resources -> resources.getOptionalUri(key)));
		} catch(final IllegalArgumentException illegalArgumentException) {
			throw new ResourceConfigurationException(illegalArgumentException);
		}
	}

}
