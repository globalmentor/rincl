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
 * Abstract base class to facilitate implementing the concern for internationalization of resources.
 * @author Garret Wilson
 */
public abstract class AbstractResourceI18nConcern implements ResourceI18nConcern {

	/**
	 * Retrieves parent resources for the given context.
	 * <p>
	 * This method should be used by subclass {@link #getResources(Class)} implementations to determine the parent resources to use.
	 * </p>
	 * <p>
	 * The default implementation returns an empty optional.
	 * </p>
	 * @param contextClass The context with which these resources are related; usually the class of the object requesting the resource.
	 * @return The resources for fallback resource resolution.
	 * @throws NullPointerException if the given context class is <code>null</code>.
	 * @throws ResourceConfigurationException if there is a configuration error.
	 */
	protected Optional<Resources> getParentResources(@Nonnull final Class<?> contextClass) throws ResourceConfigurationException {
		return Optional.empty();
	}

}
