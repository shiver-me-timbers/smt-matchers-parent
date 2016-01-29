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

import org.hamcrest.Description;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static shiver.me.timbers.data.random.RandomBooleans.someBoolean;
import static shiver.me.timbers.data.random.RandomStrings.someString;

public class StringRegexMatcherTest {

    private RegexMatcher regexMatcher;
    private String pattern;
    private StringRegexMatcher matcher;

    @Before
    public void setUp() {
        regexMatcher = mock(RegexMatcher.class);
        pattern = someString();
        matcher = new StringRegexMatcher(regexMatcher, pattern);
    }

    @Test
    public void Can_check_that_a_string_matches_a_pattern() {

        final String string = someString();

        final Boolean expected = someBoolean();

        // Given
        given(regexMatcher.matches(pattern, string)).willReturn(expected);

        // When
        final boolean actual = matcher.matchesSafely(string);

        // Then
        assertThat(actual, is(expected));
    }

    @Test
    public void Can_fail_to_match_the_string_with_the_pattern_and_give_a_meaningful_description() {

        final String string = someString();
        final Description description = mock(Description.class);
        final Description descriptionStart = mock(Description.class);
        final Description descriptionPattern = mock(Description.class);

        // Given
        given(description.appendText("the string to match the pattern ")).willReturn(descriptionStart);
        given(descriptionStart.appendValue(pattern)).willReturn(descriptionPattern);

        // When
        matcher.matchesSafely(string);
        matcher.describeTo(description);

        // Then
        verify(descriptionPattern).appendText(".");
    }
}