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

import java.util.Locale;
import java.util.Locale.Category;

import org.junit.*;

/**
 * Test base implemented methods of {@link AbstractResourceI18nConcern}.
 * @author Garret Wilson
 */
public class AbstractResourceI18nConcernTest {

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

	/** @see AbstractResourceI18nConcern#getLocale(Category) */
	@Test
	public void testNoLocaleSetReturnsJVMDefault() {
		Locale.setDefault(Locale.ENGLISH);
		Locale.setDefault(Category.DISPLAY, Locale.GERMAN);
		Locale.setDefault(Category.FORMAT, Locale.FRENCH);
		final ResourceI18nConcern concern = new AbstractResourceI18nConcern() {
			@Override
			public Resources getResources(Class<?> contextClass, Locale locale) throws ResourceConfigurationException {
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
	 * @see AbstractResourceI18nConcern#getLocale(Category)
	 * @see AbstractResourceI18nConcern#setLocale(Category, Locale)
	 */
	@Test
	public void testSetDisplayLocale() {
		Locale.setDefault(Locale.ENGLISH);
		Locale.setDefault(Category.DISPLAY, Locale.GERMAN);
		Locale.setDefault(Category.FORMAT, Locale.FRENCH);
		final ResourceI18nConcern concern = new AbstractResourceI18nConcern() {
			@Override
			public Resources getResources(Class<?> contextClass, Locale locale) throws ResourceConfigurationException {
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
	 * @see AbstractResourceI18nConcern#getLocale(Category)
	 * @see AbstractResourceI18nConcern#setLocale(Category, Locale)
	 */
	@Test
	public void testSetFormatLocale() {
		Locale.setDefault(Locale.ENGLISH);
		Locale.setDefault(Category.DISPLAY, Locale.GERMAN);
		Locale.setDefault(Category.FORMAT, Locale.FRENCH);
		final ResourceI18nConcern concern = new AbstractResourceI18nConcern() {
			@Override
			public Resources getResources(Class<?> contextClass, Locale locale) throws ResourceConfigurationException {
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
	 * @see AbstractResourceI18nConcern#getLocale(Category)
	 * @see AbstractResourceI18nConcern#setLocale(Locale)
	 */
	@Test
	public void testSetLocale() {
		Locale.setDefault(Locale.ENGLISH);
		Locale.setDefault(Category.DISPLAY, Locale.GERMAN);
		Locale.setDefault(Category.FORMAT, Locale.FRENCH);
		final ResourceI18nConcern concern = new AbstractResourceI18nConcern() {
			@Override
			public Resources getResources(Class<?> contextClass, Locale locale) throws ResourceConfigurationException {
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

}
