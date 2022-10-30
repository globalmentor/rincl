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

package io.rincl;

import static java.util.Objects.*;

import java.util.Optional;

import javax.annotation.*;

import io.confound.config.ConfigurationException;
import io.confound.config.EmptyConfiguration;

/**
 * An implementation of resources that contains no definitions.
 * <p>
 * This implementation will automatically delegate to the parent resources, if any.
 * </p>
 * @author Garret Wilson
 */
public final class EmptyResources extends EmptyConfiguration implements Resources {

	private final Class<?> contextClass;

	@Override
	public Class<?> getContextClass() {
		return contextClass;
	}

	/**
	 * Context class constructor.
	 * @param contextClass The context with which these resources are related; usually the class of the object requesting the resource.
	 * @throws NullPointerException if the given context class is <code>null</code>.
	 */
	public EmptyResources(@Nonnull final Class<?> contextClass) {
		this.contextClass = requireNonNull(contextClass);
	}

	@Override
	public Optional<String> findString(final String key, final Object... arguments) throws ConfigurationException {
		return Optional.empty();
	}

}
