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

import java.io.IOException;
import java.util.*;

import org.junit.jupiter.api.*;

import static com.globalmentor.io.ClassResources.*;
import static io.rincl.resourcebundle.RinclResourceBundleControl.*;
import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

/**
 * Tests of {@link RinclResourceBundleControl}.
 * @author Garret Wilson
 */
public class RinclResourceBundleControlTest {

	/**
	 * Tests loading of the a properties file using the ISO-8859-1 charset.
	 * @see RinclResourceBundleControl#newBundle(String, Locale, String, ClassLoader, boolean)
	 * @see RinclResourceBundleControl#DEFAULT
	 * @see RinclResourceBundleControl#JAVA_PROPERTIES_FORMAT
	 */
	@Test
	public void testNewBundlePropertiesIso88591() throws IOException, InstantiationException, IllegalAccessException {
		final ResourceBundle resourceBundle = RinclResourceBundleControl.DEFAULT.newBundle(getClassLoaderResourcePath(getClass(), "test-iso-8859-1"), Locale.ROOT,
				JAVA_PROPERTIES_FORMAT, getClass().getClassLoader(), true);
		assertThat(resourceBundle.getString("test"), is("touché"));
	}

	/**
	 * Tests loading of the a properties file using the UTF-8 charset with no BOM.
	 * @see RinclResourceBundleControl#newBundle(String, Locale, String, ClassLoader, boolean)
	 * @see RinclResourceBundleControl#DEFAULT
	 * @see RinclResourceBundleControl#JAVA_PROPERTIES_FORMAT
	 */
	@Test
	public void testNewBundlePropertiesUtf8NoBom() throws IOException, InstantiationException, IllegalAccessException {
		final ResourceBundle resourceBundle = RinclResourceBundleControl.DEFAULT.newBundle(getClassLoaderResourcePath(getClass(), "test-utf-8-no-bom"), Locale.ROOT,
				JAVA_PROPERTIES_FORMAT, getClass().getClassLoader(), true);
		assertThat(resourceBundle.getString("test"), is("touché"));
	}

	/**
	 * Tests loading of the a properties file using the UTF-8 charset with BOM.
	 * @see RinclResourceBundleControl#newBundle(String, Locale, String, ClassLoader, boolean)
	 * @see RinclResourceBundleControl#DEFAULT
	 * @see RinclResourceBundleControl#JAVA_PROPERTIES_FORMAT
	 */
	@Test
	public void testNewBundlePropertiesUtf8Bom() throws IOException, InstantiationException, IllegalAccessException {
		final ResourceBundle resourceBundle = RinclResourceBundleControl.DEFAULT.newBundle(getClassLoaderResourcePath(getClass(), "test-utf-8-bom"), Locale.ROOT,
				JAVA_PROPERTIES_FORMAT, getClass().getClassLoader(), true);
		assertThat(resourceBundle.getString("test"), is("touché"));
	}

	/**
	 * Tests loading of the a properties file using the UTF-16BE charset with BOM.
	 * @see RinclResourceBundleControl#newBundle(String, Locale, String, ClassLoader, boolean)
	 * @see RinclResourceBundleControl#DEFAULT
	 * @see RinclResourceBundleControl#JAVA_PROPERTIES_FORMAT
	 */
	@Test
	public void testNewBundlePropertiesUtf16BeBom() throws IOException, InstantiationException, IllegalAccessException {
		final ResourceBundle resourceBundle = RinclResourceBundleControl.DEFAULT.newBundle(getClassLoaderResourcePath(getClass(), "test-utf-16be-bom"), Locale.ROOT,
				JAVA_PROPERTIES_FORMAT, getClass().getClassLoader(), true);
		assertThat(resourceBundle.getString("test"), is("touché"));
	}

	/**
	 * Tests loading of the a properties file using the UTF-16LE charset with BOM.
	 * @see RinclResourceBundleControl#newBundle(String, Locale, String, ClassLoader, boolean)
	 * @see RinclResourceBundleControl#DEFAULT
	 * @see RinclResourceBundleControl#JAVA_PROPERTIES_FORMAT
	 */
	@Test
	public void testNewBundlePropertiesUtf16LeBom() throws IOException, InstantiationException, IllegalAccessException {
		final ResourceBundle resourceBundle = RinclResourceBundleControl.DEFAULT.newBundle(getClassLoaderResourcePath(getClass(), "test-utf-16le-bom"), Locale.ROOT,
				JAVA_PROPERTIES_FORMAT, getClass().getClassLoader(), true);
		assertThat(resourceBundle.getString("test"), is("touché"));
	}

	/**
	 * Tests loading of the a properties file using the UTF-32BE charset with BOM.
	 * @see RinclResourceBundleControl#newBundle(String, Locale, String, ClassLoader, boolean)
	 * @see RinclResourceBundleControl#DEFAULT
	 * @see RinclResourceBundleControl#JAVA_PROPERTIES_FORMAT
	 */
	@Test
	public void testNewBundlePropertiesUtf32BeBom() throws IOException, InstantiationException, IllegalAccessException {
		final ResourceBundle resourceBundle = RinclResourceBundleControl.DEFAULT.newBundle(getClassLoaderResourcePath(getClass(), "test-utf-32be-bom"), Locale.ROOT,
				JAVA_PROPERTIES_FORMAT, getClass().getClassLoader(), true);
		assertThat(resourceBundle.getString("test"), is("touché"));
	}

	/**
	 * Tests loading of the a properties file using the UTF-32LE charset with BOM.
	 * @see RinclResourceBundleControl#newBundle(String, Locale, String, ClassLoader, boolean)
	 * @see RinclResourceBundleControl#DEFAULT
	 * @see RinclResourceBundleControl#JAVA_PROPERTIES_FORMAT
	 */
	@Test
	public void testNewBundlePropertiesUtf32LeBom() throws IOException, InstantiationException, IllegalAccessException {
		final ResourceBundle resourceBundle = RinclResourceBundleControl.DEFAULT.newBundle(getClassLoaderResourcePath(getClass(), "test-utf-32le-bom"), Locale.ROOT,
				JAVA_PROPERTIES_FORMAT, getClass().getClassLoader(), true);
		assertThat(resourceBundle.getString("test"), is("touché"));
	}

	/**
	 * Tests loading of the a properties file using the UTF-8 charset with no BOM.
	 * @see RinclResourceBundleControl#newBundle(String, Locale, String, ClassLoader, boolean)
	 * @see RinclResourceBundleControl#DEFAULT
	 * @see RinclResourceBundleControl#JAVA_PROPERTIES_FORMAT
	 */
	@Test
	public void testNewBundleXmlPropertiesUtf8NoBom() throws IOException, InstantiationException, IllegalAccessException {
		final ResourceBundle resourceBundle = RinclResourceBundleControl.DEFAULT.newBundle(getClassLoaderResourcePath(getClass(), "test-utf-8-no-bom"), Locale.ROOT,
				XmlPropertiesResourceBundleLoader.FILENAME_EXTENSION, getClass().getClassLoader(), true);
		assertThat(resourceBundle.getString("test"), is("touché"));
	}

	/**
	 * Tests loading of the a properties file using the UTF-8 charset with BOM.
	 * @see RinclResourceBundleControl#newBundle(String, Locale, String, ClassLoader, boolean)
	 * @see RinclResourceBundleControl#DEFAULT
	 * @see RinclResourceBundleControl#JAVA_PROPERTIES_FORMAT
	 */
	@Test
	public void testNewBundleXmlPropertiesUtf8Bom() throws IOException, InstantiationException, IllegalAccessException {
		final ResourceBundle resourceBundle = RinclResourceBundleControl.DEFAULT.newBundle(getClassLoaderResourcePath(getClass(), "test-utf-8-bom"), Locale.ROOT,
				XmlPropertiesResourceBundleLoader.FILENAME_EXTENSION, getClass().getClassLoader(), true);
		assertThat(resourceBundle.getString("test"), is("touché"));
	}

}
