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

import java.util.Optional;

import org.junit.*;

/**
 * Test base implemented methods of {@link BaseResources}.
 * @author Garret Wilson
 */
public class BaseResourcesTest {

	/** @see BaseResources#getOptionalString(String, Object...) */
	@Test
	public void testGetOptionalString() {
		//test direct resources access
		final BaseResources parentResources = mock(BaseResources.class, CALLS_REAL_METHODS);
		when(parentResources.getOptionalStringImpl("foo")).thenReturn(Optional.of("foo {0}"));
		assertThat(parentResources.getOptionalString("foo"), is(Optional.of("foo {0}")));
		assertThat(parentResources.getOptionalString("foo", "bar"), is(Optional.of("foo bar")));
		//test parent resources fallback; see RINCL-14
		final BaseResources childResources = mock(BaseResources.class, CALLS_REAL_METHODS);
		when(childResources.getParentResources()).thenReturn(Optional.of(parentResources));
		when(childResources.getOptionalStringImpl("foo")).thenReturn(Optional.empty());
		assertThat(childResources.getOptionalString("foo"), is(Optional.of("foo {0}")));
		assertThat(childResources.getOptionalString("foo", "bar"), is(Optional.of("foo bar")));
	}
}
