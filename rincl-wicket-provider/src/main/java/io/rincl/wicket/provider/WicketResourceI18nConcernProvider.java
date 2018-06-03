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

package io.rincl.wicket.provider;

import java.util.stream.Stream;

import io.csar.*;
import io.rincl.wicket.*;

/**
 * Provides a default Wicket-based concern for resource internationalization, with fallback to resource bundles.
 * @author Garret Wilson
 */
public class WicketResourceI18nConcernProvider implements ConcernProvider {

	@Override
	public Stream<Concern> concerns() {
		return Stream.of(new WicketResourceI18nConcern());
	}

}
