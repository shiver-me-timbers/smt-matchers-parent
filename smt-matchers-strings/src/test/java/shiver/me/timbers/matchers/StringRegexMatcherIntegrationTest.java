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

import com.github.mustachejava.DefaultMustacheFactory;
import com.github.mustachejava.Mustache;
import com.github.mustachejava.resolver.ClasspathResolver;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.io.StringWriter;
import java.util.HashMap;

import static java.lang.String.format;
import static org.junit.Assert.assertThat;
import static shiver.me.timbers.data.random.RandomFloats.someFloat;
import static shiver.me.timbers.data.random.RandomIntegers.someInteger;
import static shiver.me.timbers.data.random.RandomStrings.someAlphaNumericString;
import static shiver.me.timbers.data.random.RandomStrings.someString;
import static shiver.me.timbers.matchers.StringRegexMatcher.matches;

public class StringRegexMatcherIntegrationTest {

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    private Mustache noMatchErrorMessage;
    private StringWriter writer;

    @Before
    public void setUp() {
        final DefaultMustacheFactory mustacheFactory = new DefaultMustacheFactory(new ClasspathResolver());
        noMatchErrorMessage = mustacheFactory.compile("no-match-error-message.mustache");
        writer = new StringWriter();
    }

    @Test
    public void Can_check_that_a_string_matches_the_supplied_pattern() {

        // Given
        final String actual = format("%d %.2f %s", someInteger(), someFloat(), someAlphaNumericString(4));

        // Then
        assertThat(actual, matches("-?\\d+ -?\\d+\\.\\d\\d [a-zA-Z0-9]{4}"));
    }

    @Test
    public void Can_get_a_meaningful_assertion_error_message() {

        // Given
        final String pattern = "\\d+ \\d+";
        final String actual = someString(5);
        noMatchErrorMessage.execute(writer, new HashMap<String, String>() {{
            put("pattern", pattern);
            put("actual", actual);
        }});
        expectedException.expect(AssertionError.class);
        expectedException.expectMessage(writer.toString());

        // Then
        assertThat(actual, matches(pattern));
    }
}