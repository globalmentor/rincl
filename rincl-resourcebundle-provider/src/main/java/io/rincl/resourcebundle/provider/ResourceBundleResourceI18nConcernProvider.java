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

package io.rincl.resourcebundle.provider;

import static java.util.Collections.*;

import io.csar.*;
import io.rincl.resourcebundle.ResourceBundleResourceI18nConcern;

/**
 * Provides a default resource bundle-based concern for resource internationalization.
 * @author Garret Wilson
 */
public class ResourceBundleResourceI18nConcernProvider implements ConcernProvider {

	@Override
	public Iterable<Concern> getConcerns() {
		return singleton(new ResourceBundleResourceI18nConcern());
	}
}
