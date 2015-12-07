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
import java.util.concurrent.TimeUnit;

/**
 * An equality date specific matcher that can be further customised to better fit the testing scenario.
 *
 * @author Karl Bennett
 */
public class DateMatcher extends TypeSafeMatcher<Date> {

    private final TimeOperations timeOperations;
    private final Date expected;

    public DateMatcher(TimeOperations timeOperations, Date expected) {
        this.timeOperations = timeOperations;
        this.expected = expected;
    }

    @Override
    protected boolean matchesSafely(Date actual) {
        return expected.equals(actual);
    }

    @Override
    public void describeTo(Description description) {
        description.appendText("the date to be ").appendValue(expected);
    }

    /**
     * Allow a duration around the expected date that the actual date may fall within.
     */
    public WithinDateMatcher within(Long duration, TimeUnit unit) {
        return new WithinDateMatcher(timeOperations, expected, duration, unit);
    }
}
