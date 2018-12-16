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

import static org.junit.Assert.*;
import static org.hamcrest.Matchers.*;

import java.util.*;
import java.util.Locale.Category;

import org.junit.*;

import io.confound.config.*;

/**
 * Test base implemented methods of {@link BaseResourceI18nConcern}.
 * @author Garret Wilson
 */
public class BaseResourceI18nConcernTest {

	private Locale defaultLocale, defaultDisplayLocale, defaultFormatLocale;

	@Before
	public void saveDefaultLocales() {
		defaultLocale = Locale.getDefault();
		defaultDisplayLocale = Locale.getDefault(Category.DISPLAY);
		defaultFormatLocale = Locale.getDefault(Category.FORMAT);
	}

	@After
	public void revertDefaultLocales() {
		Locale.setDefault(defaultLocale);
		Locale.setDefault(Category.DISPLAY, defaultDisplayLocale);
		Locale.setDefault(Category.FORMAT, defaultFormatLocale);
	}

	/** @see BaseResourceI18nConcern#getLocale(Category) */
	@Test
	public void testNoLocaleSetReturnsJVMDefault() {
		Locale.setDefault(Locale.ENGLISH);
		Locale.setDefault(Category.DISPLAY, Locale.GERMAN);
		Locale.setDefault(Category.FORMAT, Locale.FRENCH);
		final ResourceI18nConcern concern = new BaseResourceI18nConcern(ResourcesFactory.NONE) {
			@Override
			public Optional<Resources> getOptionalResources(Class<?> contextClass, Locale locale) throws ConfigurationException {
				throw new AssertionError();
			}
		};
		assertThat(concern.getLocale(Category.DISPLAY), is(Locale.GERMAN));
		assertThat(concern.getLocale(Category.FORMAT), is(Locale.FRENCH));
		//the JVM defaults haven't changed
		assertThat(Locale.getDefault(), is(Locale.ENGLISH));
		assertThat(Locale.getDefault(Category.DISPLAY), is(Locale.GERMAN));
		assertThat(Locale.getDefault(Category.FORMAT), is(Locale.FRENCH));
	}

	/**
	 * @see BaseResourceI18nConcern#getLocale(Category)
	 * @see BaseResourceI18nConcern#setLocale(Category, Locale)
	 */
	@Test
	public void testSetDisplayLocale() {
		Locale.setDefault(Locale.ENGLISH);
		Locale.setDefault(Category.DISPLAY, Locale.GERMAN);
		Locale.setDefault(Category.FORMAT, Locale.FRENCH);
		final ResourceI18nConcern concern = new BaseResourceI18nConcern(ResourcesFactory.NONE) {
			@Override
			public Optional<Resources> getOptionalResources(Class<?> contextClass, Locale locale) throws ConfigurationException {
				throw new AssertionError();
			}
		};
		concern.setLocale(Category.DISPLAY, Locale.ITALIAN);
		//the concern is updated
		assertThat(concern.getLocale(Category.DISPLAY), is(Locale.ITALIAN));
		assertThat(concern.getLocale(Category.FORMAT), is(Locale.FRENCH));
		//the JVM defaults haven't changed
		assertThat(Locale.getDefault(), is(Locale.ENGLISH));
		assertThat(Locale.getDefault(Category.DISPLAY), is(Locale.GERMAN));
		assertThat(Locale.getDefault(Category.FORMAT), is(Locale.FRENCH));
	}

	/**
	 * @see BaseResourceI18nConcern#getLocale(Category)
	 * @see BaseResourceI18nConcern#setLocale(Category, Locale)
	 */
	@Test
	public void testSetFormatLocale() {
		Locale.setDefault(Locale.ENGLISH);
		Locale.setDefault(Category.DISPLAY, Locale.GERMAN);
		Locale.setDefault(Category.FORMAT, Locale.FRENCH);
		final ResourceI18nConcern concern = new BaseResourceI18nConcern(ResourcesFactory.NONE) {
			@Override
			public Optional<Resources> getOptionalResources(Class<?> contextClass, Locale locale) throws ConfigurationException {
				throw new AssertionError();
			}
		};
		concern.setLocale(Category.FORMAT, Locale.ITALIAN);
		//the concern is updated
		assertThat(concern.getLocale(Category.DISPLAY), is(Locale.GERMAN));
		assertThat(concern.getLocale(Category.FORMAT), is(Locale.ITALIAN));
		//the JVM defaults haven't changed
		assertThat(Locale.getDefault(), is(Locale.ENGLISH));
		assertThat(Locale.getDefault(Category.DISPLAY), is(Locale.GERMAN));
		assertThat(Locale.getDefault(Category.FORMAT), is(Locale.FRENCH));
	}

	/**
	 * @see BaseResourceI18nConcern#getLocale(Category)
	 * @see BaseResourceI18nConcern#setLocale(Locale)
	 */
	@Test
	public void testSetLocale() {
		Locale.setDefault(Locale.ENGLISH);
		Locale.setDefault(Category.DISPLAY, Locale.GERMAN);
		Locale.setDefault(Category.FORMAT, Locale.FRENCH);
		final ResourceI18nConcern concern = new BaseResourceI18nConcern(ResourcesFactory.NONE) {
			@Override
			public Optional<Resources> getOptionalResources(Class<?> contextClass, Locale locale) throws ConfigurationException {
				throw new AssertionError();
			}
		};
		concern.setLocale(Locale.ITALIAN);
		//the concern is updated
		assertThat(concern.getLocale(Category.DISPLAY), is(Locale.ITALIAN));
		assertThat(concern.getLocale(Category.FORMAT), is(Locale.ITALIAN));
		//the JVM defaults haven't changed
		assertThat(Locale.getDefault(), is(Locale.ENGLISH));
		assertThat(Locale.getDefault(Category.DISPLAY), is(Locale.GERMAN));
		assertThat(Locale.getDefault(Category.FORMAT), is(Locale.FRENCH));
	}

	/**
	 * Tests fallback to parent resources from resources factory.
	 * @see BaseResourceI18nConcern#getParentResourcesFactory()
	 * @see EmptyResources
	 */
	@Test
	public void testParentResourcesFactory() {
		final Map<String, String> parentResourcesMap = new HashMap<>();
		parentResourcesMap.put("foo", "bar");
		final Resources parentResources = new ConfigurationResources(getClass(), new StringMapConfiguration(parentResourcesMap));
		final ResourcesFactory parentResourcesFactory = (contextClass, locale) -> Optional.of(parentResources);
		final ResourceI18nConcern concern = new BaseResourceI18nConcern(parentResourcesFactory) {
			@Override
			public Optional<Resources> getOptionalResources(Class<?> contextClass, Locale locale) throws ConfigurationException {
				return Optional.of(new EmptyResources(contextClass).withFallbackResources(getParentResourcesFactory().getResources(contextClass, locale)));
			}
		};
		assertThat(concern.getResources(this).getString("foo"), is("bar"));
	}
}
