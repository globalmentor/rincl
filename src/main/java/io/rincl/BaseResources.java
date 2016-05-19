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

import static com.globalmentor.java.Objects.*;

import java.text.MessageFormat;
import java.util.Optional;

import javax.annotation.*;

/**
 * Base i18n resources implementation providing common base functionality.
 * <p>
 * An implementing subclass must override {@link #getStringImpl(String)} for local raw string retrieval.
 * </p>
 * @author Garret Wilson
 */
public abstract class BaseResources extends AbstractResources {

	/**
	 * Context class and parent resources constructor.
	 * @param contextClass The context with which these resources are related; usually the class of the object requesting the resource.
	 * @param parentResources The parent resources for fallback lookup.
	 * @throws NullPointerException if the given context class and/or parent resources is <code>null</code>.
	 */
	public BaseResources(@Nonnull final Class<?> contextClass, @Nonnull final Optional<Resources> parentResources) {
		super(contextClass, parentResources);
	}

	@Override
	public final Optional<String> getOptionalString(final String key, final String... arguments) throws ResourceConfigurationException {
		Optional<String> string = getDereferencedString(key); //get the dereferenced string
		if(string.isPresent()) { //if there is a string
			if(arguments.length > 0) { //if there are arguments, format the string
				//TODO improve source of MessageFormat; maybe use ThreadLocal
				//TODO switch to using ICU4J
				string = Optional.of(new MessageFormat(string.get(), Rincl.getLocale()).format(arguments));
			}
		} else { //if there is no string, delegate to the parent resources
			string = getParentResources().flatMap(resources -> resources.getOptionalString(key));
		}
		return string;
	}

	/**
	 * Implementation for ultimately retrieving a string resource with resources evaluated.
	 * <p>
	 * This method must not fall back to parent resources; only local strings must be returned.
	 * </p>
	 * <p>
	 * This implementation delegates to {@link #getStringImpl(String)}.
	 * </p>
	 * @param key The resource key.
	 * @return The value of the resource associated with the given key.
	 * @throws NullPointerException if the given key is <code>null</code>.
	 * @throws ResourceConfigurationException if an expression is not in the correct format, or if no resource is associated with a key in an expression.
	 */
	protected final Optional<String> getDereferencedString(final String key) throws ResourceConfigurationException {
		return getStringImpl(key).map(this::dereferenceString); //get the string resource and evaluate references before passing it back
	}

	/**
	 * Implementation for ultimately retrieving a raw string resource.
	 * <p>
	 * This method should usually be implemented but not called directly by other classes. Callers must invoke {@link #dereferenceString(String)} on the returned
	 * string value.
	 * </p>
	 * <p>
	 * This method must not fall back to parent resources; only local strings must be returned.
	 * </p>
	 * @param key The resource key.
	 * @return The value of the resource associated with the given key.
	 * @throws NullPointerException if the given key is <code>null</code>.
	 * @throws ResourceConfigurationException if an expression is not in the correct format, or if no resource is associated with a key in an expression.
	 */
	protected abstract Optional<String> getStringImpl(final String key) throws ResourceConfigurationException;

	/**
	 * Evaluates and replaces any references in the given string.
	 * <p>
	 * This method does not need to be called if the underlying resources implementation already supports expression replacement.
	 * </p>
	 * @param string The string for which expressions should be evaluated.
	 * @return A string with expressions evaluated, which may be the original string.
	 * @throws NullPointerException if the given string is <code>null</code>.
	 * @throws ResourceConfigurationException if an expression is not in the correct format, or if no resource is associated with a key in an expression.
	 */
	protected @Nonnull String dereferenceString(@Nonnull final String string) {
		return checkInstance(string); //TODO implement; allow for dereference strategy
	}
}
