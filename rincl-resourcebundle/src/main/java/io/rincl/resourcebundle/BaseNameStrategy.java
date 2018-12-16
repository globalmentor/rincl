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

package io.rincl.resourcebundle;

import java.util.stream.Stream;

import javax.annotation.Nonnull;

import io.confound.config.ConfigurationException;

/**
 * Strategy for determining the base names to use for a reference class when searching for resource bundles.
 * @author Garret Wilson
 */
@FunctionalInterface
public interface BaseNameStrategy {

	/** Base name strategy that returns the base name of the context class. */
	public static final BaseNameStrategy CLASS_BASE_NAME_STRATEGY = referenceClass -> Stream.of(referenceClass.getName());

	/**
	 * Creates a base name strategy first returns the given base name(s) then the base name derived from the reference class.
	 * @param baseNames Explicit base name(s) to use for resource bundle loading.
	 * @return A strategy that returns first the given base name(s) and then the reference class name.
	 */
	public static BaseNameStrategy forBaseNamesThenClassName(@Nonnull final String... baseNames) {
		return referenceClass -> Stream.concat(Stream.of(baseNames), Stream.of(referenceClass.getName()));
	}

	/**
	 * Creates a base name strategy first returns the base name derived from the reference class and then the given base name(s).
	 * @param baseNames Explicit base name(s) to use for resource bundle loading.
	 * @return A strategy that returns first the reference class name and then the given base name(s).
	 */
	public static BaseNameStrategy forClassNameThenBaseNames(@Nonnull final String... baseNames) {
		return referenceClass -> Stream.concat(Stream.of(referenceClass.getName()), Stream.of(baseNames));
	}

	/**
	 * Creates a base name strategy returns the given base name(s).
	 * @param baseNames Explicit base name(s) to use for resource bundle loading.
	 * @return A strategy that returns the given base name(s).
	 */
	public static BaseNameStrategy forBaseNames(@Nonnull final String... baseNames) {
		return referenceClass -> Stream.of(baseNames);
	}

	/**
	 * Retrieves a stream of base names appropriate for the given reference class.
	 * <p>
	 * The given class represents a single reference point. This method will typically be called with various reference classes up the hierarchy for a given
	 * context class. The implementation of this method therefore would not normally look up the hierarchy chain.
	 * </p>
	 * @param referenceClass The class being reference at a single hierarchy for which base names will be returned when searching for resource bundles.
	 * @return Access to configured resources for the given context class.
	 * @throws NullPointerException if the given context class is <code>null</code>.
	 * @throws ConfigurationException if there is a configuration error.
	 */
	public Stream<String> baseNames(@Nonnull final Class<?> referenceClass) throws ConfigurationException;
}
