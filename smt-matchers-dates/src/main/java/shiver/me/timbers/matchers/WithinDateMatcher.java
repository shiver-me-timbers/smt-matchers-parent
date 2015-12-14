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

    private final TimeOperations timeOperations;
    private final Date expected;
    private final Long duration;
    private final TimeUnit unit;

    public WithinDateMatcher(TimeOperations timeOperations, Date expected, Long duration, TimeUnit unit) {
        super(timeOperations, expected, duration, unit);
        this.timeOperations = timeOperations;
        this.expected = expected;
        this.duration = duration;
        this.unit = unit;
    }

    @Override
    protected boolean matchesTime(TimeOperations timeOperations, long expected, long duration, long actual) {
        return timeOperations.isAfter(expected - duration, actual)
            && timeOperations.isBefore(expected + duration, actual);
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
