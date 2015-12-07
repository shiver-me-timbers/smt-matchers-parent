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

import org.hamcrest.TypeSafeMatcher;

import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * @author Karl Bennett
 */
public abstract class TimeOperationsDateMatcher extends TypeSafeMatcher<Date> {

    private final TimeOperations timeOperations;
    private final long expectedTime;
    private final long durationInMillis;

    public TimeOperationsDateMatcher(TimeOperations timeOperations, Date expected, Long duration, TimeUnit unit) {
        this.timeOperations = timeOperations;
        this.expectedTime = expected.getTime();
        this.durationInMillis = unit.toMillis(duration);
    }

    @Override
    protected boolean matchesSafely(Date actual) {
        final long actualTime = actual.getTime();
        return matchesTime(timeOperations, expectedTime, durationInMillis, actualTime);
    }

    protected abstract boolean matchesTime(TimeOperations timeOperations, long expected, long duration, long actual);
}
