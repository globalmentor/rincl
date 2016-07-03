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

/**
 * Mixin interface to provide quick-and-easy resources i18n support to a class.
 * <p>
 * A class implementing this interface can simply call the {@link #getResources()} method to retrieve i18n resources.
 * </p>
 * @author Garret Wilson
 */
public interface Rincled {

	/**
	 * Retrieves resources for the class.
	 * <p>
	 * The class returned by {@link Object#getClass()} will be used as the context class.
	 * </p>
	 * @return Access to configured resources for the implementing class.
	 * @see Rincl#getResources(Class)
	 * @see Object#getClass()
	 */
	public default Resources getResources() throws ResourceConfigurationException {
		return Rincl.getResources(getClass());
	}

}
