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

import org.junit.Before;
import org.junit.Test;

import static java.lang.String.format;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static shiver.me.timbers.data.random.RandomIntegers.someIntegerBetween;
import static shiver.me.timbers.data.random.RandomStrings.someAlphaNumericString;

public class NoSuchPropertyExceptionTest {

    private String[] propertyNames;
    private String property;
    private int index;
    private Object original;
    private Object error;
    private NoSuchPropertyException noSuchPropertyException;

    @Before
    public void setUp() {
        propertyNames = new String[]{someAlphaNumericString(), someAlphaNumericString(), someAlphaNumericString()};
        property = format("%s.%s.%s", propertyNames);
        index = someIntegerBetween(0, propertyNames.length);
        original = new AClass();
        error = new BClass();
        noSuchPropertyException = new NoSuchPropertyException(
            property,
            index,
            original,
            error,
            new Exception()
        );
    }

    @Test
    public void Can_get_a_meaningful_message() {

        // Given
        final String markedProperty = buildMarkedProperty();

        // When
        final String actual = noSuchPropertyException.getMessage();

        // Then
        assertThat(actual, equalTo(
            format(
                "For class %s, property (%s) is invalid in composite class %s.",
                original.getClass().getName(),
                markedProperty,
                error.getClass().getName()
            )
        ));
    }

    @Test
    public void Can_get_the_property_name() {

        // When
        final String actual = noSuchPropertyException.getProperty();

        // Then
        assertThat(actual, equalTo(property));
    }

    @Test
    public void Can_get_a_marked_property_name() {

        // Given
        final String markedProperty = buildMarkedProperty();

        // When
        final String actual = noSuchPropertyException.getMarkedProperty();

        // Then
        assertThat(actual, equalTo(markedProperty));
    }

    @Test
    public void Can_get_the_missing_fields_name() {

        // When
        final String actual = noSuchPropertyException.getMissingField();

        // Then
        assertThat(actual, equalTo(propertyNames[index]));
    }

    @Test
    public void Can_get_the_original_object() {

        // When
        final Object actual = noSuchPropertyException.getOriginalObject();

        // Then
        assertThat(actual, is(original));
    }

    @Test
    public void Can_get_the_error_object() {

        // When
        final Object actual = noSuchPropertyException.getErrorObject();

        // Then
        assertThat(actual, is(error));
    }

    private String buildMarkedProperty() {
        final StringBuilder markedProperty = new StringBuilder(propertyNames[0]);
        for (int i = 1; i < propertyNames.length; i++) {
            if (i != index) markedProperty.append('.').append(propertyNames[i]);
            else markedProperty.append(".[").append(propertyNames[i]).append(']');
        }
        return markedProperty.toString();
    }

    private static class AClass {
    }

    private static class BClass {
    }
}