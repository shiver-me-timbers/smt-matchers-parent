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

import org.hamcrest.Matcher;

import java.util.Date;

import static org.hamcrest.CoreMatchers.equalTo;

/**
 * @author Karl Bennett
 */
public class Matchers {

    /**
     * Check that the actual date is before the expected.
     */
    public static BeforeDateMatcher fallsBefore(Date expected) {
        return BeforeDateMatcher.fallsBefore(expected);
    }

    /**
     * Check that the actual date falls before the expected date within a supplied duration.
     * <p>
     * Note: This method is supplied to make the "within" feature more discoverable. It is also possible to call the
     * {@link BeforeDateMatcher#within} method on the {@link BeforeDateMatcher} returned by the
     * {@link #fallsBefore(Date)} method e.g. {@code assertThat(actual, fallsBefore(expected).within(duration, unit));}
     */
    public static BeforeWithinDateMatcher fallsBefore(Date expected, Within within) {
        return BeforeDateMatcher.fallsBefore(expected, within);
    }

    /**
     * Check that the actual date is equal to the expected.
     */
    public static OnDateMatcher fallsOn(final Date expected) {
        return OnDateMatcher.fallsOn(expected);
    }

    /**
     * Check that the actual date falls within the supplied duration before or after the expected date.
     * <p>
     * Note: This method is supplied to make the "within" feature more discoverable. It is also possible to call the
     * {@link OnDateMatcher#within} method on the {@link OnDateMatcher} returned by the {@link #fallsOn(Date)} method
     * e.g. {@code assertThat(actual, fallsOn(expected).within(duration, unit));}
     */
    public static WithinDateMatcher fallsOn(Date expected, Within within) {
        return OnDateMatcher.fallsOn(expected, within);
    }

    /**
     * Check that the actual date is after the expected.
     */
    public static AfterDateMatcher fallsAfter(Date expected) {
        return AfterDateMatcher.fallsAfter(expected);
    }

    /**
     * Check that the actual date falls after the expected date within a supplied duration.
     * <p>
     * Note: This method is supplied to make the "within" feature more discoverable. It is also possible to call the
     * {@link AfterDateMatcher#within} method on the {@link AfterDateMatcher} returned by the {@link #fallsAfter(Date)}
     * method e.g. {@code assertThat(actual, fallsAfter(expected).within(duration, unit));}
     */
    public static AfterWithinDateMatcher fallsAfter(Date expected, Within within) {
        return AfterDateMatcher.fallsAfter(expected, within);
    }

    /**
     * Check the that the named fields value is valid for the supplied matcher.
     */
    public static <T> FieldMatcher<T> hasFieldThat(String fieldName, Matcher matcher) {
        return FieldMatcher.hasFieldThat(fieldName, matcher);
    }

    /**
     * Check the that the named fields value matches the supplied value.
     */
    public static <T> FieldMatcher<T> hasField(String fieldName, Object expected) {
        return FieldMatcher.hasFieldThat(fieldName, equalTo(expected));
    }

    /**
     * Check the that the properties (e.g. "one.two.three") value is valid for the supplied matcher.
     */
    public static <T> PropertyMatcher<T> hasPropertyThat(String property, Matcher matcher) {
        return PropertyMatcher.hasPropertyThat(property, matcher);
    }

    /**
     * Check the that the properties (e.g. "one.two.three") value matches the supplied value.
     */
    public static <T> PropertyMatcher<T> hasProperty(String property, Object expected) {
        return PropertyMatcher.hasPropertyThat(property, equalTo(expected));
    }

    /**
     * Check the that the regex pattern matches the supplied value.
     */
    public static Matcher<String> matches(String pattern) {
        return StringRegexMatcher.matches(pattern);
    }
}
