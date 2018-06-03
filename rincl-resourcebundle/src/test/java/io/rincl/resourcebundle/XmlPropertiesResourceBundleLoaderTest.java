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
 * Tests for {@link XmlPropertiesResourceBundleLoader}.
 * @author Garret Wilson
 */
public class XmlPropertiesResourceBundleLoaderTest {

	/** @see XmlPropertiesResourceBundleLoader#getFilenameExtensionSuffixes() */
	@Test
	public void testGetFilenameExtensionSuffixes() {
		assertThat(new XmlPropertiesResourceBundleLoader().getFilenameExtensionSuffixes().collect(toSet()), containsInAnyOrder("properties.xml"));
	}

	/**
	 * @see XmlPropertiesResourceBundleLoader#load(InputStream)
	 * @throws IOException if there is an error loading the test resources file.
	 */
	@Test
	public void testLoadXmlUtf8Bom() throws IOException {
		try (final InputStream inputStream = new BufferedInputStream(getClass().getResourceAsStream("test-utf-8-bom.properties.xml"))) {
			final ResourceBundle resourceBundle = new XmlPropertiesResourceBundleLoader().load(inputStream);
			assertThat(resourceBundle.getString("test"), is("touché"));
		}
	}

	/**
	 * @see XmlPropertiesResourceBundleLoader#load(InputStream)
	 * @throws IOException if there is an error loading the test resources file.
	 */
	@Test
	public void testLoadXmlUtf8NoBom() throws IOException {
		try (final InputStream inputStream = new BufferedInputStream(getClass().getResourceAsStream("test-utf-8-no-bom.properties.xml"))) {
			final ResourceBundle resourceBundle = new XmlPropertiesResourceBundleLoader().load(inputStream);
			assertThat(resourceBundle.getString("test"), is("touché"));
		}
	}

}
