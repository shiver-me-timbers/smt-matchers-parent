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

import org.junit.Before;
import org.junit.Test;

import java.util.Date;
import java.util.concurrent.TimeUnit;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static shiver.me.timbers.data.random.RandomEnums.someEnum;
import static shiver.me.timbers.data.random.RandomLongs.someLong;

public class WithinDateMatcherTest {

    private TimeOperations timeOperations;

    @Before
    public void setUp() {
        timeOperations = mock(TimeOperations.class);
    }

    @Test
    public void Can_check_that_a_date_is_within_a_range_of_another_date() {

        final Date date1 = mock(Date.class);
        final Date date2 = mock(Date.class);
        final Long duration = someLong();
        final TimeUnit unit = someEnum(TimeUnit.class);

        final long durationInMillis = unit.toMillis(duration);

        final long dateTime1 = someLong();
        final long dateTime2 = someLong();

        // Given
        given(date1.getTime()).willReturn(dateTime1);
        given(date2.getTime()).willReturn(dateTime2);
        given(timeOperations.isAfter(dateTime1 - durationInMillis, dateTime2)).willReturn(true);
        given(timeOperations.isBefore(dateTime1 + durationInMillis, dateTime2)).willReturn(true);

        // When
        final boolean actual = new WithinDateMatcher(timeOperations, date1, duration, unit).matches(date2);

        // Then
        assertThat(actual, is(true));
    }

    @Test
    public void Can_check_that_a_date_is_before_a_range_of_another_date() {

        final Date date1 = mock(Date.class);
        final Date date2 = mock(Date.class);
        final Long duration = someLong();
        final TimeUnit unit = someEnum(TimeUnit.class);

        final long durationInMillis = unit.toMillis(duration);

        final long dateTime1 = someLong();
        final long dateTime2 = someLong();

        // Given
        given(date1.getTime()).willReturn(dateTime1);
        given(date2.getTime()).willReturn(dateTime2);
        given(timeOperations.isAfter(dateTime1 - durationInMillis, dateTime2)).willReturn(false);
        given(timeOperations.isBefore(dateTime1 + durationInMillis, dateTime2)).willReturn(true);

        // When
        final boolean actual = new WithinDateMatcher(timeOperations, date1, duration, unit).matches(date2);

        // Then
        assertThat(actual, is(false));
    }

    @Test
    public void Can_check_that_a_date_is_after_a_range_of_another_date() {

        final Date date1 = mock(Date.class);
        final Date date2 = mock(Date.class);
        final Long duration = someLong();
        final TimeUnit unit = someEnum(TimeUnit.class);

        final long durationInMillis = unit.toMillis(duration);

        final long dateTime1 = someLong();
        final long dateTime2 = someLong();

        // Given
        given(date1.getTime()).willReturn(dateTime1);
        given(date2.getTime()).willReturn(dateTime2);
        given(timeOperations.isAfter(dateTime1 - durationInMillis, dateTime2)).willReturn(true);
        given(timeOperations.isBefore(dateTime1 + durationInMillis, dateTime2)).willReturn(false);

        // When
        final boolean actual = new WithinDateMatcher(timeOperations, date1, duration, unit).matches(date2);

        // Then
        assertThat(actual, is(false));
    }
}