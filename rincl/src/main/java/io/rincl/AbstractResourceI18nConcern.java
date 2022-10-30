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

import javax.annotation.*;

/**
 * Abstract class to facilitate implementing the concern for internationalization of resources.
 * @author Garret Wilson
 */
public abstract class AbstractResourceI18nConcern implements ResourceI18nConcern {

	private final ResourcesFactory parentResourcesFactory;

	/** @return The strategy for creating parent resources for a particular context and locale. */
	protected ResourcesFactory getParentResourcesFactory() {
		return parentResourcesFactory;
	}

	/**
	 * Constructor.
	 * @param parentResourcesFactory The strategy for creating parent resources for a particular context and locale.
	 * @throws NullPointerException if the given parent resources factory is <code>null</code>.
	 */
	public AbstractResourceI18nConcern(@Nonnull final ResourcesFactory parentResourcesFactory) {
		this.parentResourcesFactory = requireNonNull(parentResourcesFactory);
	}

}
