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

import com.github.mustachejava.DefaultMustacheFactory;
import com.github.mustachejava.Mustache;
import com.github.mustachejava.resolver.ClasspathResolver;
import org.hamcrest.Description;
import org.hamcrest.TypeSafeMatcher;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.io.StringWriter;
import java.util.HashMap;

import static java.lang.String.format;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;
import static shiver.me.timbers.data.random.RandomLongs.someLong;
import static shiver.me.timbers.data.random.RandomStrings.someAlphaNumericString;
import static shiver.me.timbers.matchers.FieldMatcher.hasField;
import static shiver.me.timbers.matchers.FieldMatcher.hasFieldThat;

public class FieldMatcherIntegrationTest {

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    private Mustache invalidFieldErrorTemplate;
    private Mustache missingFieldErrorTemplate;
    private StringWriter writer;

    @Before
    public void setUp() {
        final DefaultMustacheFactory mustacheFactory = new DefaultMustacheFactory(new ClasspathResolver());
        invalidFieldErrorTemplate = mustacheFactory.compile("invalid-field-error-message.mustache");
        missingFieldErrorTemplate = mustacheFactory.compile("missing-field-error-message.mustache");
        writer = new StringWriter();
    }

    @Test
    public void Can_apply_a_matcher_to_a_classes_field() {

        // Given
        final Long expected = someLong();
        class AClass {
            private final long fieldName = expected;
        }
        final AClass object = new AClass();

        // Then
        assertThat(object, hasFieldThat("fieldName", equalTo(expected)));
    }

    @Test
    public void Can_get_a_meaningful_assertion_error_message_when_the_field_matcher_fails() {

        // Given
        final String fieldName = "fieldName";
        final String matcherError = someAlphaNumericString(10);
        final TypeSafeMatcher<Long> matches = new TypeSafeMatcher<Long>() {
            public void describeTo(Description description) {
                description.appendText(matcherError);
            }

            protected boolean matchesSafely(Long actual) {
                return false;
            }
        };
        class AClass {
            private Object fieldName;
        }
        final AClass object = new AClass();
        invalidFieldErrorTemplate.execute(writer, new HashMap<String, Object>() {{
            put("class", object.getClass().getName());
            put("name", fieldName);
            put("matcher", matcherError);
        }});
        expectedException.expect(AssertionError.class);
        expectedException.expectMessage(writer.toString());

        // Then
        assertThat(object, hasFieldThat(fieldName, matches));
    }

    @Test
    public void Can_get_a_meaningful_assertion_error_message_when_the_field_does_not_exist() {

        // Given
        final String fieldName = someAlphaNumericString(5);
        final TypeSafeMatcher<Long> matches = new TypeSafeMatcher<Long>() {
            public void describeTo(Description description) {
                throw new IllegalStateException("Should not be called.");
            }

            protected boolean matchesSafely(Long actual) {
                throw new IllegalStateException("Should not be called.");
            }
        };
        class AClass {
        }
        final AClass object = new AClass();
        missingFieldErrorTemplate.execute(writer, new HashMap<String, Object>() {{
            put("class", object.getClass().getName());
            put("name", fieldName);
        }});
        expectedException.expect(AssertionError.class);
        expectedException.expectMessage(writer.toString());

        // Then
        assertThat(object, hasFieldThat(fieldName, matches));
    }

    @Test
    public void Can_check_that_a_classes_field_is_equal_to_a_value() {

        // Given
        final Long expected = someLong();
        class AClass {
            private final long fieldName = expected;
        }

        // Then
        assertThat(new AClass(), hasField("fieldName", expected));
    }

    @Test
    public void Can_check_that_a_classes_field_is_not_equal_to_a_value() {

        // Given
        final String fieldName = "fieldName";
        class AClass {
            private final long fieldName;

            AClass(long fieldName) {
                this.fieldName = fieldName;
            }
        }
        final AClass object = new AClass(someLong());
        final Long expected = someLong();
        invalidFieldErrorTemplate.execute(writer, new HashMap<String, Object>() {{
            put("class", object.getClass().getName());
            put("name", fieldName);
            put("matcher", format("<%sL>", expected));
        }});
        expectedException.expect(AssertionError.class);
        expectedException.expectMessage(writer.toString());

        // Then
        assertThat(object, hasField(fieldName, expected));
    }
}