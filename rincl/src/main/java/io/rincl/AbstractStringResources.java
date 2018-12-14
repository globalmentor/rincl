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

import java.text.MessageFormat;
import java.util.Locale;
import java.util.Optional;

import javax.annotation.*;

import io.confound.config.*;

/**
 * Abstract implementation of access to i18n resources for which the underlying storage is based on strings.
 * <p>
 * As with the parent class, an implementing subclass must override {@link #findConfigurationValueImpl(String)} for local raw string retrieval. This class
 * retrieves all values as stored in string format accessed via {@link #findConfigurationValueImpl(String)}, and afterwards dereferenced using
 * {@link #dereferenceString(String)}.
 * </p>
 * @author Garret Wilson
 */
public abstract class AbstractStringResources extends AbstractStringConfiguration implements Resources {

	private final Class<?> contextClass;

	@Override
	public Class<?> getContextClass() {
		return contextClass;
	}

	/**
	 * Context class constructor.
	 * @param contextClass The context with which these resources are related; usually the class of the object requesting the resource.
	 * @throws NullPointerException if the given context class is <code>null</code>.
	 */
	public AbstractStringResources(@Nonnull final Class<?> contextClass) {
		this.contextClass = requireNonNull(contextClass);
	}

	/**
	 * {@inheritDoc}
	 * <p>
	 * This implementation delegates to {@link #getOptionalStringImpl(String)}.
	 * </p>
	 */
	/*TODO delete
	@Override
	public boolean hasResource(@Nonnull final String key) throws ResourceConfigurationException {
		return getOptionalStringImpl(key).isPresent();
	}
	*/

	/**
	 * {@inheritDoc}
	 * @implSpec This implementation formats the value, if any, retrieved from {@link #findConfigurationValue(String)}.
	 * @see #findConfigurationValue(String)
	 */
	@Override
	public final Optional<String> getOptionalString(final String key, final Object... arguments) throws ResourceConfigurationException { //TODO add tests, maybe in subclass
		Optional<String> string = findConfigurationValue(normalizeKey(key)); //get the dereferenced string
		if(string.isPresent()) { //if there is a string
			if(arguments.length > 0) { //if there are arguments, format the string
				//TODO improve source of MessageFormat; maybe use ThreadLocal
				//TODO switch to using ICU4J
				string = Optional.of(new MessageFormat(string.get(), Rincl.getLocale(Locale.Category.FORMAT)).format(arguments));
			}
		}
		return string;
	}

}
