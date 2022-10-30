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

import java.util.Locale;

import javax.annotation.*;

import io.confound.config.ConfigurationException;

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
	 * @return Access to configured resources for the implementing class.
	 * @see Rincl#getResources(Object)
	 */
	public default Resources getResources() throws ConfigurationException {
		return Rincl.getResources(this);
	}

	/**
	 * Retrieves resources related to a specified locale for the class.
	 * @param locale The locale for which resources should be returned.
	 * @return Access to configured resources for the implementing class.
	 * @throws NullPointerException if the given locale is <code>null</code>.
	 * @throws ConfigurationException if there is a configuration error.
	 * @see Rincl#getResources(Object, Locale)
	 */
	public default @Nonnull Resources getResources(@Nonnull final Locale locale) throws ConfigurationException {
		return Rincl.getResources(this, locale);
	}

}
