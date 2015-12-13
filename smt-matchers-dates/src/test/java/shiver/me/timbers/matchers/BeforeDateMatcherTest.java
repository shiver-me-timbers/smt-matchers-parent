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

import org.junit.Test;

import java.util.Date;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static shiver.me.timbers.data.random.RandomTimes.someTime;

public class BeforeDateMatcherTest {

    @Test
    public void Can_check_that_a_date_is_before_another_date() {

        // Given
        final Date date1 = someTime();
        final Date date2 = new Date(date1.getTime() - 1);

        // When
        final boolean actual = new BeforeDateMatcher(date1).matches(date2);

        // Then
        assertThat(actual, is(true));
    }

    @Test
    public void Can_check_that_a_date_is_not_before_another_date() {

        // Given
        final Date date1 = someTime();
        final Date date2 = new Date(date1.getTime() + 1);

        // When
        final boolean actual = new BeforeDateMatcher(date1).matches(date2);

        // Then
        assertThat(actual, is(false));
    }

    @Test
    public void Can_check_that_a_date_is_not_before_another_date_when_it_is_the_same_date() {

        // Given
        final Date date = someTime();

        // When
        final boolean actual = new BeforeDateMatcher(date).matches(date);

        // Then
        assertThat(actual, is(false));
    }
}