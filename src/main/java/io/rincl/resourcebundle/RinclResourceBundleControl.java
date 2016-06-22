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

import static java.util.Collections.*;
import static java.util.Objects.*;
import static java.util.stream.Collectors.*;
import static java.util.stream.StreamSupport.*;

import java.io.*;
import java.net.*;
import java.util.*;
import java.util.stream.Stream;

import javax.annotation.*;

/**
 * Custom resource bundle control implementation that provides custom resource bundle loading for Rincl.
 * <p>
 * This control implementation keeps a list of {@link ResourceBundleLoader} instances to support custom resource bundle types.
 * </p>
 * <p>
 * The {@link #DEFAULT} instance of this class uses {@link ServiceLoader} to load all registered types of resource bundle loaders, allowing for transparent and
 * automatic installation of resource bundle loaders by third parties. The service provider library will supply a text file
 * <code>META-INF/services/io.rincl.resourcebundle.ResourceBundleLoader</code> containing a line indicating the resource bundle loader class:
 * </p>
 * 
 * <pre>
 * <code>com.example.FooResourceBundleLoader</code>
 * </pre>
 * <p>
 * When the {@link #DEFAULT} instance of this resource bundle control class is first created, it will enumerate all resource bundle loaders so listed and
 * register them with the {@link #DEFAULT} instance, using the {@link ResourceBundleLoader#getFilenameExtensionSuffixes()} supplied by each as the supported
 * resource bundle format identifiers.
 * </p>
 * <p>
 * This library provides an {@link XmlPropertiesResourceBundleLoader} resource bundle loader, registered as a service provider using exactly the mechanism
 * described here.
 * </p>
 * @author Garret Wilson
 * @see ResourceBundleLoader
 */
public class RinclResourceBundleControl extends ResourceBundle.Control {

	/**
	 * Default instance of Rincl resource bundle control.
	 * <p>
	 * This instance loads all registered {@link ResourceBundleLoader} types using {@link ServiceLoader}.
	 * </p>
	 * <p>
	 * This library be default provides an {@link XmlPropertiesResourceBundleLoader} which will be registered for the
	 * {@value XmlPropertiesResourceBundleLoader#EXTENSION_SUFFIX} extension suffix.
	 * </p>
	 */
	public static final RinclResourceBundleControl DEFAULT;

	static {
		DEFAULT = new RinclResourceBundleControl(stream(ServiceLoader.load(ResourceBundleLoader.class).spliterator(), false));
	}

	/** The map of registered resource bundle loaders, associated with their format type identifiers. */
	private final Map<String, ResourceBundleLoader> formatResourceBundleLoaders;

	/**
	 * Resource bundle loaders constructor. The provided resource bundle loaders will be registered with this control instance.
	 * <p>
	 * The value returned by each {@link ResourceBundleLoader#getFilenameExtensionSuffixes()} will be used as format identifiers. If multiple resource bundle
	 * loaders indicate the same filename extension suffix, the last resource bundle loader will replace the others for that format. Otherwise the formats will be
	 * attempted in the order given, before the default Java supported formats are attempted.
	 * </p>
	 * @param resourceBundleLoaders The resource bundle loaders
	 */
	public RinclResourceBundleControl(@Nonnull final Stream<ResourceBundleLoader> resourceBundleLoaders) {
		//use a LinkedHashMap to remember the given order of resource bundle loaders
		final Map<String, ResourceBundleLoader> resourceBundleLoadersMap = new LinkedHashMap<>();
		resourceBundleLoaders.forEach(resourceBundleLoader -> {
			resourceBundleLoader.getFilenameExtensionSuffixes().forEach(format -> {
				resourceBundleLoadersMap.put(format, resourceBundleLoader);
			});
		});
		formatResourceBundleLoaders = unmodifiableMap(resourceBundleLoadersMap);
	}

	/**
	 * {@inheritDoc}
	 * <p>
	 * This implementation returns additional the formats supported by the registered {@link ResourceBundleLoader}s.
	 * </p>
	 */
	@Override
	public List<String> getFormats(final String baseName) {
		//get the default supported formats
		final List<String> formats = new ArrayList<>(super.getFormats(baseName));
		//add each new format if it isn't already in our list
		formatResourceBundleLoaders.keySet().stream()
				//the list is probably so small that searching the list is faster than messing with a linked hash set
				.filter(format -> !formats.contains(format)) //TODO create negative predicate utility; see http://stackoverflow.com/a/28236099/421049
				.forEach(formats::add);
		return formats;
	}

	/**
	 * {@inheritDoc}
	 * <p>
	 * This implementation adds support for additional formats using the registered {@link ResourceBundleLoader}s.
	 * </p>
	 */
	@Override
	public ResourceBundle newBundle(final String baseName, final Locale locale, final String format, final ClassLoader classLoader, final boolean reload)
			throws IllegalAccessException, InstantiationException, IOException {
		//see if we have a resource bundle registered for this format
		final ResourceBundleLoader resourceBundleLoader = formatResourceBundleLoaders.get(requireNonNull(format));
		if(resourceBundleLoader != null) {
			assert resourceBundleLoader.getFilenameExtensionSuffixes().collect(toSet()).contains(format) : "Resource bundle loader incorrectly registered.";
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
		//see if the default loading control supports the format
		return super.newBundle(baseName, locale, format, classLoader, reload);
	}

}
