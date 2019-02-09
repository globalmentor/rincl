/*
 * Copyright © 2018 GlobalMentor, Inc. <http://www.globalmentor.com/>
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

import io.confound.config.*;

/**
 * A wrapper resources that forwards calls to the decorated resources, falling back to a parent resources.
 * @author Garret Wilson
 */
public class ChildResourcesDecorator extends BaseChildConfigurationDecorator<Resources> implements Resources {

	/**
	 * Wrapped resources and parent resources constructor.
	 * @param resources The resources to decorate.
	 * @param parentResources The parent resources to use for fallback lookup.
	 * @throws NullPointerException if the given resources and/or parent resources is <code>null</code>.
	 */
	public ChildResourcesDecorator(@Nonnull Resources resources, @Nonnull final Resources parentResources) {
		super(resources, parentResources);
	}

	@Override
	public Class<?> getContextClass() {
		return getConfiguration().getContextClass();
	}

	//String

	@Override
	public Optional<String> findString(final String key, final Object... arguments) throws ConfigurationException {
		return or(getConfiguration().findString(key, arguments),
				() -> getParentConfiguration().flatMap(configuration -> configuration.findString(key, arguments)));
	}

}
