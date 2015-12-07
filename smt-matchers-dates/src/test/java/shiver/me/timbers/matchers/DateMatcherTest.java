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
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static shiver.me.timbers.data.random.RandomEnums.someEnum;
import static shiver.me.timbers.data.random.RandomLongs.someLong;

public class DateMatcherTest {

    private TimeOperations timeOperations;

    @Before
    public void setUp() {
        timeOperations = mock(TimeOperations.class);
    }

    @Test
    public void Can_check_that_two_dates_are_equal() {

        // Given
        final Date date = mock(Date.class);

        // When
        final boolean actual = new DateMatcher(timeOperations, date).matches(date);

        // Then
        assertThat(actual, is(true));
    }

    @Test
    public void Can_check_that_two_dates_are_not_equal() {

        // Given
        final Date date1 = mock(Date.class);
        final Date date2 = mock(Date.class);

        // When
        final boolean actual = new DateMatcher(timeOperations, date1).matches(date2);

        // Then
        assertThat(actual, is(false));
    }

    @Test
    public void Can_create_a_within_date_matcher() {

        // Given
        final Date date = mock(Date.class);
        final Long duration = someLong();
        final TimeUnit unit = someEnum(TimeUnit.class);

        // When
        final WithinDateMatcher actual = new DateMatcher(timeOperations, date).within(duration, unit);

        // Then
        assertThat(actual, not(nullValue()));
    }
}