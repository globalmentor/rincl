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

import javax.annotation.Nullable;

import com.globalmentor.model.ConfigurationException;

/**
 * An unchecked configuration exception indicating that resources are not correctly configured, e.g. a value is stored in an invalid format.
 * @author Garret Wilson
 */
public class ResourceConfigurationException extends ConfigurationException {

	private static final long serialVersionUID = 7119517783329277760L;

	/**
	 * Message constructor.
	 * @param message An explanation of why the input could not be parsed, or <code>null</code> if a default message should be used.
	 */
	public ResourceConfigurationException(@Nullable final String message) {
		this(message, null); //construct the class with no cause
	}

	/**
	 * Cause constructor.
	 * @param cause The cause error or <code>null</code> if the cause is nonexistent or unknown.
	 */
	public ResourceConfigurationException(@Nullable final Throwable cause) {
		this(null, cause); //construct the class with no message
	}

	/**
	 * Message and cause constructor.
	 * @param message An explanation of why the input could not be parsed, or <code>null</code> if a default message should be used.
	 * @param cause The cause error or <code>null</code> if the cause is nonexistent or unknown.
	 */
	public ResourceConfigurationException(@Nullable final String message, @Nullable final Throwable cause) {
		super(message, cause); //construct the class
	}

}
