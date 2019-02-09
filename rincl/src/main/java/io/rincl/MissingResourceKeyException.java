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

import java.util.*;

import javax.annotation.*;

import io.confound.config.MissingConfigurationKeyException;

/**
 * A configuration exception indicating that a configuration key was not found for resources.
 * @author Garret Wilson
 */
public class MissingResourceKeyException extends MissingConfigurationKeyException {

	private static final long serialVersionUID = 8934301772708870552L;

	/**
	 * Key constructor.
	 * @param key The key of the configuration configuration that was not found.
	 */
	public MissingResourceKeyException(@Nonnull final String key) {
		this(null, key);
	}

	/**
	 * Message and key constructor.
	 * @param message An explanation of why the input could not be parsed, or <code>null</code> if no message should be used.
	 * @param key The key of the configuration configuration that was not found.
	 */
	public MissingResourceKeyException(@Nullable final String message, @Nonnull final String key) {
		this(message, key, null);
	}

	/**
	 * Cause constructor. The message of the cause will be used if available.
	 * @param key The key of the configuration configuration that was not found.
	 * @param cause The cause error or <code>null</code> if the cause is nonexistent or unknown.
	 */
	public MissingResourceKeyException(@Nonnull final String key, @Nullable final Throwable cause) {
		this(cause == null ? null : cause.toString(), key, cause);
	}

	/**
	 * Message, key, and cause constructor.
	 * @param message An explanation of why the input could not be parsed, or <code>null</code> if no message should be used.
	 * @param key The key of the configuration configuration that was not found.
	 * @param cause The cause error or <code>null</code> if the cause is nonexistent or unknown.
	 */
	public MissingResourceKeyException(@Nullable final String message, @Nonnull final String key, @Nullable final Throwable cause) {
		super(message, key, cause);
	}

	/**
	 * Creates an exception from an instance of Java's missing resource exception, usually associated with resource bundles.
	 * @param missingResourceException The Java resource lookup exception that is the cause of this exception.
	 * @see ResourceBundle
	 * @see MissingResourceException
	 */
	public MissingResourceKeyException(@Nullable final MissingResourceException missingResourceException) {
		this(missingResourceException.getMessage(), missingResourceException.getKey(), missingResourceException);
	}

}
