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

import static java.util.Collections.*;

import java.io.*;
import java.util.*;

import com.globalmentor.util.HashMapResourceBundle;

/**
 * Strategy for loading resource bundles from properties files stored in XML format.
 * <p>
 * This implementation recognizes files with the extension {@value #FILENAME_EXTENSION}, stored in the XML schema recognized by
 * {@link Properties#loadFromXML(InputStream)}.
 * </p>
 * @author Garret Wilson
 * @see Properties#loadFromXML(InputStream)
 */
public class XmlPropertiesResourceBundleLoader implements ResourceBundleLoader {

	/** The supported filename extension. */
	public static final String FILENAME_EXTENSION = "properties.xml";

	@Override
	public Set<String> getFilenameExtensions() {
		return singleton(FILENAME_EXTENSION);
	}

	@Override
	public ResourceBundle load(final InputStream inputStream) throws IOException {
		final Properties properties = new Properties();
		properties.loadFromXML(inputStream);
		return new HashMapResourceBundle(properties);
	}

}
