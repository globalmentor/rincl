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

import static org.junit.Assert.*;

import java.util.*;
import java.util.Locale.Category;

import static org.hamcrest.Matchers.*;
import org.junit.*;

import io.rincl.Resources;

/**
 * Tests of {@link ResourceBundleResourceI18nConcern}.
 * @author Garret Wilson
 */
public class ResourceBundleResourceI18ConcernTest {

	private Locale defaultLocale, defaultDisplayLocale, defaultFormatLocale;

	@Before
	public void setTestLocale() {
		defaultLocale = Locale.getDefault();
		defaultDisplayLocale = Locale.getDefault(Category.DISPLAY);
		defaultFormatLocale = Locale.getDefault(Category.FORMAT);
		Locale.setDefault(Locale.ENGLISH);
	}

	@After
	public void revertDefaultLocales() {
		Locale.setDefault(defaultLocale);
		Locale.setDefault(Category.DISPLAY, defaultDisplayLocale);
		Locale.setDefault(Category.FORMAT, defaultFormatLocale);
	}

	//ResourceBundleResourceI18nConcern.getResources(Class)

	/**
	 * Happy path test of loading resources for {@link FooBar}, which uses a traditional properties file.
	 * @see ResourceBundleResourceI18nConcern#DEFAULT
	 * @see ResourceBundleResourceI18nConcern#getResources(Class)
	 * @see FooBar
	 */
	@Test
	public void testDefaultGetResourcesForFooBar() {
		final Resources resources = ResourceBundleResourceI18nConcern.DEFAULT.getResources(FooBar.class);
		assertThat(resources.getString("foo"), is("bar"));
	}

	/**
	 * Happy path test of loading resources for {@link XmlFooBar}, which uses an XML format properties file.
	 * @see ResourceBundleResourceI18nConcern#DEFAULT
	 * @see ResourceBundleResourceI18nConcern#getResources(Class)
	 * @see FooBar
	 */
	@Test
	public void testDefaultGetResourcesForXMlFooBar() {
		final Resources resources = ResourceBundleResourceI18nConcern.DEFAULT.getResources(XmlFooBar.class);
		assertThat(resources.getString("foo"), is("bar"));
	}

	/**
	 * Happy path test of loading XML resources for {@link XmlFooBar}.
	 * @see ResourceBundleResourceI18nConcern#DEFAULT
	 * @see ResourceBundleResourceI18nConcern#getResources(Class)
	 * @see FooBar
	 */
	@Test
	public void testDefaultGetResourcesForXmlFooBar() {
		final Resources resources = ResourceBundleResourceI18nConcern.DEFAULT.getResources(XmlFooBar.class);
		assertThat(resources.getString("foo"), is("bar"));
	}

	/**
	 * Tests loading resources for {@link Interface}.
	 * @see ResourceBundleResourceI18nConcern#DEFAULT
	 * @see ResourceBundleResourceI18nConcern#getResources(Class)
	 * @see Interface
	 */
	@Test
	public void testDefaultGetResourcesForInterface() {
		final Resources resources = ResourceBundleResourceI18nConcern.DEFAULT.getResources(Interface.class);
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
	 * @see ResourceBundleResourceI18nConcern#DEFAULT
	 * @see ResourceBundleResourceI18nConcern#getResources(Class)
	 * @see SubInterface
	 */
	@Test
	public void testDefaultGetResourcesForSubInterface() {
		final Resources resources = ResourceBundleResourceI18nConcern.DEFAULT.getResources(SubInterface.class);
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
	 * @see ResourceBundleResourceI18nConcern#DEFAULT
	 * @see ResourceBundleResourceI18nConcern#getResources(Class)
	 * @see AbstractImpl
	 */
	@Test
	public void testDefaultGetResourcesForAbstractImpl() {
		final Resources resources = ResourceBundleResourceI18nConcern.DEFAULT.getResources(AbstractImpl.class);
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
	 * @see ResourceBundleResourceI18nConcern#DEFAULT
	 * @see ResourceBundleResourceI18nConcern#getResources(Class)
	 * @see BaseImpl
	 */
	@Test
	public void testDefaultGetResourcesForBaseImpl() {
		final Resources resources = ResourceBundleResourceI18nConcern.DEFAULT.getResources(BaseImpl.class);
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
	 * @see ResourceBundleResourceI18nConcern#DEFAULT
	 * @see ResourceBundleResourceI18nConcern#getResources(Class)
	 * @see Impl
	 */
	@Test
	public void testDefaultGetResourcesForImpl() {
		final Resources resources = ResourceBundleResourceI18nConcern.DEFAULT.getResources(Impl.class);
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
