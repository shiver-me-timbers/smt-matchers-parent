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

import org.hamcrest.Matcher;

import static org.hamcrest.CoreMatchers.equalTo;

/**
 * Matchers that can be used to verify, through reflection, that a class contains a matching field. The matcher will
 * check the current class and all it's super classes for the field.
 *
 * @author Karl Bennett
 */
public class ReflectionMatchers {

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
}
