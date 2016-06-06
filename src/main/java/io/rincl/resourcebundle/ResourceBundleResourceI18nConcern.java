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

import static com.globalmentor.java.Objects.*;

import java.util.*;
import java.util.stream.Stream;

import javax.annotation.Nonnull;

import com.globalmentor.java.Classes;

import io.rincl.*;

/**
 * Resource internationalization concern that returns resources backed by a {@link ResourceBundle}.
 * <p>
 * This class creates resource access to a resource bundle determined by the base name of relative to the context class. If no such resource bundle exists, the
 * inheritance hierarchy of the context class is checked. If no resource bundle can be found, default resources are returned.
 * </p>
 * @author Garret Wilson
 * @see ResourceBundleResources
 */
public abstract class ResourceBundleResourceI18nConcern extends AbstractResourceI18nConcern {

	/** Default implementation of resource bundle-based resources. */
	public static final ResourceBundleResourceI18nConcern DEFAULT = new ResourceBundleResourceI18nConcern() {};

	/** This class cannot be publicly instantiated. */
	private ResourceBundleResourceI18nConcern() {
	}

	/**
	 * Creates resource bundle resources that first use the given base name when loading resource bundles before using the base name derived from the reference
	 * class.
	 * <p>
	 * The context class hierarchy is still traversed when searching for resource bundles.
	 * </p>
	 * @param baseName The specific base name used for resource bundle loading.
	 * @return Resource bundle resources.
	 */
	public static ResourceBundleResourceI18nConcern forBaseNameFirst(@Nonnull final String baseName) {
		checkInstance(baseName);
		return new ResourceBundleResourceI18nConcern() {
			@Override
			protected Stream<String> baseNames(Class<?> referenceClass) throws ResourceConfigurationException {
				return Stream.concat(Stream.of(baseName), super.baseNames(referenceClass));
			}
		};
	}

	/**
	 * Creates resource bundle resources that lastly use the given base name when loading resource bundles before using the base name derived from the reference
	 * class.
	 * <p>
	 * The context class hierarchy is still traversed when searching for resource bundles.
	 * </p>
	 * @param baseName The specific base name used for resource bundle loading.
	 * @return Resource bundle resources.
	 */
	public static ResourceBundleResourceI18nConcern forBaseNameLast(@Nonnull final String baseName) {
		checkInstance(baseName);
		return new ResourceBundleResourceI18nConcern() {
			@Override
			protected Stream<String> baseNames(Class<?> referenceClass) throws ResourceConfigurationException {
				return Stream.concat(super.baseNames(referenceClass), Stream.of(baseName));
			}
		};
	}

	/**
	 * Creates resource bundle resources that only use the given base name when loading resource bundles.
	 * <p>
	 * The context class hierarchy is still traversed when searching for resource bundles.
	 * </p>
	 * @param baseName The specific base name used for resource bundle loading.
	 * @return Resource bundle resources.
	 */
	public static ResourceBundleResourceI18nConcern forOnlyBaseName(@Nonnull final String baseName) {
		checkInstance(baseName);
		return new ResourceBundleResourceI18nConcern() {
			@Override
			protected Stream<String> baseNames(Class<?> referenceClass) throws ResourceConfigurationException {
				return Stream.of(baseName);
			}
		};
	}

	/**
	 * Retrieves a stream of base names appropriate for the given reference class.
	 * <p>
	 * The given class represents a single reference point. This method will typically be called with various reference classes up the hierarchy for a given
	 * context class. The implementation of this method therefore would not normally look up the hierarchy chain.
	 * </p>
	 * <p>
	 * The default implementation merely returns the name of the class.
	 * </p>
	 * @param referenceClass The class being reference at a single hierarchy for which base names will be returned when searching for resource bundles.
	 * @return Access to configured resources for the given context class.
	 * @throws NullPointerException if the given context class is <code>null</code>.
	 * @throws ResourceConfigurationException if there is a configuration error.
	 */
	protected Stream<String> baseNames(@Nonnull final Class<?> referenceClass) throws ResourceConfigurationException {
		return Stream.of(referenceClass.getName());
	}

	/**
	 * {@inheritDoc}
	 * <p>
	 * This implementation returns a hierarchy of {@link ResourceBundleResources} implementations from the discovered resource bundles loaded via
	 * {@link ResourceBundle#getBundle(String, Locale, ClassLoader)}; using the base names returned by {@link #baseNames(Class)} for each reference class up the
	 * hierarchy of parent classes and interfaces, the locale returned by {@link Rincl#getLocale()}, and the class loader of each reference class.
	 * </p>
	 * <p>
	 * If no appropriate resource bundle is found up the hierarchy, an {@link EmptyResources} instance will be returned.
	 * </p>
	 * <p>
	 * TODO implement resources caching
	 * </p>
	 * @see Rincl#getLocale()
	 */
	@Override
	public Resources getResources(final Class<?> contextClass) throws ResourceConfigurationException {
		final Map<Class<?>, ResourceBundle> resourceBundles = getResourceBundles(contextClass, Rincl.getLocale(Locale.Category.DISPLAY));
		if(resourceBundles.isEmpty()) {
			return new EmptyResources(contextClass, getParentResources(contextClass));
		}
		//get a list of the entries and look at them in reverse order, so that we can connect parent resources correctly
		final List<Map.Entry<Class<?>, ResourceBundle>> resourceBundleEntries = new ArrayList<>(resourceBundles.entrySet());
		final ListIterator<Map.Entry<Class<?>, ResourceBundle>> resourceBundleEntryIterator = resourceBundleEntries.listIterator(resourceBundleEntries.size());
		Resources resources = null;
		do { //we know there is at least one resource bundle
			final Map.Entry<Class<?>, ResourceBundle> resourceBundleEntry = resourceBundleEntryIterator.previous();
			//create resources using the existing resources, if any, as the parent
			resources = new ResourceBundleResources(resourceBundleEntry.getKey(), Optional.ofNullable(resources), resourceBundleEntry.getValue());
		} while(resourceBundleEntryIterator.hasPrevious());
		return resources;
	}

	/**
	 * Retrieves resource bundles for the given context in the given locale, associated with classes used for lookup. The map entries will be sorted in order of
	 * precedence, from highest priority to lowest priority.
	 * <p>
	 * This implementation will search up the implementation hierarchy of the given context class, finding every resource bundle it can load via
	 * {@link ResourceBundle#getBundle(String, Locale, ClassLoader)}; using the base names returned by {@link #baseNames(Class)} for each reference class, the
	 * locale returned by {@link Rincl#getLocale()}, and the class loader of each reference class. Only the first base name that returns a resource bundle will be
	 * used.
	 * </p>
	 * @param contextClass The context with which these resources are related; usually the class of the object requesting the resource.
	 * @param locale The locale to use for retrieving the resource bundles.
	 * @return Resource bundles discovered for the given context class and locale.
	 * @throws NullPointerException if the given context class and/or locale is <code>null</code>.
	 */
	Map<Class<?>, ResourceBundle> getResourceBundles(@Nonnull final Class<?> contextClass, @Nonnull final Locale locale) throws ResourceConfigurationException {
		checkInstance(locale);
		final List<Class<?>> referenceClasses = Classes.getAncestorClasses(contextClass); //get the classes for which we should find resource bundles
		final Map<Class<?>, ResourceBundle> resourceBundles = new LinkedHashMap<>(referenceClasses.size());
		for(final Class<?> referenceClass : referenceClasses) {
			final ClassLoader classLoader = referenceClass.getClassLoader();
			//if this context class has no class loader, it's probably because we reached Object or some similar class,
			//which uses the bootstrap class loader; skip it and go on (we may be out of super classes anyway)
			if(classLoader == null) {
				continue;
			}
			for(final String baseName : (Iterable<String>)baseNames(referenceClass)::iterator) {
				try {
					final ResourceBundle resourceBundle = ResourceBundle.getBundle(baseName, locale, classLoader);
					resourceBundles.put(referenceClass, resourceBundle);
				} catch(final MissingResourceException missingResourceException) { //if we couldn't get the resource bundle
					//keep searching up the chain
				}
			}
		}
		return resourceBundles;
	}

}
