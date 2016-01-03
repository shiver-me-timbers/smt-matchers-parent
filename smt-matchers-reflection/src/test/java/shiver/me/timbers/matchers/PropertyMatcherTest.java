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

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static shiver.me.timbers.data.random.RandomBooleans.someBoolean;
import static shiver.me.timbers.data.random.RandomStrings.someAlphaNumericString;
import static shiver.me.timbers.data.random.RandomStrings.someString;

public class PropertyMatcherTest {

    private Reflections reflections;
    private String property;
    private Matcher matcher;
    private PropertyMatcher<Object> propertyMatcher;

    @Before
    public void setUp() {
        reflections = mock(Reflections.class);
        property = someAlphaNumericString(8);
        matcher = mock(Matcher.class);
        propertyMatcher = new PropertyMatcher<>(reflections, property, matcher);
    }

    @Test
    public void Can_match_a_property() throws NoSuchPropertyException, IllegalAccessException {

        final AClass aClass = new AClass();
        final Object value = new Object();

        final boolean expected = someBoolean();

        // Given
        given(reflections.getPropertyValue(property, aClass)).willReturn(value);
        given(matcher.matches(value)).willReturn(expected);

        // When
        final boolean actual = propertyMatcher.matches(aClass);

        // Then
        assertThat(actual, is(expected));
    }

    @Test
    public void Can_get_a_default_description() throws NoSuchPropertyException, IllegalAccessException {

        final AClass aClass = new AClass();
        final Description description = mock(Description.class);

        final Description descriptionStart = mock(Description.class);
        final Description descriptionClassName = mock(Description.class);
        final Description descriptionMiddle = mock(Description.class);
        final Description descriptionProperty = mock(Description.class);
        final Description mismatchDescription = mock(Description.class);

        // Given
        given(description.appendText("class ")).willReturn(descriptionStart);
        given(descriptionStart.appendText(aClass.getClass().getName())).willReturn(descriptionClassName);
        given(descriptionClassName.appendText(" to contain the property ")).willReturn(descriptionMiddle);
        given(descriptionMiddle.appendText(property)).willReturn(descriptionProperty);
        given(descriptionProperty.appendText(" that is ")).willReturn(descriptionProperty);

        // When
        propertyMatcher.matches(aClass);
        propertyMatcher.describeTo(description);
        propertyMatcher.describeMismatch(aClass, mismatchDescription);

        // Then
        verify(descriptionProperty).appendDescriptionOf(matcher);
        verify(mismatchDescription).appendText("the property value does not match.");
    }

    @Test
    public void Can_fail_to_match_a_property_that_does_not_exist_and_give_a_meaningful_description()
        throws NoSuchPropertyException, IllegalAccessException {

        final AClass aClass = new AClass();
        final Description description = mock(Description.class);

        final NoSuchPropertyException exception = mock(NoSuchPropertyException.class);
        final Description descriptionStart = mock(Description.class);
        final Description descriptionClassName = mock(Description.class);
        final Description descriptionMiddle = mock(Description.class);
        final Description descriptionProperty = mock(Description.class);
        final Description mismatchDescription = mock(Description.class);
        final Description mismatchDescriptionStart = mock(Description.class);
        final String markedProperty = someString();
        final Description mismatchDescriptionMarkedProperty = mock(Description.class);
        final Description mismatchDescriptionMiddle = mock(Description.class);
        final Object object = new Object();
        final Description mismatchDescriptionEnd = mock(Description.class);

        // Given
        given(reflections.getPropertyValue(property, aClass)).willThrow(exception);
        given(description.appendText("class ")).willReturn(descriptionStart);
        given(descriptionStart.appendText(aClass.getClass().getName())).willReturn(descriptionClassName);
        given(descriptionClassName.appendText(" to contain the property ")).willReturn(descriptionMiddle);
        given(descriptionMiddle.appendText(property)).willReturn(descriptionProperty);
        given(mismatchDescription.appendText("the property was invalid at ")).willReturn(mismatchDescriptionStart);
        given(exception.getMarkedProperty()).willReturn(markedProperty);
        given(mismatchDescriptionStart.appendText(markedProperty)).willReturn(mismatchDescriptionMarkedProperty);
        given(mismatchDescriptionMarkedProperty.appendText(" in class ")).willReturn(mismatchDescriptionMiddle);
        given(exception.getErrorObject()).willReturn(object);
        given(mismatchDescriptionMiddle.appendText(object.getClass().getName())).willReturn(mismatchDescriptionEnd);

        // When
        propertyMatcher.matches(aClass);
        propertyMatcher.describeTo(description);
        propertyMatcher.describeMismatch(aClass, mismatchDescription);

        // Then
        verify(descriptionProperty).appendText(".");
        verify(mismatchDescriptionEnd).appendText(".");
    }

    private class AClass {
    }
}