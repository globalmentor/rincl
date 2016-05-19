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

import static org.hamcrest.Matchers.*;
import org.junit.*;

/**
 * Tests of {@link ResourceBundleResourceI18nConcern}.
 * @author Garret Wilson
 */
public class ResourceBundleResourceI18ConcernTest {

	/**
	 * Happy path test of loading a resource bundle for {@link FooBar} .
	 * @see ResourceBundleResourceI18nConcern#DEFAULT
	 * @see FooBar
	 */
	@Test
	public void testDefaultGetResourceBundlesForFooBar() {
		final Map<Class<?>, ResourceBundle> resourceBundles = ResourceBundleResourceI18nConcern.DEFAULT.getResourceBundles(FooBar.class, Locale.US);
		assertThat(resourceBundles.size(), is(1));
		assertThat(resourceBundles.containsKey(FooBar.class), is(true));
		assertThat(resourceBundles.get(FooBar.class).getString("foo"), is("bar"));
	}

}
