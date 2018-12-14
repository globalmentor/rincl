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

import static java.util.Objects.*;

import java.util.*;

import javax.annotation.Nonnull;

import io.rincl.*;

/**
 * Resources factory that returns resources backed by a {@link ResourceBundle}.
 * <p>
 * This class creates resource access to a resource bundle determined by the base name of relative to the context class, based upon the {@link BaseNameStrategy}
 * used. If no such resource bundle exists, the inheritance hierarchy of the context class is checked based upon the {@link ResolvingClassStrategy} used. If no
 * resource bundle can be found, default resources are returned based upon the parent {@link ResourcesFactory}.
 * </p>
 * <p>
 * To create factory that loads resources only from a single named resource bundle, use the {@link BaseNameStrategy#forBaseNames(String...)} base name strategy,
 * indicating the base name of the resources file, coupled with the {@link ResolvingClassStrategy#NO_ANCESTORS} resolving class strategy.
 * </p>
 * @author Garret Wilson
 * @see ResourceBundleResources
 */
public class ResourceBundleResourcesFactory implements ResourcesFactory {

	/**
	 * Creates a resources factory that invariably returns a resource bundle for a particular class, with no parent class resolution.
	 * <p>
	 * If one or more explicit base names are given, they will be used <em>instead of</em> the class name when determining a resource bundle.
	 * </p>
	 * <p>
	 * This is a convenience method that uses the correct combination of base name strategy and resolving class strategy, combined with
	 * {@link ResolvingClassStrategy#forFixedContext(Class)}, depending on the arguments given.
	 * </p>
	 * @param fixedContextClass The class for which <em>all</em> resolving classes should be determined.
	 * @param baseNames Explicit base name(s) to use for resource bundle loading <em>instead of</em> the class name.
	 * @return A new resource bundle resources factory fixed for a specific class context.
	 * @see BaseNameStrategy#forClassNameThenBaseNames(String...)
	 * @see BaseNameStrategy#forBaseNames(String...)
	 * @see ResolvingClassStrategy#DEFAULT
	 * @see ResolvingClassStrategy#NO_ANCESTORS
	 * @see ResolvingClassStrategy#forFixedContext(Class)
	 */
	public static ResourceBundleResourcesFactory forFixedContext(@Nonnull Class<?> fixedContextClass, @Nonnull final String... baseNames) {
		return forFixedContext(fixedContextClass, false, baseNames);
	}

	/**
	 * Creates a resources factory that invariably returns a resource bundle for a particular class.
	 * <p>
	 * If one or more explicit base names are given, they will be used <em>instead of</em> the class name when determining a resource bundle.
	 * </p>
	 * <p>
	 * This is a convenience method that uses the correct combination of base name strategy and resolving class strategy, combined with
	 * {@link ResolvingClassStrategy#forFixedContext(Class)}, depending on the arguments given.
	 * </p>
	 * @param fixedContextClass The class for which <em>all</em> resolving classes should be determined.
	 * @param resolveAncestors Whether ancestors of the fixed context class will be searched as well when resolving resources.
	 * @param baseNames Explicit base name(s) to use for resource bundle loading <em>instead of</em> the class name.
	 * @return A new resource bundle resources factory fixed for a specific class context.
	 * @see BaseNameStrategy#forClassNameThenBaseNames(String...)
	 * @see BaseNameStrategy#forBaseNames(String...)
	 * @see ResolvingClassStrategy#DEFAULT
	 * @see ResolvingClassStrategy#NO_ANCESTORS
	 * @see ResolvingClassStrategy#forFixedContext(Class)
	 */
	public static ResourceBundleResourcesFactory forFixedContext(@Nonnull Class<?> fixedContextClass, final boolean resolveAncestors,
			@Nonnull final String... baseNames) {
		return forFixedContext(ResourcesFactory.NONE, fixedContextClass, resolveAncestors, baseNames);
	}

	/**
	 * Creates a resources factory that invariably returns a resource bundle for a particular class, with no parent class resolution.
	 * <p>
	 * If one or more explicit base names are given, they will be used <em>instead of</em> the class name when determining a resource bundle.
	 * </p>
	 * <p>
	 * This is a convenience method that uses the correct combination of base name strategy and resolving class strategy, combined with
	 * {@link ResolvingClassStrategy#forFixedContext(Class)}, depending on the arguments given.
	 * </p>
	 * @param parentResourcesFactory The strategy for creating parent resources for a particular context and locale.
	 * @param fixedContextClass The class for which <em>all</em> resolving classes should be determined.
	 * @param baseNames Explicit base name(s) to use for resource bundle loading <em>instead of</em> the class name.
	 * @return A new resource bundle resources factory fixed for a specific class context.
	 * @see BaseNameStrategy#forClassNameThenBaseNames(String...)
	 * @see BaseNameStrategy#forBaseNames(String...)
	 * @see ResolvingClassStrategy#DEFAULT
	 * @see ResolvingClassStrategy#NO_ANCESTORS
	 * @see ResolvingClassStrategy#forFixedContext(Class)
	 */
	public static ResourceBundleResourcesFactory forFixedContext(@Nonnull final ResourcesFactory parentResourcesFactory, @Nonnull Class<?> fixedContextClass,
			@Nonnull final String... baseNames) {
		return forFixedContext(parentResourcesFactory, fixedContextClass, false, baseNames);
	}

	/**
	 * Creates a resources factory that invariably returns a resource bundle for a particular class.
	 * <p>
	 * If one or more explicit base names are given, they will be used <em>instead of</em> the class name when determining a resource bundle.
	 * </p>
	 * <p>
	 * This is a convenience method that uses the correct combination of base name strategy and resolving class strategy, combined with
	 * {@link ResolvingClassStrategy#forFixedContext(Class)}, depending on the arguments given.
	 * </p>
	 * @param parentResourcesFactory The strategy for creating parent resources for a particular context and locale.
	 * @param fixedContextClass The class for which <em>all</em> resolving classes should be determined.
	 * @param resolveAncestors Whether ancestors of the fixed context class will be searched as well when resolving resources.
	 * @param baseNames Explicit base name(s) to use for resource bundle loading <em>instead of</em> the class name.
	 * @return A new resource bundle resources factory fixed for a specific class context.
	 * @see BaseNameStrategy#forClassNameThenBaseNames(String...)
	 * @see BaseNameStrategy#forBaseNames(String...)
	 * @see ResolvingClassStrategy#DEFAULT
	 * @see ResolvingClassStrategy#NO_ANCESTORS
	 * @see ResolvingClassStrategy#forFixedContext(Class)
	 */
	public static ResourceBundleResourcesFactory forFixedContext(@Nonnull final ResourcesFactory parentResourcesFactory, @Nonnull Class<?> fixedContextClass,
			final boolean resolveAncestors, @Nonnull final String... baseNames) {
		final BaseNameStrategy baseNameStrategy = baseNames.length > 0 ? BaseNameStrategy.forBaseNames(baseNames)
				: BaseNameStrategy.forClassNameThenBaseNames(baseNames);
		final ResolvingClassStrategy resolvingClassStrategy = resolveAncestors ? ResolvingClassStrategy.DEFAULT : ResolvingClassStrategy.NO_ANCESTORS;
		return new ResourceBundleResourcesFactory(parentResourcesFactory, baseNameStrategy, resolvingClassStrategy.forFixedContext(fixedContextClass));
	}

	private final ResourcesFactory parentResourcesFactory;

	/** @return The strategy for creating parent resources for a particular context and locale. */
	protected ResourcesFactory getParentResourcesFactory() {
		return parentResourcesFactory;
	}

	private final ResourceBundle.Control resourceBundleControl;

	/** @return A strategy for controlling the loading of resource bundles. */
	protected @Nonnull ResourceBundle.Control getResourceBundleControl() {
		return resourceBundleControl;
	}

	private final BaseNameStrategy baseNameStrategy;

	/** @return The strategy for determining base names for a reference class. */
	protected @Nonnull BaseNameStrategy getBaseNameStrategy() {
		return baseNameStrategy;
	}

	private final ResolvingClassStrategy resolvingClassStrategy;

	/** @return The strategy for determining parent resources for resolving classes. */
	protected @Nonnull ResolvingClassStrategy getResolvingClassStrategy() {
		return resolvingClassStrategy;
	}

	/**
	 * Constructor that searches for base names based upon class names up the hierarchy of the class.
	 * @see RinclResourceBundleControl#DEFAULT
	 * @see BaseNameStrategy#CLASS_BASE_NAME_STRATEGY
	 * @see ResolvingClassStrategy#DEFAULT
	 */
	public ResourceBundleResourcesFactory() {
		this(BaseNameStrategy.CLASS_BASE_NAME_STRATEGY);
	}

	/**
	 * Base name strategy and resolving class strategy constructor.
	 * @param baseNameStrategy The strategy for determining the base names to use for a reference class when searching for resource bundles.
	 * @throws NullPointerException if the given base name strategy and/or resolving class strategy is <code>null</code>.
	 * @see RinclResourceBundleControl#DEFAULT
	 * @see ResolvingClassStrategy#DEFAULT
	 */
	public ResourceBundleResourcesFactory(@Nonnull final BaseNameStrategy baseNameStrategy) {
		this(baseNameStrategy, ResolvingClassStrategy.DEFAULT);
	}

	/**
	 * Base name strategy and resolving class strategy constructor.
	 * @param resolvingClassStrategy Strategy for determining the parent class priority when creating resolving parent resources.
	 * @throws NullPointerException if the given base name strategy and/or resolving class strategy is <code>null</code>.
	 * @see RinclResourceBundleControl#DEFAULT
	 * @see BaseNameStrategy#CLASS_BASE_NAME_STRATEGY
	 */
	public ResourceBundleResourcesFactory(@Nonnull final ResolvingClassStrategy resolvingClassStrategy) {
		this(BaseNameStrategy.CLASS_BASE_NAME_STRATEGY, resolvingClassStrategy);
	}

	/**
	 * Base name strategy and resolving class strategy constructor.
	 * @param baseNameStrategy The strategy for determining the base names to use for a reference class when searching for resource bundles.
	 * @param resolvingClassStrategy Strategy for determining the parent class priority when creating resolving parent resources.
	 * @throws NullPointerException if the given base name strategy and/or resolving class strategy is <code>null</code>.
	 * @see RinclResourceBundleControl#DEFAULT
	 */
	public ResourceBundleResourcesFactory(@Nonnull final BaseNameStrategy baseNameStrategy, @Nonnull final ResolvingClassStrategy resolvingClassStrategy) {
		this(RinclResourceBundleControl.DEFAULT, baseNameStrategy, resolvingClassStrategy);
	}

	/**
	 * Control, base name strategy, and resolving class strategy constructor.
	 * @param resourceBundleControl A resource bundle loading control strategy to use for loading resources.
	 * @param baseNameStrategy The strategy for determining the base names to use for a reference class when searching for resource bundles.
	 * @param resolvingClassStrategy Strategy for determining the parent class priority when creating resolving parent resources.
	 * @throws NullPointerException if the given resource bundle control, base name strategy and/or resolving class strategy is <code>null</code>.
	 */
	public ResourceBundleResourcesFactory(@Nonnull final ResourceBundle.Control resourceBundleControl, @Nonnull final BaseNameStrategy baseNameStrategy,
			@Nonnull final ResolvingClassStrategy resolvingClassStrategy) {
		this(ResourcesFactory.NONE, resourceBundleControl, baseNameStrategy, resolvingClassStrategy);
	}

	/**
	 * Constructor that searches for base names based upon class names up the hierarchy of the class.
	 * @param parentResourcesFactory The strategy for creating parent resources for a particular context and locale.
	 * @throws NullPointerException if the given parent resources factory is <code>null</code>.
	 * @see RinclResourceBundleControl#DEFAULT
	 * @see BaseNameStrategy#CLASS_BASE_NAME_STRATEGY
	 * @see ResolvingClassStrategy#DEFAULT
	 */
	public ResourceBundleResourcesFactory(@Nonnull final ResourcesFactory parentResourcesFactory) {
		this(parentResourcesFactory, BaseNameStrategy.CLASS_BASE_NAME_STRATEGY);
	}

	/**
	 * Base name strategy and resolving class strategy constructor.
	 * @param parentResourcesFactory The strategy for creating parent resources for a particular context and locale.
	 * @param baseNameStrategy The strategy for determining the base names to use for a reference class when searching for resource bundles.
	 * @throws NullPointerException if the given parent resources factory and/or base name strategy is <code>null</code>.
	 * @see RinclResourceBundleControl#DEFAULT
	 * @see ResolvingClassStrategy#DEFAULT
	 */
	public ResourceBundleResourcesFactory(@Nonnull final ResourcesFactory parentResourcesFactory, @Nonnull final BaseNameStrategy baseNameStrategy) {
		this(parentResourcesFactory, baseNameStrategy, ResolvingClassStrategy.DEFAULT);
	}

	/**
	 * Base name strategy and resolving class strategy constructor.
	 * @param parentResourcesFactory The strategy for creating parent resources for a particular context and locale.
	 * @param resolvingClassStrategy Strategy for determining the parent class priority when creating resolving parent resources.
	 * @throws NullPointerException if the given parent resources factory and/or resolving class strategy is <code>null</code>.
	 * @see RinclResourceBundleControl#DEFAULT
	 * @see BaseNameStrategy#CLASS_BASE_NAME_STRATEGY
	 */
	public ResourceBundleResourcesFactory(@Nonnull final ResourcesFactory parentResourcesFactory, @Nonnull final ResolvingClassStrategy resolvingClassStrategy) {
		this(parentResourcesFactory, BaseNameStrategy.CLASS_BASE_NAME_STRATEGY, resolvingClassStrategy);
	}

	/**
	 * Base name strategy and resolving class strategy constructor.
	 * @param parentResourcesFactory The strategy for creating parent resources for a particular context and locale.
	 * @param baseNameStrategy The strategy for determining the base names to use for a reference class when searching for resource bundles.
	 * @param resolvingClassStrategy Strategy for determining the parent class priority when creating resolving parent resources.
	 * @throws NullPointerException if the given parent resources factory, base name strategy, and/or resolving class strategy is <code>null</code>.
	 * @see RinclResourceBundleControl#DEFAULT
	 */
	public ResourceBundleResourcesFactory(@Nonnull final ResourcesFactory parentResourcesFactory, @Nonnull final BaseNameStrategy baseNameStrategy,
			@Nonnull final ResolvingClassStrategy resolvingClassStrategy) {
		this(parentResourcesFactory, RinclResourceBundleControl.DEFAULT, baseNameStrategy, resolvingClassStrategy);
	}

	/**
	 * Control, base name strategy, and resolving class strategy constructor.
	 * @param parentResourcesFactory The strategy for creating parent resources for a particular context and locale.
	 * @param resourceBundleControl A resource bundle loading control strategy to use for loading resources.
	 * @param baseNameStrategy The strategy for determining the base names to use for a reference class when searching for resource bundles.
	 * @param resolvingClassStrategy Strategy for determining the parent class priority when creating resolving parent resources.
	 * @throws NullPointerException if the given parent resources factory, resource bundle control, base name strategy and/or resolving class strategy is
	 *           <code>null</code>.
	 */
	public ResourceBundleResourcesFactory(@Nonnull final ResourcesFactory parentResourcesFactory, @Nonnull final ResourceBundle.Control resourceBundleControl,
			@Nonnull final BaseNameStrategy baseNameStrategy, @Nonnull final ResolvingClassStrategy resolvingClassStrategy) {
		this.parentResourcesFactory = requireNonNull(parentResourcesFactory);
		this.resourceBundleControl = requireNonNull(resourceBundleControl);
		this.baseNameStrategy = requireNonNull(baseNameStrategy);
		this.resolvingClassStrategy = requireNonNull(resolvingClassStrategy);
	}

	/**
	 * {@inheritDoc}
	 * <p>
	 * This implementation returns a hierarchy of {@link ResourceBundleResources} implementations from the discovered resource bundles loaded via
	 * {@link ResourceBundle#getBundle(String, Locale, ClassLoader, ResourceBundle.Control)}; using the base names returned by the {@link #getBaseNameStrategy()},
	 * the classes returned by {@link #getResolvingClassStrategy()}, the locale returned by {@link Rincl#getLocale(Locale.Category)} for
	 * {@link Locale.Category#DISPLAY}, the class loader of each reference class, and the resource bundle control returned by {@link #getResourceBundleControl()}.
	 * </p>
	 * <p>
	 * TODO implement resources caching
	 * </p>
	 * @see Rincl#getLocale(Locale.Category)
	 * @see Locale.Category#DISPLAY
	 * @see #getResourceBundleControl()
	 */
	@Override
	public Optional<Resources> getOptionalResources(final Class<?> contextClass, final Locale locale) throws ResourceConfigurationException {
		Resources resources = null; //at first we don't know if we'll find any resources
		//get a list of the resolving classes to use, and for each one try to get a resource bundle
		for(final Class<?> resolvingClass : (Iterable<Class<?>>)() -> getResolvingClassStrategy().resolvingClasses(contextClass).iterator()) {
			final Optional<ResourceBundle> resourceBundle = getResourceBundle(resolvingClass, locale);
			if(resourceBundle.isPresent()) { //chain the resources if there is a resource bundle present
				final Resources resolvingResources = new ResourceBundleResources(resolvingClass, resourceBundle.get());
				resources = resources == null ? resolvingResources : resources.withFallbackResources(resolvingResources);
			}
		}
		final Optional<Resources> parentResources = getParentResourcesFactory().getOptionalResources(contextClass, locale);
		//if there are no resources for the class and its hierarchy, we use the parent resources (if any) as is
		//otherwise, if there are parent resources, add them as a fallback
		return resources == null ? parentResources : Optional.of(resources.withFallbackResources(parentResources));
	}

	/**
	 * Retrieves a resource bundle for the given context class in the given locale.
	 * <p>
	 * This implementation calls {@link ResourceBundle#getBundle(String, Locale, ClassLoader, ResourceBundle.Control)} using the base names returned by
	 * {@link #getBaseNameStrategy()} for the reference class, the given locale, the class loader of the reference class, and the resource bundle control returned
	 * by {@link #getResourceBundleControl()}. Only the first base name that returns a resource bundle will be used.
	 * </p>
	 * @param referenceClass The class with which these resources are related.
	 * @param locale The locale to use for retrieving the resource bundles.
	 * @return The resource bundle discovered for the given context class and locale.
	 * @throws NullPointerException if the given context class and/or locale is <code>null</code>.
	 * @see #getResourceBundleControl()
	 */
	protected Optional<ResourceBundle> getResourceBundle(@Nonnull final Class<?> referenceClass, @Nonnull final Locale locale)
			throws ResourceConfigurationException {
		requireNonNull(locale);
		final ClassLoader classLoader = referenceClass.getClassLoader();
		//if this context class has no class loader, it's probably because we reached Object or some similar class,
		//which uses the bootstrap class loader; skip it and go on (we may be out of super classes anyway)
		if(classLoader != null) {
			for(final String baseName : (Iterable<String>)getBaseNameStrategy().baseNames(referenceClass)::iterator) {

				//In the future, instead of going through ResourceBundle using a custom control,
				//we may decide to do the lookup ourselves and then load a Configuration loaded using an installed ConfigurationFileFormat,
				//returning it wrapped in a ConfigurationResources decorator.

				try {
					return Optional.of(ResourceBundle.getBundle(baseName, locale, classLoader, getResourceBundleControl()));
				} catch(final MissingResourceException missingResourceException) { //if we couldn't get the resource bundle
					//keep searching
				}
			}
		}
		return Optional.empty();
	}

}
