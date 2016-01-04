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

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static shiver.me.timbers.data.random.RandomLongs.someLong;

public class TimeOperationsTest {

    @Test
    public void Can_check_that_a_time_is_before_another_time() {

        // Given
        final Long expectedTime = someLong();
        final Long actualTime = expectedTime - 1;

        // When
        final boolean actual = new TimeOperations().isBeforeOrEqualTo(actualTime, expectedTime);

        // Then
        assertThat(actual, is(true));
    }

    @Test
    public void Can_check_that_a_time_is_equal_to_before_another_time() {

        // Given
        final Long expectedTime = someLong();

        // When
        final boolean actual = new TimeOperations().isBeforeOrEqualTo(expectedTime, expectedTime);

        // Then
        assertThat(actual, is(true));
    }

    @Test
    public void Can_check_that_a_time_is_not_before_another_time() {

        // Given
        final Long expectedTime = someLong();
        final Long actualTime = expectedTime + 1;

        // When
        final boolean actual = new TimeOperations().isBeforeOrEqualTo(actualTime, expectedTime);

        // Then
        assertThat(actual, is(false));
    }

    @Test
    public void Can_check_that_a_time_is_after_another_time() {

        // Given
        final Long expectedTime = someLong();
        final Long actualTime = expectedTime + 1;

        // When
        final boolean actual = new TimeOperations().isAfterOrEqualTo(actualTime, expectedTime);

        // Then
        assertThat(actual, is(true));
    }

    @Test
    public void Can_check_that_a_time_is_equal_to_after_another_time() {

        // Given
        final Long expectedTime = someLong();

        // When
        final boolean actual = new TimeOperations().isAfterOrEqualTo(expectedTime, expectedTime);

        // Then
        assertThat(actual, is(true));
    }

    @Test
    public void Can_check_that_a_time_is_not_after_another_time() {

        // Given
        final Long expectedTime = someLong();
        final Long actualTime = expectedTime - 1;

        // When
        final boolean actual = new TimeOperations().isAfterOrEqualTo(actualTime, expectedTime);

        // Then
        assertThat(actual, is(false));
    }
}