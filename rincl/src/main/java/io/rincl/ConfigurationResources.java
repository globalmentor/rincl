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

import static java.util.Objects.*;

import javax.annotation.Nonnull;

import io.confound.config.*;

/**
 * Resources that wrap an existing Configuration instance.
 * @author Garret Wilson
 */
public class ConfigurationResources extends AbstractConfigurationDecorator implements Resources {

	private final Class<?> contextClass;

	@Override
	public Class<?> getContextClass() {
		return contextClass;
	}

	/**
	 * Context class and wrapped configuration constructor.
	 * @param contextClass The context with which these resources are related; usually the class of the object requesting the resource.
	 * @param configuration The configuration to decorate.
	 * @throws NullPointerException if the given context class and/or configuration is <code>null</code>.
	 */
	public ConfigurationResources(@Nonnull final Class<?> contextClass, @Nonnull final Configuration configuration) {
		super(configuration);
		this.contextClass = requireNonNull(contextClass);
	}

}
