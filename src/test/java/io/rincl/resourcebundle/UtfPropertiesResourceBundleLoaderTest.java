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

import static java.util.stream.Collectors.*;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;

import java.io.*;
import java.util.*;
import org.junit.*;

/**
 * Tests for {@link UtfPropertiesResourceBundleLoader}.
 * @author Garret Wilson
 */
public class UtfPropertiesResourceBundleLoaderTest {

	/** @see UtfPropertiesResourceBundleLoader#getFilenameExtensionSuffixes() */
	@Test
	public void testGetFilenameExtensionSuffixes() {
		assertThat(UtfPropertiesResourceBundleLoader.INSTANCE.getFilenameExtensionSuffixes().collect(toSet()), containsInAnyOrder("properties"));
	}

	/**
	 * @see UtfPropertiesResourceBundleLoaderload(InputStream)
	 * @throws IOException if there is an error loading the test resources file.
	 */
	@Test
	public void testLoadPropertiesUtf8Bom() throws IOException {
		try (final InputStream inputStream = new BufferedInputStream(getClass().getResourceAsStream("test-utf-8-bom.properties"))) {
			final ResourceBundle resourceBundle = UtfPropertiesResourceBundleLoader.INSTANCE.load(inputStream);
			assertThat(resourceBundle.getString("test"), is("touché"));
		}
	}

	/**
	 * @see UtfPropertiesResourceBundleLoader#load(InputStream)
	 * @throws IOException if there is an error loading the test resources file.
	 */
	@Test
	public void testLoadPropertiesUtf8NoBom() throws IOException {
		try (final InputStream inputStream = new BufferedInputStream(getClass().getResourceAsStream("test-utf-8-no-bom.properties"))) {
			final ResourceBundle resourceBundle = UtfPropertiesResourceBundleLoader.INSTANCE.load(inputStream);
			assertThat(resourceBundle.getString("test"), is("touché"));
		}
	}

}
