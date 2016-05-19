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

import javax.annotation.*;

import io.csar.*;

/**
 * The concern for internationalization of resources.
 * <p>
 * Once this concern is acquired, it provides access to a specific set of {@link Resources} for some <dfn>context class</dfn>, e.g. the class of the instance
 * requesting resources.
 * </p>
 * @author Garret Wilson
 * @see Csar
 */
public interface ResourceI18nConcern extends Concern {

	/**
	 * Retrieves resources for the given context.
	 * <p>
	 * The context class returned by {@link Resources#getContextClass()} may not necessarily be the context class provided here.
	 * </p>
	 * @param contextClass The context with which these resources are related; usually the class of the object requesting the resource.
	 * @return Access to configured resources for the given context class.
	 * @throws NullPointerException if the given context class is <code>null</code>.
	 * @throws ResourceConfigurationException if there is a configuration error.
	 */
	public Resources getResources(@Nonnull final Class<?> contextClass) throws ResourceConfigurationException;

}
