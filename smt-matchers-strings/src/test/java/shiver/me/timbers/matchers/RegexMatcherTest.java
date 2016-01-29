/*
 * Copyright 2016 Karl Bennett
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

import org.junit.Test;

import static java.lang.String.format;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static shiver.me.timbers.data.random.RandomFloats.someFloat;
import static shiver.me.timbers.data.random.RandomIntegers.someInteger;
import static shiver.me.timbers.data.random.RandomStrings.someAlphaNumericString;

public class RegexMatcherTest {

    @Test
    public void Can_match_a_pattern_to_some_text() {

        // Given
        final String string = format("%.2f %4s %d", someFloat(), someAlphaNumericString(4), someInteger());

        // When
        final boolean actual = new RegexMatcher().matches("-?\\d+\\.\\d\\d [a-zA-Z0-9]{4} -?\\d+", string);

        // Then
        assertThat(actual, is(true));
    }
}