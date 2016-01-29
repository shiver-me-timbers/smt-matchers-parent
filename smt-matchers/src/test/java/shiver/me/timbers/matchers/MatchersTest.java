/*
 * Copyright 2016 Karl Bennett
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
import java.util.concurrent.TimeUnit;

import static java.lang.String.format;
import static java.util.concurrent.TimeUnit.HOURS;
import static java.util.concurrent.TimeUnit.MILLISECONDS;
import static java.util.concurrent.TimeUnit.MINUTES;
import static java.util.concurrent.TimeUnit.SECONDS;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;
import static shiver.me.timbers.data.random.RandomEnums.someEnum;
import static shiver.me.timbers.data.random.RandomFloats.someFloat;
import static shiver.me.timbers.data.random.RandomIntegers.someInteger;
import static shiver.me.timbers.data.random.RandomLongs.someLong;
import static shiver.me.timbers.data.random.RandomLongs.someLongBetween;
import static shiver.me.timbers.data.random.RandomLongs.somePositiveLong;
import static shiver.me.timbers.data.random.RandomStrings.someAlphaNumericString;
import static shiver.me.timbers.data.random.RandomThings.someThing;
import static shiver.me.timbers.matchers.Matchers.fallsAfter;
import static shiver.me.timbers.matchers.Matchers.fallsBefore;
import static shiver.me.timbers.matchers.Matchers.fallsOn;
import static shiver.me.timbers.matchers.Matchers.hasField;
import static shiver.me.timbers.matchers.Matchers.hasFieldThat;
import static shiver.me.timbers.matchers.Matchers.hasProperty;
import static shiver.me.timbers.matchers.Matchers.hasPropertyThat;
import static shiver.me.timbers.matchers.Matchers.matches;
import static shiver.me.timbers.matchers.Within.within;

public class MatchersTest {

    @Test
    public void Instantiation_to_get_full_coverage() {
        new Matchers();
    }

    @Test
    public void Can_check_that_a_date_falls_before_and_close_to_another_date() {

        // Given
        final Long date1 = someLongBetween(0L, 1000L);
        final Long duration = someLongBetween(1L, 1000L);
        final TimeUnit unit = someThing(MILLISECONDS, SECONDS, MINUTES, HOURS);
        final Long durationInMilliseconds = unit.toMillis(duration);
        final Long difference = someLongBetween(1L, durationInMilliseconds);
        final Long date2 = date1 - difference;
        final Date expected = new Date(date1);
        final Date actual = new Date(date2);

        // Then
        assertThat(actual, fallsBefore(expected).within(duration, unit));
        assertThat(actual, fallsBefore(expected, within(duration, unit)));
    }

    @Test
    public void Can_check_that_a_date_falls_close_to_another_date() {

        // Given
        final Long date1 = somePositiveLong();
        final Long duration = someLongBetween(0L, 1000L);
        final TimeUnit unit = someEnum(TimeUnit.class);
        final Long durationInMilliseconds = unit.toMillis(duration);
        final Long difference = someLongBetween(-durationInMilliseconds, durationInMilliseconds + 1);
        final Long date2 = date1 + difference;
        final Date expected = new Date(date1);
        final Date actual = new Date(date2);

        // Then
        assertThat(actual, fallsOn(expected).within(duration, unit));
        assertThat(actual, fallsOn(expected, within(duration, unit)));
    }

    @Test
    public void Can_check_that_a_date_falls_close_to_after_another_date() {

        // Given
        final Long date1 = someLongBetween(0L, 1000L);
        final Long duration = someLongBetween(1L, 1000L);
        final TimeUnit unit = someThing(MILLISECONDS, SECONDS, MINUTES, HOURS);
        final Long durationInMilliseconds = unit.toMillis(duration);
        final Long difference = someLongBetween(1L, durationInMilliseconds);
        final Long date2 = date1 + difference;
        final Date expected = new Date(date1);
        final Date actual = new Date(date2);

        // Then
        assertThat(actual, fallsAfter(expected).within(duration, unit));
        assertThat(actual, fallsAfter(expected, within(duration, unit)));
    }

    @Test
    public void Can_apply_a_matcher_to_a_classes_field() {

        // Given
        final Long expected = someLong();
        class AClass {
            private final long fieldName = expected;
        }

        // Then
        assertThat(new AClass(), hasFieldThat("fieldName", equalTo(expected)));
    }

    @Test
    public void Can_check_that_a_classes_field_is_equal_to_a_value() {

        // Given
        final Long expected = someLong();
        class AClass {
            private final long fieldName = expected;
        }

        // Then
        assertThat(new AClass(), hasField("fieldName", expected));
    }

    @Test
    public void Can_apply_a_matcher_to_a_property() {

        // Given
        final Long expected = someLong();
        class AClass {
            private final long three = expected;
        }
        class BClass {
            private final AClass two = new AClass();
        }
        class CClass {
            private final BClass one = new BClass();
        }

        // Then
        assertThat(new CClass(), hasPropertyThat("one.two.three", equalTo(expected)));
    }

    @Test
    public void Can_check_that_a_property_is_equal_to_a_value() {

        // Given
        final Long expected = someLong();
        class AClass {
            private final long three = expected;
        }
        class BClass {
            private final AClass two = new AClass();
        }
        class CClass {
            private final BClass one = new BClass();
        }

        // Then
        assertThat(new CClass(), hasProperty("one.two.three", expected));
    }

    @Test
    public void Can_check_that_a_string_matches_the_supplied_pattern() {

        // Given
        final String actual = format("%d %.2f %s", someInteger(), someFloat(), someAlphaNumericString(4));

        // Then
        assertThat(actual, matches("-?\\d+ -?\\d+\\.\\d\\d [a-zA-Z0-9]{4}"));
    }
}