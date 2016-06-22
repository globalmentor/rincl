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

import java.io.*;
import java.util.*;
import java.util.stream.Stream;

import com.globalmentor.util.HashMapResourceBundle;

/**
 * Strategy for loading resource bundles from properties files stored in XML format.
 * <p>
 * This implementation recognizes files with the extension suffix {@value #EXTENSION_SUFFIX}, stored in the XML schema recognized by
 * {@link Properties#loadFromXML(InputStream)}.
 * </p>
 * @author Garret Wilson
 * @see Properties#loadFromXML(InputStream)
 */
public class XmlPropertiesResourceBundleLoader implements ResourceBundleLoader {

	/** The supported extension suffix. */
	public static final String EXTENSION_SUFFIX = "properties.xml";

	@Override
	public Stream<String> getFilenameExtensionSuffixes() {
		return Stream.of(EXTENSION_SUFFIX);
	}

	@Override
	public ResourceBundle load(InputStream inputStream) throws IOException {
		final Properties properties = new Properties();
		properties.loadFromXML(inputStream);
		return new HashMapResourceBundle(properties);
	}

}
