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

import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * A matcher to check that a {@link Date} falls within a duration of another date.
 *
 * @author Karl Bennett
 */
public class WithinDateMatcher extends TimeOperationsDateMatcher {

    private final Date expected;
    private final Long duration;
    private final TimeUnit unit;

    public WithinDateMatcher(Date expected, Long duration, TimeUnit unit) {
        this(new TimeOperations(), expected, duration, unit);
    }

    WithinDateMatcher(TimeOperations timeOperations, Date expected, Long duration, TimeUnit unit) {
        super(timeOperations, duration, unit);
        this.expected = expected;
        this.duration = duration;
        this.unit = unit;
    }

    @Override
    protected boolean matchesTime(TimeOperations timeOperations, long duration, Date actual) {
        final long actualTime = actual.getTime();
        final long expectedTime = expected.getTime();
        return timeOperations.isAfterOrEqualTo(actualTime, expectedTime - duration)
            && timeOperations.isBeforeOrEqualTo(actualTime, expectedTime + duration);
    }

    @Override
    public void describeTo(Description description) {
        final long expectedTime = expected.getTime();
        final long durationInMillis = unit.toMillis(duration);
        description.appendText("the date to be within ")
            .appendValue(new Date(expectedTime - durationInMillis))
            .appendText(" and ")
            .appendValue(new Date(expectedTime + durationInMillis));
    }
}
