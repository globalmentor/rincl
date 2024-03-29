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

import static java.util.Arrays.*;
import static java.util.stream.Collectors.*;
import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

import org.junit.jupiter.api.*;

/**
 * Tests of {@link ResolvingClassStrategy}.
 * @author Garret Wilson
 */
public class ResolvingClassStrategyTest {

	/**
	 * @see ResolvingClassStrategy#DEFAULT
	 * @see ResolvingClassStrategy#resolvingClasses(Class)
	 */
	@Test
	public void testDefaultResolvingClasses() {
		assertThat(ResolvingClassStrategy.DEFAULT.resolvingClasses(Impl.class).collect(toList()),
				is(asList(Impl.class, BaseImpl.class, AbstractImpl.class, SubInterface.class, Interface.class)));
	}

}
