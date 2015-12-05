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
import org.hamcrest.TypeSafeMatcher;

import java.util.Date;

/**
 * Matchers that can be used to verify if a date is on or close to another specified date.
 *
 * @author Karl Bennett
 */
public class DateMatchers {

    public static Matcher<Date> fallsOn(final Date expected) {
        return new TypeSafeMatcher<Date>() {
            @Override
            protected boolean matchesSafely(Date actual) {
                return expected.equals(actual);
            }

            @Override
            public void describeTo(Description description) {
                description.appendText("the date to be ").appendValue(expected);
            }
        };
    }
}
