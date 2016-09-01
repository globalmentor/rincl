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

import java.util.*;
import java.util.Locale.Category;

import javax.annotation.*;

import io.rincl.*;

/**
 * Resource internationalization concern that returns resources backed by a {@link ResourceBundle}.
 * <p>
 * This class creates resource access to a resource bundle determined by the base name of relative to the context class. If no such resource bundle exists, the
 * inheritance hierarchy of the context class is checked. If no resource bundle can be found, default resources are returned.
 * </p>
 * <p>
 * This library installs a global, default {@link ResourceBundleResourceI18nConcern} instance via the {@link ResourceBundleResourceI18nConcernProvider}.
 * </p>
 * @author Garret Wilson
 * @see ResourceBundleResources
 */
public class ResourceBundleResourceI18nConcern extends ResourceBundleResourcesFactory implements ResourceI18nConcern {

	/**
	 * Creates a resources concern that invariably returns a resource bundle for a particular class, with no parent class resolution.
	 * <p>
	 * If one or more explicit base names are given, they will be used <em>instead of</em> the class name when determining a resource bundle.
	 * </p>
	 * <p>
	 * This is a convenience method that uses the correct combination of base name strategy and resolving class strategy, combined with
	 * {@link ResolvingClassStrategy#forFixedContext(Class)}, depending on the arguments given.
	 * </p>
	 * @param fixedContextClass The class for which <em>all</em> resolving classes should be determined.
	 * @param baseNames Explicit base name(s) to use for resource bundle loading <em>instead of</em> the class name.
	 * @return A new resource bundle resource i18n concern fixed for a specific class context.
	 * @see BaseNameStrategy#forClassNameThenBaseNames(String...)
	 * @see BaseNameStrategy#forBaseNames(String...)
	 * @see ResolvingClassStrategy#DEFAULT
	 * @see ResolvingClassStrategy#NO_ANCESTORS
	 * @see ResolvingClassStrategy#forFixedContext(Class)
	 */
	public static ResourceBundleResourceI18nConcern forFixedContext(@Nonnull Class<?> fixedContextClass, @Nonnull final String... baseNames) {
		return forFixedContext(fixedContextClass, false, baseNames);
	}

	/**
	 * Creates a resources concern that invariably returns a resource bundle for a particular class.
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
	 * @return A new resource bundle resource 18n concern fixed for a specific class context.
	 * @see BaseNameStrategy#forClassNameThenBaseNames(String...)
	 * @see BaseNameStrategy#forBaseNames(String...)
	 * @see ResolvingClassStrategy#DEFAULT
	 * @see ResolvingClassStrategy#NO_ANCESTORS
	 * @see ResolvingClassStrategy#forFixedContext(Class)
	 */
	public static ResourceBundleResourceI18nConcern forFixedContext(@Nonnull Class<?> fixedContextClass, final boolean resolveAncestors,
			@Nonnull final String... baseNames) {
		return forFixedContext(ResourcesFactory.NONE, fixedContextClass, resolveAncestors, baseNames);
	}

	/**
	 * Creates a resources concern that invariably returns a resource bundle for a particular class, with no parent class resolution.
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
	 * @return A new resource bundle resource i18n concern fixed for a specific class context.
	 * @see BaseNameStrategy#forClassNameThenBaseNames(String...)
	 * @see BaseNameStrategy#forBaseNames(String...)
	 * @see ResolvingClassStrategy#DEFAULT
	 * @see ResolvingClassStrategy#NO_ANCESTORS
	 * @see ResolvingClassStrategy#forFixedContext(Class)
	 */
	public static ResourceBundleResourceI18nConcern forFixedContext(@Nonnull final ResourcesFactory parentResourcesFactory, @Nonnull Class<?> fixedContextClass,
			@Nonnull final String... baseNames) {
		return forFixedContext(parentResourcesFactory, fixedContextClass, false, baseNames);
	}

	/**
	 * Creates a resources concern that invariably returns a resource bundle for a particular class.
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
	 * @return A new resource bundle resource 18n concern fixed for a specific class context.
	 * @see BaseNameStrategy#forClassNameThenBaseNames(String...)
	 * @see BaseNameStrategy#forBaseNames(String...)
	 * @see ResolvingClassStrategy#DEFAULT
	 * @see ResolvingClassStrategy#NO_ANCESTORS
	 * @see ResolvingClassStrategy#forFixedContext(Class)
	 */
	public static ResourceBundleResourceI18nConcern forFixedContext(@Nonnull final ResourcesFactory parentResourcesFactory, @Nonnull Class<?> fixedContextClass,
			final boolean resolveAncestors, @Nonnull final String... baseNames) {
		final BaseNameStrategy baseNameStrategy = baseNames.length > 0 ? BaseNameStrategy.forBaseNames(baseNames)
				: BaseNameStrategy.forClassNameThenBaseNames(baseNames);
		final ResolvingClassStrategy resolvingClassStrategy = resolveAncestors ? ResolvingClassStrategy.DEFAULT : ResolvingClassStrategy.NO_ANCESTORS;
		return new ResourceBundleResourceI18nConcern(parentResourcesFactory, baseNameStrategy, resolvingClassStrategy.forFixedContext(fixedContextClass));
	}

	private final LocaleSelection localeSelection = new LocaleSelection();

	/**
	 * Constructor that searches for base names based upon class names up the hierarchy of the class.
	 * @see RinclResourceBundleControl#DEFAULT
	 * @see BaseNameStrategy#CLASS_BASE_NAME_STRATEGY
	 * @see ResolvingClassStrategy#DEFAULT
	 */
	public ResourceBundleResourceI18nConcern() {
		this(BaseNameStrategy.CLASS_BASE_NAME_STRATEGY);
	}

	/**
	 * Base name strategy and resolving class strategy constructor.
	 * @param baseNameStrategy The strategy for determining the base names to use for a reference class when searching for resource bundles.
	 * @throws NullPointerException if the given base name strategy and/or resolving class strategy is <code>null</code>.
	 * @see RinclResourceBundleControl#DEFAULT
	 * @see ResolvingClassStrategy#DEFAULT
	 */
	public ResourceBundleResourceI18nConcern(@Nonnull final BaseNameStrategy baseNameStrategy) {
		this(baseNameStrategy, ResolvingClassStrategy.DEFAULT);
	}

	/**
	 * Base name strategy and resolving class strategy constructor.
	 * @param resolvingClassStrategy Strategy for determining the parent class priority when creating resolving parent resources.
	 * @throws NullPointerException if the given base name strategy and/or resolving class strategy is <code>null</code>.
	 * @see RinclResourceBundleControl#DEFAULT
	 * @see BaseNameStrategy#CLASS_BASE_NAME_STRATEGY
	 */
	public ResourceBundleResourceI18nConcern(@Nonnull final ResolvingClassStrategy resolvingClassStrategy) {
		this(BaseNameStrategy.CLASS_BASE_NAME_STRATEGY, resolvingClassStrategy);
	}

	/**
	 * Base name strategy and resolving class strategy constructor.
	 * @param baseNameStrategy The strategy for determining the base names to use for a reference class when searching for resource bundles.
	 * @param resolvingClassStrategy Strategy for determining the parent class priority when creating resolving parent resources.
	 * @throws NullPointerException if the given base name strategy and/or resolving class strategy is <code>null</code>.
	 * @see RinclResourceBundleControl#DEFAULT
	 */
	public ResourceBundleResourceI18nConcern(@Nonnull final BaseNameStrategy baseNameStrategy, @Nonnull final ResolvingClassStrategy resolvingClassStrategy) {
		this(RinclResourceBundleControl.DEFAULT, baseNameStrategy, resolvingClassStrategy);
	}

	/**
	 * Control, base name strategy, and resolving class strategy constructor.
	 * @param resourceBundleControl A resource bundle loading control strategy to use for loading resources.
	 * @param baseNameStrategy The strategy for determining the base names to use for a reference class when searching for resource bundles.
	 * @param resolvingClassStrategy Strategy for determining the parent class priority when creating resolving parent resources.
	 * @throws NullPointerException if the given resource bundle control, base name strategy and/or resolving class strategy is <code>null</code>.
	 */
	public ResourceBundleResourceI18nConcern(@Nonnull final ResourceBundle.Control resourceBundleControl, @Nonnull final BaseNameStrategy baseNameStrategy,
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
	public ResourceBundleResourceI18nConcern(@Nonnull final ResourcesFactory parentResourcesFactory) {
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
	public ResourceBundleResourceI18nConcern(@Nonnull final ResourcesFactory parentResourcesFactory, @Nonnull final BaseNameStrategy baseNameStrategy) {
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
	public ResourceBundleResourceI18nConcern(@Nonnull final ResourcesFactory parentResourcesFactory,
			@Nonnull final ResolvingClassStrategy resolvingClassStrategy) {
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
	public ResourceBundleResourceI18nConcern(@Nonnull final ResourcesFactory parentResourcesFactory, @Nonnull final BaseNameStrategy baseNameStrategy,
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
	public ResourceBundleResourceI18nConcern(@Nonnull final ResourcesFactory parentResourcesFactory, @Nonnull final ResourceBundle.Control resourceBundleControl,
			@Nonnull final BaseNameStrategy baseNameStrategy, @Nonnull final ResolvingClassStrategy resolvingClassStrategy) {
		super(parentResourcesFactory, resourceBundleControl, baseNameStrategy, resolvingClassStrategy);
	}

	@Override
	public Locale getLocale(final Category category) {
		return localeSelection.getLocale(category);
	}

	@Override
	public void setLocale(final Category category, final Locale locale) {
		localeSelection.setLocale(category, locale);
	}

}
