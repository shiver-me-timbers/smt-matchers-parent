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
import org.hamcrest.Factory;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;

/**
 * Matches the value of a field with a given name within an object.
 *
 * @author Karl Bennett
 */
public class FieldMatcher<T> extends TypeSafeMatcher<T> {

    @Factory
    public static <T> FieldMatcher<T> hasFieldThat(String fieldName, Matcher matcher) {
        return new FieldMatcher<>(new Reflections(), fieldName, matcher);
    }

    private final Reflections reflections;
    private final String fieldName;
    private final Matcher matcher;
    private DescribeTo describeTo;
    private DescribeMismatch describeMismatch;

    public FieldMatcher(Reflections reflections, String fieldName, Matcher matcher) {
        this.reflections = reflections;
        this.fieldName = fieldName;
        this.matcher = matcher;
    }

    @Override
    protected boolean matchesSafely(final T actual) {
        try {
            describeFailedMatcher(actual);
            return matcher.matches(reflections.getFieldValue(fieldName, actual));
        } catch (NoSuchFieldException e) {
            describeMissingField(actual);
        } catch (IllegalAccessException e) {
            describeInaccessibleField(actual);
        }

        return false;
    }

    @Override
    public void describeTo(Description description) {
        describeTo.describeTo(description);
    }

    @Override
    protected void describeMismatchSafely(T item, Description mismatchDescription) {
        describeMismatch.describeMismatch(mismatchDescription);
    }

    private void describeFailedMatcher(final T actual) {
        assignDescribers(
            new DescribeTo() {
                public void describeTo(Description description) {
                    description.appendText("class ").appendText(actual.getClass().getName())
                        .appendText(" to contain a field named ").appendText(fieldName).appendText(" that is ")
                        .appendDescriptionOf(matcher);
                }
            },
            new DescribeMismatch() {
                @Override
                public void describeMismatch(Description description) {
                    description.appendText("the field does not match.");
                }
            }
        );
    }

    private void describeMissingField(final T actual) {
        assignDescribers(
            new DescribeTo() {
                public void describeTo(Description description) {
                    description.appendText("class ").appendText(actual.getClass().getName())
                        .appendText(" to contain a field named ").appendText(fieldName).appendText(".");
                }
            },
            new DescribeMismatch() {
                @Override
                public void describeMismatch(Description description) {
                    description.appendText("the field does not exist.");
                }
            }
        );
    }

    private void describeInaccessibleField(final T actual) {
        assignDescribers(
            new DescribeTo() {
                public void describeTo(Description description) {
                    description.appendText("to be able to access the filed named ").appendText(fieldName)
                        .appendText(" in class ").appendText(actual.getClass().getName()).appendText(".");
                }
            },
            new DescribeMismatch() {
                @Override
                public void describeMismatch(Description description) {
                    description.appendText("the field is inaccessible.");
                }
            }
        );
    }

    private void assignDescribers(DescribeTo describeTo, DescribeMismatch describeMismatch) {
        this.describeTo = describeTo;
        this.describeMismatch = describeMismatch;
    }

    private interface DescribeTo {
        void describeTo(Description description);
    }

    private interface DescribeMismatch {
        void describeMismatch(Description description);
    }
}
