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

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.util.Date;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static shiver.me.timbers.data.random.RandomLongs.somePositiveLong;

public class DateMatchersTest {

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Test
    public void Can_check_that_a_date_falls_on_another_date() {

        // Given
        final Long date = somePositiveLong();

        // When
        final boolean actual = DateMatchers.fallsOn(new Date(date)).matches(new Date(date));

        // Then
        assertThat(actual, is(true));
    }

    @Test
    public void Can_check_that_a_date_does_not_fall_on_another_date() {

        // Given
        final Date date1 = new Date(somePositiveLong());
        final Date date2 = new Date(somePositiveLong());

        // When
        final boolean actual = DateMatchers.fallsOn(date1).matches(date2);

        // Then
        assertThat(actual, is(false));
    }

    @Test
    public void Can_get_a_meaningful_assertion_error_message() {

        // Given
        final Date date1 = new Date(somePositiveLong());
        final Date date2 = new Date(somePositiveLong());
        expectedException.expect(AssertionError.class);
        expectedException.expectMessage(String.format("Expected: the date to be <%s>\n     but: was <%s>", date2, date1));

        // Then
        assertThat(date1, DateMatchers.fallsOn(date2));
    }
}