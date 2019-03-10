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

import static java.nio.charset.StandardCharsets.*;
import static java.util.Collections.*;

import java.io.*;
import java.nio.charset.CharacterCodingException;
import java.util.*;

import com.globalmentor.io.BOMInputStreamReader;

/**
 * Strategy for loading resource bundles from properties files stored in standard properties format but using the UTF-8 charset by default, or any UTF-*
 * encoding (including UTF-8, UTF-16BE, UTF-16LE, UTF-32BE, and UTF-32LE) for which a Byte Order Mark (BOM) is present. The implementation throws an error if
 * the input stream does not contain valid UTF-* data; it does not fall back to ISO-8859-1.
 * <p>
 * This implementation recognizes files with the extension {@value #FILENAME_EXTENSION}, stored in the format specified in {@link Properties#load(Reader)}.
 * </p>
 * <p>
 * This implementation is not meant for normal registration, but is used directly by the Rincl resource bundle control to improve Java properties file loading.
 * </p>
 * @author Garret Wilson
 * @see Properties#load(Reader)
 */
public class UtfPropertiesResourceBundleLoader implements ResourceBundleLoader {

	/** The shared singleton instance of the class. */
	public static final UtfPropertiesResourceBundleLoader INSTANCE = new UtfPropertiesResourceBundleLoader();

	/** The supported filename extension. */
	public static final String FILENAME_EXTENSION = "properties";

	/** This class cannot be publicly instantiated. */
	protected UtfPropertiesResourceBundleLoader() {
	}

	@Override
	public Set<String> getFilenameExtensions() {
		return singleton(FILENAME_EXTENSION);
	}

	/**
	 * {@inheritDoc}
	 * @throws CharacterCodingException if the given input stream contains an invalid byte sequence for the charset indicated by the BOM; or if no BOM was
	 *           present, an invalid byte sequence for the UTF-8 charset.
	 */
	@Override
	public ResourceBundle load(final InputStream inputStream) throws IOException {
		//get a reader using the charset indicated by a BOM, if any; defaulting to UTF-8
		return new PropertyResourceBundle(new BOMInputStreamReader(inputStream, UTF_8));
	}

}
