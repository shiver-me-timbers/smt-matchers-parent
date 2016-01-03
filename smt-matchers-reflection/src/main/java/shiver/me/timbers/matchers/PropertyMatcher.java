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

/**
 * Matches the value of a given property (e.g. "one.two.three") that starts within an object.
 *
 * @author Karl Bennett
 */
public class PropertyMatcher<T> extends DescribingMatcher<T> {

    private final Reflections reflections;
    private final String property;
    private final Matcher matcher;

    /**
     * Check the that the properties (e.g. "one.two.three") value is valid for the supplied matcher.
     */
    @Factory
    public static <T> PropertyMatcher<T> hasPropertyThat(String property, Matcher matcher) {
        return new PropertyMatcher<>(property, matcher);
    }

    public PropertyMatcher(String property, Matcher matcher) {
        this(new Reflections(), property, matcher);
    }

    PropertyMatcher(Reflections reflections, String property, Matcher matcher) {
        this.reflections = reflections;
        this.property = property;
        this.matcher = matcher;
    }

    @Override
    protected boolean matchesSafely(T actual) {
        try {
            describeFailedMatcher(actual);
            return matcher.matches(reflections.getPropertyValue(property, actual));
        } catch (NoSuchPropertyException e) {
            describeMissingProperty(actual, e);
        }
        return false;
    }

    private void describeFailedMatcher(final T actual) {
        assignDescribers(
            new DescribeTo() {
                public void describeTo(Description description) {
                    description.appendText("class ").appendText(actual.getClass().getName())
                        .appendText(" to contain the property ").appendText(property).appendText(" that is ")
                        .appendDescriptionOf(matcher);
                }
            },
            new DescribeMismatch() {
                @Override
                public void describeMismatch(Description description) {
                    description.appendText("the property value does not match.");
                }
            }
        );
    }

    private void describeMissingProperty(final T actual, final NoSuchPropertyException e) {
        assignDescribers(
            new DescribeTo() {
                public void describeTo(Description description) {
                    description.appendText("class ").appendText(actual.getClass().getName())
                        .appendText(" to contain the property ").appendText(property).appendText(".");
                }
            },
            new DescribeMismatch() {
                @Override
                public void describeMismatch(Description description) {
                    description.appendText("the property was invalid at ").appendText(e.getMarkedProperty())
                        .appendText(" in class ").appendText(e.getErrorObject().getClass().getName()).appendText(".");
                }
            }
        );
    }
}
