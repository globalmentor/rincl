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

import java.net.URI;
import java.util.Optional;

import javax.annotation.*;

/**
 * An implementation of resources that contains no definitions.
 * <p>
 * This implementation will automatically delegate to the parent resources, if any.
 * </p>
 * @author Garret Wilson
 */
public final class EmptyResources extends AbstractResources {

	/**
	 * Context class constructor.
	 * @param contextClass The context with which these resources are related; usually the class of the object requesting the resource.
	 * @throws NullPointerException if the given context class is <code>null</code>.
	 */
	public EmptyResources(@Nonnull final Class<?> contextClass) {
		this(contextClass, Optional.empty());
	}

	/**
	 * Context class and parent resources constructor.
	 * @param contextClass The context with which these resources are related; usually the class of the object requesting the resource.
	 * @param parentResources The parent resources for fallback lookup.
	 * @throws NullPointerException if the given context class and/or parent resources is <code>null</code>.
	 */
	public EmptyResources(@Nonnull final Class<?> contextClass, @Nonnull final Resources parentResources) {
		this(contextClass, Optional.of(parentResources));
	}

	/**
	 * Context class and optional parent resources constructor.
	 * @param contextClass The context with which these resources are related; usually the class of the object requesting the resource.
	 * @param parentResources The parent resources for fallback lookup.
	 * @throws NullPointerException if the given context class and/or parent resources is <code>null</code>.
	 */
	public EmptyResources(@Nonnull final Class<?> contextClass, @Nonnull final Optional<Resources> parentResources) {
		super(contextClass, parentResources);
	}

	@Override
	public boolean hasResource(String key) throws ResourceConfigurationException {
		return false;
	}

	@Override
	public <T> Optional<T> getOptionalResource(String key) throws ResourceConfigurationException {
		return getParentResources().flatMap(resources -> resources.getOptionalResource(key));
	}

	@Override
	public Optional<Double> getOptionalDouble(String key) throws ResourceConfigurationException {
		return getParentResources().flatMap(resources -> resources.getOptionalDouble(key));
	}

	@Override
	public Optional<Boolean> getOptionalBoolean(String key) throws ResourceConfigurationException {
		return getParentResources().flatMap(resources -> resources.getOptionalBoolean(key));
	}

	@Override
	public Optional<Integer> getOptionalInt(String key) throws ResourceConfigurationException {
		return getParentResources().flatMap(resources -> resources.getOptionalInt(key));
	}

	@Override
	public Optional<Long> getOptionalLong(String key) throws ResourceConfigurationException {
		return getParentResources().flatMap(resources -> resources.getOptionalLong(key));
	}

	@Override
	public Optional<String> getOptionalString(String key, Object... arguments) throws ResourceConfigurationException {
		return getParentResources().flatMap(resources -> resources.getOptionalString(key, arguments));
	}

	@Override
	public Optional<URI> getOptionalUri(String key) throws ResourceConfigurationException {
		return getParentResources().flatMap(resources -> resources.getOptionalUri(key));
	}

}
