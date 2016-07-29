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

import java.net.URI;
import java.util.Optional;

import org.junit.*;

/**
 * Test base implemented methods of {@link AbstractStringResources}.
 * @author Garret Wilson
 */
public class AbstractStringResourcesTest {

	/** @see AbstractStringResources#getOptionalDouble(String) */
	@Test
	public void testGetOptionalDouble() {
		final AbstractStringResources resources = mock(AbstractStringResources.class, CALLS_REAL_METHODS);
		when(resources.getOptionalStringImpl("foo")).thenReturn(Optional.of("1.23"));
		assertThat(resources.getOptionalDouble("foo"), is(Optional.of(1.23)));
	}

	/** @see AbstractStringResources#getOptionalInt(String) */
	@Test
	public void testGetOptionalInt() {
		final AbstractStringResources resources = mock(AbstractStringResources.class, CALLS_REAL_METHODS);
		when(resources.getOptionalStringImpl("foo")).thenReturn(Optional.of("123"));
		assertThat(resources.getOptionalInt("foo"), is(Optional.of(123)));
	}

	/** @see AbstractStringResources#getOptionalLong(String) */
	@Test
	public void testGetOptionalLong() {
		final AbstractStringResources resources = mock(AbstractStringResources.class, CALLS_REAL_METHODS);
		when(resources.getOptionalStringImpl("foo")).thenReturn(Optional.of("123456789"));
		assertThat(resources.getOptionalLong("foo"), is(Optional.of(123456789L)));
	}

	/** @see AbstractStringResources#getOptionalUri(String) */
	@Test
	public void testGetOptionalUri() {
		final AbstractStringResources resources = mock(AbstractStringResources.class, CALLS_REAL_METHODS);
		when(resources.getOptionalStringImpl("foo")).thenReturn(Optional.of("http://example.com/bar"));
		assertThat(resources.getOptionalUri("foo"), is(Optional.of(URI.create("http://example.com/bar"))));
	}

}
