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

import java.util.Optional;

import javax.annotation.*;

/**
 * Abstract implementation of access to i18n resources.
 * @author Garret Wilson
 */
public abstract class AbstractResources implements Resources {

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
	 * Context class and parent resources constructor.
	 * @param contextClass The context with which these resources are related; usually the class of the object requesting the resource.
	 * @param parentResources The parent resources for fallback lookup.
	 * @throws NullPointerException if the given context class and/or parent resources is <code>null</code>.
	 */
	public AbstractResources(@Nonnull final Class<?> contextClass, @Nonnull final Optional<Resources> parentResources) {
		this.contextClass = requireNonNull(contextClass);
		this.parentResources = requireNonNull(parentResources);
	}

}
