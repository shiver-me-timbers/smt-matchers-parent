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

    @Factory
    public static <T> PropertyMatcher<T> hasPropertyThat(String property, Matcher matcher) {
        return new PropertyMatcher<>(new Reflections(), property, matcher);
    }

    public PropertyMatcher(Reflections reflections, String property, Matcher matcher) {
        this.reflections = reflections;
        this.property = property;
        this.matcher = matcher;
    }

    @Override
    protected boolean matchesSafely(T actual) {
        try {
            describeFailedMatcher(actual);
            return matcher.matches(reflections.getPropertyValue(property, actual));
        } catch (NoSuchFieldException e) {
            describeMissingField(actual);
        } catch (IllegalAccessException e) {
            describeInaccessibleField(actual);
        }
        return false;
    }

    private void describeFailedMatcher(final T actual) {
        assignDescribers(
            new DescribeTo() {
                public void describeTo(Description description) {
                    throw new UnsupportedOperationException();
                }
            },
            new DescribeMismatch() {
                @Override
                public void describeMismatch(Description description) {
                    throw new UnsupportedOperationException();
                }
            }
        );
    }

    private void describeMissingField(final T actual) {
        assignDescribers(
            new DescribeTo() {
                public void describeTo(Description description) {
                    throw new UnsupportedOperationException();
                }
            },
            new DescribeMismatch() {
                @Override
                public void describeMismatch(Description description) {
                    throw new UnsupportedOperationException();
                }
            }
        );
    }

    private void describeInaccessibleField(final T actual) {
        assignDescribers(
            new DescribeTo() {
                public void describeTo(Description description) {
                    throw new UnsupportedOperationException();
                }
            },
            new DescribeMismatch() {
                @Override
                public void describeMismatch(Description description) {
                    throw new UnsupportedOperationException();
                }
            }
        );
    }
}
