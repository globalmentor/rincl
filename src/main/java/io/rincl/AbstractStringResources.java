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

import java.util.Optional;

import javax.annotation.*;

/**
 * Abstract implementation of access to i18n resources for which the underlying storage is based on strings.
 * <p>
 * This class retrieves all resources as stored in string format based upon {@link #getStringImpl(String)}.
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
	 * This implementation parses the value using {@link Boolean#valueOf(String)}.
	 * </p>
	 */
	@Override
	public Optional<Boolean> getOptionalBoolean(final String key) throws ResourceConfigurationException {
		return getDereferencedString(key).map(Boolean::valueOf)
				//delegate TODO switch to Optional.or() in Java 9: http://hg.openjdk.java.net/jdk9/dev/jdk/rev/423df075cf72
				.map(Optional::of).orElseGet(() -> getParentResources().flatMap(resources -> resources.getOptionalBoolean(key)));
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
			return getDereferencedString(key).map(Double::valueOf)
					//delegate TODO switch to Optional.or() in Java 9: http://hg.openjdk.java.net/jdk9/dev/jdk/rev/423df075cf72
					.map(Optional::of).orElseGet(() -> getParentResources().flatMap(resources -> resources.getOptionalDouble(key)));
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
			return getDereferencedString(key).map(Integer::valueOf)
					//delegate TODO switch to Optional.or() in Java 9: http://hg.openjdk.java.net/jdk9/dev/jdk/rev/423df075cf72
					.map(Optional::of).orElseGet(() -> getParentResources().flatMap(resources -> resources.getOptionalInt(key)));
		} catch(final NumberFormatException numberFormatException) {
			throw new ResourceConfigurationException(numberFormatException);
		}
	}
}
