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

package io.rincl;

import static org.junit.Assert.*;
import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.*;

import java.util.OptionalInt;
import java.util.OptionalLong;

import org.junit.*;

/**
 * Test default methods of {@link Resources}.
 * @author Garret Wilson
 */
public class ResourcesTest {

	/** @see Resources#findLong(String) */
	@Test
	public void testFindLong() {
		final Resources resources = mock(Resources.class, CALLS_REAL_METHODS);
		when(resources.findInt("foo")).thenReturn(OptionalInt.of(123));
		//the default version delegates to the int version
		assertThat(resources.findLong("foo"), is(OptionalLong.of(123L)));
	}

}
