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
import static shiver.me.timbers.data.random.RandomStrings.someString;

public class FieldMatcherTest {

    private Reflections reflections;
    private String name;
    private Matcher matcher;
    private FieldMatcher<Object> fieldMatcher;

    @Before
    public void setUp() {
        reflections = mock(Reflections.class);
        name = someString();
        matcher = mock(Matcher.class);
        fieldMatcher = new FieldMatcher<>(reflections, name, matcher);
    }

    @Test
    public void Can_match_a_field() throws NoSuchFieldException, IllegalAccessException {

        final AClass aClass = new AClass();
        final Object value = new Object();

        final boolean expected = someBoolean();

        // Given
        given(reflections.getFieldValue(name, aClass)).willReturn(value);
        given(matcher.matches(value)).willReturn(expected);

        // When
        final boolean actual = fieldMatcher.matches(aClass);

        // Then
        assertThat(actual, is(expected));
    }

    @Test
    public void Can_get_a_default_description() throws NoSuchFieldException, IllegalAccessException {

        final AClass aClass = new AClass();
        final Description description = mock(Description.class);

        final Description descriptionStart = mock(Description.class);
        final Description descriptionClassName = mock(Description.class);
        final Description descriptionMiddle = mock(Description.class);
        final Description descriptionFieldName = mock(Description.class);
        final Description mismatchDescription = mock(Description.class);

        // Given
        given(description.appendText("class ")).willReturn(descriptionStart);
        given(descriptionStart.appendText(aClass.getClass().getName())).willReturn(descriptionClassName);
        given(descriptionClassName.appendText(" to contain a field named ")).willReturn(descriptionMiddle);
        given(descriptionMiddle.appendText(name)).willReturn(descriptionFieldName);
        given(descriptionFieldName.appendText(" that is ")).willReturn(descriptionFieldName);

        // When
        fieldMatcher.matches(aClass);
        fieldMatcher.describeTo(description);
        fieldMatcher.describeMismatch(aClass, mismatchDescription);

        // Then
        verify(descriptionFieldName).appendDescriptionOf(matcher);
        verify(mismatchDescription).appendText("the field does not match.");
    }

    @Test
    public void Can_fail_to_match_a_field_that_does_not_exist_and_give_a_meaningful_description()
        throws NoSuchFieldException, IllegalAccessException {

        final AClass aClass = new AClass();
        final Description description = mock(Description.class);
        final Description descriptionStart = mock(Description.class);
        final Description descriptionClassName = mock(Description.class);
        final Description descriptionMiddle = mock(Description.class);
        final Description descriptionFieldName = mock(Description.class);
        final Description mismatchDescription = mock(Description.class);

        // Given
        given(reflections.getFieldValue(name, aClass)).willThrow(new NoSuchFieldException());
        given(description.appendText("class ")).willReturn(descriptionStart);
        given(descriptionStart.appendText(aClass.getClass().getName())).willReturn(descriptionClassName);
        given(descriptionClassName.appendText(" to contain a field named ")).willReturn(descriptionMiddle);
        given(descriptionMiddle.appendText(name)).willReturn(descriptionFieldName);

        // When
        fieldMatcher.matches(aClass);
        fieldMatcher.describeTo(description);
        fieldMatcher.describeMismatch(aClass, mismatchDescription);

        // Then
        verify(descriptionFieldName).appendText(".");
        verify(mismatchDescription).appendText("the field does not exist.");
    }

    @Test
    public void Can_fail_to_match_a_field_that_is_inaccessible_and_give_a_meaningful_description()
        throws NoSuchFieldException, IllegalAccessException {

        final AClass aClass = new AClass();
        final Description description = mock(Description.class);
        final Description descriptionStart = mock(Description.class);
        final Description descriptionClassName = mock(Description.class);
        final Description descriptionMiddle = mock(Description.class);
        final Description descriptionFieldName = mock(Description.class);
        final Description mismatchDescription = mock(Description.class);

        // Given
        given(reflections.getFieldValue(name, aClass)).willThrow(new IllegalAccessException());
        given(description.appendText("to be able to access the field named ")).willReturn(descriptionStart);
        given(descriptionStart.appendText(name)).willReturn(descriptionFieldName);
        given(descriptionFieldName.appendText(" in class ")).willReturn(descriptionMiddle);
        given(descriptionMiddle.appendText(aClass.getClass().getName())).willReturn(descriptionClassName);

        // When
        fieldMatcher.matches(aClass);
        fieldMatcher.describeTo(description);
        fieldMatcher.describeMismatch(aClass, mismatchDescription);

        // Then
        verify(descriptionClassName).appendText(".");
        verify(mismatchDescription).appendText("the field is inaccessible.");
    }

    private class AClass {
    }
}