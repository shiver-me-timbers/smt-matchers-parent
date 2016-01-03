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
import org.hamcrest.TypeSafeMatcher;

import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * A matcher to check that a {@link Date} falls after another date.
 *
 * @author Karl Bennett
 */
public class AfterDateMatcher extends TypeSafeMatcher<Date> {

    /**
     * Check that the actual date is after the expected.
     */
    @Factory
    public static AfterDateMatcher fallsAfter(Date expected) {
        return new AfterDateMatcher(expected);
    }

    /**
     * Check that the actual date falls after the expected date within a supplied duration.
     * <p>
     * Note: This method is supplied to make the "within" feature more discoverable. It is also possible to call the
     * {@link AfterDateMatcher#within} method on the {@link AfterDateMatcher} returned by the {@link #fallsAfter(Date)}
     * method e.g. {@code assertThat(actual, fallsAfter(expected).within(duration, unit));}
     */
    public static AfterWithinDateMatcher fallsAfter(Date expected, Within within) {
        return AfterDateMatcher.fallsAfter(expected).within(within.getDuration(), within.getUnit());
    }

    private final TimeOperations timeOperations;
    private final Date expected;

    public AfterDateMatcher(Date expected) {
        this(new TimeOperations(), expected);
    }

    AfterDateMatcher(TimeOperations timeOperations, Date expected) {
        this.timeOperations = timeOperations;
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

    /**
     * Allow a duration after and including the expected date that the actual date may fall within.
     */
    public AfterWithinDateMatcher within(Long duration, TimeUnit unit) {
        return new AfterWithinDateMatcher(timeOperations, expected, duration, unit);
    }
}
