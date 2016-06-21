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
import java.net.*;
import java.util.*;

/**
 * Custom resource bundle control implementation that provides custom resource bundle loading for Rincl.
 * <p>
 * This implementation adds XML properties support.
 * </p>
 * @author Garret Wilson
 */
public class RinclResourceBundleControl extends ResourceBundle.Control {

	/** Singleton instance of Rincl resource bundle control. */
	public static final RinclResourceBundleControl INSTANCE = new RinclResourceBundleControl();

	/** This class cannot be publicly instantiated. */
	private RinclResourceBundleControl() {
	}

	/**
	 * {@inheritDoc} This implementation adds support for XML properties files.
	 * @see XmlPropertiesResourceBundleLoader
	 */
	@Override
	public List<String> getFormats(final String baseName) {
		//get the default supported formats
		final List<String> formats = new ArrayList<>(super.getFormats(baseName));
		//add each new format if it isn't already in our list
		XmlPropertiesResourceBundleLoader.INSTANCE.getFilenameExtensionSuffixes()
				//the list is probably so small that searching the list is faster than messing with a linked hash set
				.filter(format -> !formats.contains(format)) //TODO create negative predicate utility; see http://stackoverflow.com/a/28236099/421049
				.forEach(formats::add);
		return formats;
	}

	/**
	 * {@inheritDoc} This implementation adds support for XML properties files.
	 * @see XmlPropertiesResourceBundleLoader
	 */
	@Override
	public ResourceBundle newBundle(final String baseName, final Locale locale, final String format, final ClassLoader classLoader, final boolean reload)
			throws IllegalAccessException, InstantiationException, IOException {
		final ResourceBundleLoader resourceBundleLoader = XmlPropertiesResourceBundleLoader.INSTANCE; //TODO add support for other loaders
		//try all the filename extension suffixes supported by this loader
		for(final String extensionSuffix : (Iterable<String>)resourceBundleLoader.getFilenameExtensionSuffixes()::iterator) {
			if(extensionSuffix.equals(format)) { //if this is a format supported by this resource loader
				final String bundleName = toBundleName(baseName, locale);
				final String resourceName = toResourceName(bundleName, format);
				final URL url = classLoader.getResource(resourceName);
				if(url != null) {
					final URLConnection connection = url.openConnection();
					if(connection != null) {
						if(reload) {
							connection.setUseCaches(false);
						}
						try (final InputStream inputStream = connection.getInputStream()) {
							return resourceBundleLoader.load(inputStream);
						}
					}
				}
			}
		}
		//see if the default loading control supports the format
		return super.newBundle(baseName, locale, format, classLoader, reload);
	}

}
