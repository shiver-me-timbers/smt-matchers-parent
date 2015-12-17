/*
 * Copyright 2015 Karl Bennett
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

package shiver.me.timbers.matchers;

import org.hamcrest.Matcher;
import org.junit.Test;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static shiver.me.timbers.data.random.RandomBooleans.someBoolean;
import static shiver.me.timbers.data.random.RandomStrings.someAlphaNumericString;

public class PropertyMatcherTest {

    @Test
    public void Can_match_a_property() throws NoSuchFieldException, IllegalAccessException {

        final Reflections reflections = mock(Reflections.class);
        final String property = someAlphaNumericString(8);
        final Matcher matcher = mock(Matcher.class);

        class AClass {
        }
        final AClass aClass = new AClass();
        final Object value = new Object();

        final boolean expected = someBoolean();

        // Given
        given(reflections.getPropertyValue(property, aClass)).willReturn(value);
        given(matcher.matches(value)).willReturn(expected);

        // When
        final boolean actual = new PropertyMatcher<>(reflections, property, matcher).matches(aClass);

        // Then
        assertThat(actual, is(expected));
    }
}