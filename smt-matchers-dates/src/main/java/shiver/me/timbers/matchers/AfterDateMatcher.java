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
import org.hamcrest.TypeSafeMatcher;

import java.util.Date;

/**
 * A matcher to check that a {@link Date} falls after another date.
 *
 * @author Karl Bennett
 */
public class AfterDateMatcher extends TypeSafeMatcher<Date> {

    private final Date expected;

    public AfterDateMatcher(Date expected) {
        this.expected = expected;
    }

    @Override
    protected boolean matchesSafely(Date actual) {
        return actual.after(expected);
    }

    @Override
    public void describeTo(Description description) {
        description.appendText("the date to after ").appendValue(expected);
    }
}
