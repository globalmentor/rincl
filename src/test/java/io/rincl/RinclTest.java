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
import static org.mockito.Mockito.*;

import java.util.Locale;
import java.util.Locale.Category;

import org.junit.*;

import io.csar.Csar;

/**
 * Tests of {@link Rincl}.
 * @author Garret Wilson
 */
public class RinclTest {

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

	@After
	public void resetRincl() {
		Csar.unregisterDefaultConcern(ResourceI18nConcern.class); //unregister any default concern 
	}

	//no Rincl configuration

	/** @see Rincl#getLocale(Category) */
	@Test
	public void noConfigurationRinclGetLocaleIsJVMDefault() {
		Locale.setDefault(Locale.ENGLISH);
		assertThat(Rincl.getLocale(Category.DISPLAY), is(sameInstance(Locale.getDefault(Category.DISPLAY))));
		assertThat(Rincl.getLocale(Category.FORMAT), is(sameInstance(Locale.getDefault(Category.FORMAT))));
	}

	/** @see Rincl#setLocale(Category, Locale) */
	@Test
	public void noConfigurationRinclSetLocaleCategoryChangesJVMDefault() {
		Locale.setDefault(Locale.ENGLISH);
		Rincl.setLocale(Category.DISPLAY, Locale.GERMAN);
		Rincl.setLocale(Category.FORMAT, Locale.FRENCH);
		assertThat(Locale.getDefault(Category.DISPLAY), is(Locale.GERMAN));
		assertThat(Locale.getDefault(Category.FORMAT), is(Locale.FRENCH));
		assertThat(Rincl.getLocale(Category.DISPLAY), is(sameInstance(Locale.getDefault(Category.DISPLAY))));
		assertThat(Rincl.getLocale(Category.FORMAT), is(sameInstance(Locale.getDefault(Category.FORMAT))));
	}

	/** @see Rincl#setLocale(Locale) */
	@Test
	public void noConfigurationRinclSetLocaleChangesJVMDefault() {
		Locale.setDefault(Locale.ENGLISH);
		Rincl.setLocale(Locale.ITALIAN);
		assertThat(Locale.getDefault(), is(Locale.ITALIAN));
		assertThat(Locale.getDefault(Category.DISPLAY), is(Locale.ITALIAN));
		assertThat(Locale.getDefault(Category.FORMAT), is(Locale.ITALIAN));
		assertThat(Rincl.getLocale(Category.DISPLAY), is(sameInstance(Locale.getDefault(Category.DISPLAY))));
		assertThat(Rincl.getLocale(Category.FORMAT), is(sameInstance(Locale.getDefault(Category.FORMAT))));
	}

	//default concern

	/**
	 * @see Rincl#setDefaultResourceI18nConcern(ResourceI18nConcern)
	 * @see Rincl#getLocale(Category)
	 */
	@Test
	public void defaultConcernRinclGetLocaleIsConcernLocale() {
		final ResourceI18nConcern defaultConcern = mock(ResourceI18nConcern.class);
		when(defaultConcern.getLocale(Category.DISPLAY)).thenReturn(Locale.GERMAN);
		when(defaultConcern.getLocale(Category.FORMAT)).thenReturn(Locale.FRENCH);
		Rincl.setDefaultResourceI18nConcern(defaultConcern);
		Locale.setDefault(Locale.ENGLISH);
		assertThat(Rincl.getLocale(Category.DISPLAY), is(Locale.GERMAN));
		assertThat(Rincl.getLocale(Category.FORMAT), is(Locale.FRENCH));
	}

	/** @see Rincl#setLocale(Category, Locale) */
	@Test
	public void defaultConcernRinclSetLocaleCategoryChangesJVMDefault() {
		final ResourceI18nConcern defaultConcern = mock(ResourceI18nConcern.class);
		when(defaultConcern.getLocale(Category.DISPLAY)).thenReturn(Locale.GERMAN);
		when(defaultConcern.getLocale(Category.FORMAT)).thenReturn(Locale.FRENCH);
		Rincl.setDefaultResourceI18nConcern(defaultConcern);
		Locale.setDefault(Locale.ENGLISH);
		//note that setting of the actual default concern is not tested here
		Rincl.setLocale(Category.DISPLAY, Locale.GERMAN);
		Rincl.setLocale(Category.FORMAT, Locale.FRENCH);
		assertThat(Locale.getDefault(Category.DISPLAY), is(Locale.GERMAN));
		assertThat(Locale.getDefault(Category.FORMAT), is(Locale.FRENCH));
		assertThat(Rincl.getLocale(Category.DISPLAY), is(sameInstance(Locale.getDefault(Category.DISPLAY))));
		assertThat(Rincl.getLocale(Category.FORMAT), is(sameInstance(Locale.getDefault(Category.FORMAT))));
	}

	/** @see Rincl#setLocale(Locale) */
	@Test
	public void defaultConcernRinclSetLocaleChangesJVMDefault() {
		final ResourceI18nConcern defaultConcern = mock(ResourceI18nConcern.class);
		when(defaultConcern.getLocale(Category.DISPLAY)).thenReturn(Locale.ITALIAN);
		when(defaultConcern.getLocale(Category.FORMAT)).thenReturn(Locale.ITALIAN);
		Rincl.setDefaultResourceI18nConcern(defaultConcern);
		Locale.setDefault(Locale.ENGLISH);
		//note that setting of the actual default concern is not tested here
		Rincl.setLocale(Locale.ITALIAN);
		assertThat(Locale.getDefault(), is(Locale.ITALIAN));
		assertThat(Locale.getDefault(Category.DISPLAY), is(Locale.ITALIAN));
		assertThat(Locale.getDefault(Category.FORMAT), is(Locale.ITALIAN));
		assertThat(Rincl.getLocale(Category.DISPLAY), is(sameInstance(Locale.getDefault(Category.DISPLAY))));
		assertThat(Rincl.getLocale(Category.FORMAT), is(sameInstance(Locale.getDefault(Category.FORMAT))));
	}

}
