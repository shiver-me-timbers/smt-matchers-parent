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
 * A matcher to check that a {@link Date} falls before another date.
 *
 * @author Karl Bennett
 */
public class BeforeDateMatcher extends TypeSafeMatcher<Date> {

    /**
     * Check that the actual date is before the expected.
     */
    @Factory
    public static BeforeDateMatcher fallsBefore(Date expected) {
        return new BeforeDateMatcher(expected);
    }

    /**
     * Check that the actual date falls before the supplied expected date within a supplied duration.
     * <p>
     * Note: This method is supplied to make the "within" feature more discoverable. It is also possible to call the
     * {@link BeforeDateMatcher#within} method on the {@link BeforeDateMatcher} returned by the
     * {@link #fallsBefore(Date)} method e.g. {@code assertThat(actual, fallsBefore(expected).within(duration, unit));}
     */
    public static BeforeWithinDateMatcher fallsBefore(Date expected, Within within) {
        return BeforeDateMatcher.fallsBefore(expected).within(within.getDuration(), within.getUnit());
    }

    private final TimeOperations timeOperations;
    private final Date expected;

    public BeforeDateMatcher(Date expected) {
        this(new TimeOperations(), expected);
    }

    BeforeDateMatcher(TimeOperations timeOperations, Date expected) {
        this.timeOperations = timeOperations;
        this.expected = expected;
    }

    @Override
    protected boolean matchesSafely(Date actual) {
        return actual.before(expected);
    }

    @Override
    public void describeTo(Description description) {
        description.appendText("the date to before ").appendValue(expected);
    }

    /**
     * Allow a duration before and including the expected date that the actual date may fall within.
     */
    public BeforeWithinDateMatcher within(Long duration, TimeUnit unit) {
        return new BeforeWithinDateMatcher(timeOperations, expected, duration, unit);
    }
}
