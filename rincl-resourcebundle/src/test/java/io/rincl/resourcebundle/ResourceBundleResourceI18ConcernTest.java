/*
 * Copyright © 2016 GlobalMentor, Inc. <https://www.globalmentor.com/>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
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

import org.junit.jupiter.api.*;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

import io.rincl.Resources;

/**
 * Tests of {@link ResourceBundleResourceI18nConcern}.
 * @author Garret Wilson
 */
public class ResourceBundleResourceI18ConcernTest {

	private Locale defaultLocale, defaultDisplayLocale, defaultFormatLocale;

	@BeforeEach
	public void setTestLocale() {
		defaultLocale = Locale.getDefault();
		defaultDisplayLocale = Locale.getDefault(Category.DISPLAY);
		defaultFormatLocale = Locale.getDefault(Category.FORMAT);
		Locale.setDefault(Locale.ENGLISH);
	}

	@AfterEach
	public void revertDefaultLocales() {
		Locale.setDefault(defaultLocale);
		Locale.setDefault(Category.DISPLAY, defaultDisplayLocale);
		Locale.setDefault(Category.FORMAT, defaultFormatLocale);
	}

	//ResourceBundleResourceI18nConcern.getResources(Class)

	/**
	 * Happy path test of loading resources for {@link FooBar}, which uses a traditional properties file.
	 * @see ResourceBundleResourceI18nConcern#getResources(Class)
	 * @see FooBar
	 */
	@Test
	public void testDefaultGetResourcesForFooBar() {
		final Resources resources = new ResourceBundleResourceI18nConcern().getResources(FooBar.class);
		assertThat(resources.getString("foo"), is("bar"));
	}

	/**
	 * Test of loading resources for {@link FooBar} for various locales.
	 * @see ResourceBundleResourceI18nConcern#getResources(Class, Locale)
	 * @see FooBar
	 */
	@Test
	public void testDefaultGetLocaleResourcesForFooBar() {
		assertThat(new ResourceBundleResourceI18nConcern().getResources(FooBar.class, new Locale("en")).getString("teacup"), is("teacup"));
		assertThat(new ResourceBundleResourceI18nConcern().getResources(FooBar.class, new Locale("pt")).getString("teacup"), is("chávena"));
		assertThat(new ResourceBundleResourceI18nConcern().getResources(FooBar.class, new Locale("pt", "BR")).getString("teacup"), is("xícara"));
	}

	/**
	 * Happy path test of loading resources for {@link XmlFooBar}, which uses an XML format properties file.
	 * @see ResourceBundleResourceI18nConcern#getResources(Class)
	 * @see FooBar
	 */
	@Test
	public void testDefaultGetResourcesForXMlFooBar() {
		final Resources resources = new ResourceBundleResourceI18nConcern().getResources(XmlFooBar.class);
		assertThat(resources.getString("foo"), is("bar"));
	}

	/**
	 * Happy path test of loading XML resources for {@link XmlFooBar}.
	 * @see ResourceBundleResourceI18nConcern#getResources(Class)
	 * @see FooBar
	 */
	@Test
	public void testDefaultGetResourcesForXmlFooBar() {
		final Resources resources = new ResourceBundleResourceI18nConcern().getResources(XmlFooBar.class);
		assertThat(resources.getString("foo"), is("bar"));
	}

	/**
	 * Tests loading resources for {@link Interface}.
	 * @see ResourceBundleResourceI18nConcern#ResourceBundleResourceI18nConcern()
	 * @see ResourceBundleResourceI18nConcern#getResources(Class)
	 * @see Interface
	 */
	@Test
	public void testDefaultGetResourcesForInterface() {
		final Resources resources = new ResourceBundleResourceI18nConcern().getResources(Interface.class);
		assertThat(resources.getString("interface"), is("interface-value"));
		assertThat(resources.getString("interfaceToOverride"), is("interfaceToOverride-interface"));
		assertThat(resources.hasResource("subInterface"), is(false));
		assertThat(resources.hasResource("subInterfaceToOverride"), is(false));
		assertThat(resources.hasResource("abstractImpl"), is(false));
		assertThat(resources.hasResource("abstractImplToOverride"), is(false));
		assertThat(resources.hasResource("baseImpl"), is(false));
		assertThat(resources.hasResource("baseImplToOverride"), is(false));
		assertThat(resources.hasResource("impl"), is(false));
		assertThat(resources.getString("override"), is("override-interface"));
	}

	/**
	 * Tests loading resources for {@link SubInterface}.
	 * @see ResourceBundleResourceI18nConcern#ResourceBundleResourceI18nConcern()
	 * @see ResourceBundleResourceI18nConcern#getResources(Class)
	 * @see SubInterface
	 */
	@Test
	public void testDefaultGetResourcesForSubInterface() {
		final Resources resources = new ResourceBundleResourceI18nConcern().getResources(SubInterface.class);
		assertThat(resources.getString("interface"), is("interface-value"));
		assertThat(resources.getString("interfaceToOverride"), is("interfaceToOverride-subInterface"));
		assertThat(resources.getString("subInterface"), is("subInterface-value"));
		assertThat(resources.getString("subInterfaceToOverride"), is("subInterfaceToOverride-subInterface"));
		assertThat(resources.hasResource("abstractImpl"), is(false));
		assertThat(resources.hasResource("abstractImplToOverride"), is(false));
		assertThat(resources.hasResource("baseImpl"), is(false));
		assertThat(resources.hasResource("baseImplToOverride"), is(false));
		assertThat(resources.hasResource("impl"), is(false));
		assertThat(resources.getString("override"), is("override-subInterface"));
	}

	/**
	 * Tests loading resources for {@link AbstractImpl}.
	 * @see ResourceBundleResourceI18nConcern#ResourceBundleResourceI18nConcern()
	 * @see ResourceBundleResourceI18nConcern#getResources(Class)
	 * @see AbstractImpl
	 */
	@Test
	public void testDefaultGetResourcesForAbstractImpl() {
		final Resources resources = new ResourceBundleResourceI18nConcern().getResources(AbstractImpl.class);
		assertThat(resources.getString("interface"), is("interface-value"));
		assertThat(resources.getString("interfaceToOverride"), is("interfaceToOverride-abstractImpl"));
		assertThat(resources.hasResource("subInterface"), is(false));
		assertThat(resources.hasResource("subInterfaceToOverride"), is(false));
		assertThat(resources.getString("abstractImpl"), is("abstractImpl-value"));
		assertThat(resources.getString("abstractImplToOverride"), is("abstractImplToOverride-abstractImpl"));
		assertThat(resources.hasResource("baseImpl"), is(false));
		assertThat(resources.hasResource("baseImplToOverride"), is(false));
		assertThat(resources.hasResource("impl"), is(false));
		assertThat(resources.getString("override"), is("override-abstractImpl"));
	}

	/**
	 * Tests loading resources for {@link BaseImpl}.
	 * @see ResourceBundleResourceI18nConcern#ResourceBundleResourceI18nConcern()
	 * @see ResourceBundleResourceI18nConcern#getResources(Class)
	 * @see BaseImpl
	 */
	@Test
	public void testDefaultGetResourcesForBaseImpl() {
		final Resources resources = new ResourceBundleResourceI18nConcern().getResources(BaseImpl.class);
		assertThat(resources.getString("interface"), is("interface-value"));
		assertThat(resources.getString("interfaceToOverride"), is("interfaceToOverride-abstractImpl"));
		assertThat(resources.getString("subInterface"), is("subInterface-value"));
		assertThat(resources.getString("subInterfaceToOverride"), is("subInterfaceToOverride-baseImpl"));
		assertThat(resources.getString("abstractImpl"), is("abstractImpl-value"));
		assertThat(resources.getString("abstractImplToOverride"), is("abstractImplToOverride-baseImpl"));
		assertThat(resources.getString("baseImpl"), is("baseImpl-value"));
		assertThat(resources.getString("baseImplToOverride"), is("baseImplToOverride-baseImpl"));
		assertThat(resources.hasResource("impl"), is(false));
		assertThat(resources.getString("override"), is("override-baseImpl"));
	}

	/**
	 * Tests loading resources for {@link Impl}.
	 * @see ResourceBundleResourceI18nConcern#ResourceBundleResourceI18nConcern()
	 * @see ResourceBundleResourceI18nConcern#getResources(Class)
	 * @see Impl
	 */
	@Test
	public void testDefaultGetResourcesForImpl() {
		final Resources resources = new ResourceBundleResourceI18nConcern().getResources(Impl.class);
		assertThat(resources.getString("interface"), is("interface-value"));
		assertThat(resources.getString("interfaceToOverride"), is("interfaceToOverride-abstractImpl"));
		assertThat(resources.getString("subInterface"), is("subInterface-value"));
		assertThat(resources.getString("subInterfaceToOverride"), is("subInterfaceToOverride-baseImpl"));
		assertThat(resources.getString("abstractImpl"), is("abstractImpl-value"));
		assertThat(resources.getString("abstractImplToOverride"), is("abstractImplToOverride-baseImpl"));
		assertThat(resources.getString("baseImpl"), is("baseImpl-value"));
		assertThat(resources.getString("baseImplToOverride"), is("baseImplToOverride-impl"));
		assertThat(resources.getString("impl"), is("impl-value"));
		assertThat(resources.getString("override"), is("override-impl"));
	}

}
