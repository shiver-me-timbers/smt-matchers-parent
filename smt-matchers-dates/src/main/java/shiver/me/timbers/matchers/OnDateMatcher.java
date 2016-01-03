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
 * A matcher to check the equality of a {@link Date} that can be further customised to better fit the testing scenario.
 *
 * @author Karl Bennett
 */
public class OnDateMatcher extends TypeSafeMatcher<Date> {

    /**
     * Check that the actual date is before the expected.
     */
    @Factory
    public static OnDateMatcher fallsOn(final Date expected) {
        return new OnDateMatcher(expected);
    }

    /**
     * Check that the actual date falls within the supplied duration before or after the expected date.
     * <p>
     * Note: This method is supplied to make the "within" feature more discoverable. It is also possible to call the
     * {@link OnDateMatcher#within} method on the {@link OnDateMatcher} returned by the {@link #fallsOn(Date)} method
     * e.g. {@code assertThat(actual, fallsOn(expected).within(duration, unit));}
     */
    public static WithinDateMatcher fallsOn(Date expected, Within within) {
        return OnDateMatcher.fallsOn(expected).within(within.getDuration(), within.getUnit());
    }

    private final TimeOperations timeOperations;
    private final Date expected;

    public OnDateMatcher(Date expected) {
        this(new TimeOperations(), expected);
    }

    OnDateMatcher(TimeOperations timeOperations, Date expected) {
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
