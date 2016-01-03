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

import com.github.mustachejava.DefaultMustacheFactory;
import com.github.mustachejava.Mustache;
import com.github.mustachejava.resolver.ClasspathResolver;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.io.StringWriter;
import java.util.Date;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertThat;
import static shiver.me.timbers.data.random.RandomBooleans.someBoolean;
import static shiver.me.timbers.data.random.RandomEnums.someEnum;
import static shiver.me.timbers.data.random.RandomLongs.someLongBetween;
import static shiver.me.timbers.data.random.RandomLongs.somePositiveLong;
import static shiver.me.timbers.data.random.RandomTimes.someTime;
import static shiver.me.timbers.matchers.DateMatchers.fallsAfter;
import static shiver.me.timbers.matchers.DateMatchers.fallsBefore;
import static shiver.me.timbers.matchers.DateMatchers.fallsOn;
import static shiver.me.timbers.matchers.Within.within;

public class DateMatchersTest {

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    private Mustache onErrorTemplate;
    private Mustache withinErrorTemplate;
    private Mustache beforeWithinErrorTemplate;
    private Mustache afterWithinErrorTemplate;
    private Mustache beforeErrorTemplate;
    private Mustache afterErrorTemplate;
    private StringWriter writer;

    @Before
    public void setUp() {
        final DefaultMustacheFactory mustacheFactory = new DefaultMustacheFactory(new ClasspathResolver());
        onErrorTemplate = mustacheFactory.compile("invalid-date-error-message.mustache");
        withinErrorTemplate = mustacheFactory.compile("invalid-within-date-error-message.mustache");
        beforeWithinErrorTemplate = mustacheFactory.compile("invalid-before-within-date-error-message.mustache");
        afterWithinErrorTemplate = mustacheFactory.compile("invalid-after-within-date-error-message.mustache");
        beforeErrorTemplate = mustacheFactory.compile("invalid-before-date-error-message.mustache");
        afterErrorTemplate = mustacheFactory.compile("invalid-after-date-error-message.mustache");
        writer = new StringWriter();
    }

    @Test
    public void Instantiation_to_get_full_coverage() {
        new DateMatchers();
    }

    @Test
    public void Can_check_that_a_date_falls_on_another_date() {

        // Given
        final Date date = someTime();

        // Then
        assertThat(date, fallsOn(date));
    }

    @Test
    public void Can_get_a_meaningful_assertion_error_message() {

        // Given
        final Date expected = someTime();
        final Date actual = someTime();
        onErrorTemplate.execute(writer, new HashMap<String, Date>() {{
            put("expected", expected);
            put("actual", actual);
        }});
        expectedException.expect(AssertionError.class);
        expectedException.expectMessage(writer.toString());

        // Then
        assertThat(actual, fallsOn(expected));
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
    public void Can_check_that_a_date_falls_too_far_from_another_date() {

        // Given
        final Long date1 = somePositiveLong();
        final Long duration = someLongBetween(0L, 1000L);
        final TimeUnit unit = someEnum(TimeUnit.class);
        final long durationInMillis = unit.toMillis(duration);
        final Long difference = durationInMillis + 1;
        final boolean positive = someBoolean();
        final Long date2 = date1 + (positive ? difference : -difference);
        final Date expected = new Date(date1);
        final Date actual = new Date(date2);
        final long expectedTime = expected.getTime();
        withinErrorTemplate.execute(writer, new HashMap<String, Date>() {{
            put("start", new Date(expectedTime - durationInMillis));
            put("end", new Date(expectedTime + durationInMillis));
            put("actual", actual);
        }});
        expectedException.expect(AssertionError.class);
        expectedException.expectMessage(writer.toString());

        // Then
        assertThat(actual, fallsOn(expected).within(duration, unit));
    }

    @Test
    public void Can_check_that_a_date_falls_before_another_date() {

        // Given
        final Date expected = someTime();
        final Date actual = new Date(expected.getTime() - 1);

        // Then
        assertThat(actual, fallsBefore(expected));
    }

    @Test
    public void Can_check_that_a_date_does_not_fall_before_another_date() {

        // Given
        final Date expected = someTime();
        final Date actual = new Date(expected.getTime() + 1);
        beforeErrorTemplate.execute(writer, new HashMap<String, Object>() {{
            put("expected", expected);
            put("actual", actual);
        }});
        expectedException.expect(AssertionError.class);
        expectedException.expectMessage(writer.toString());

        // Then
        assertThat(actual, fallsBefore(expected));
    }

    @Test
    public void Can_check_that_a_date_falls_before_and_close_to_another_date() {

        // Given
        final Long date1 = somePositiveLong();
        final Long duration = someLongBetween(0L, 1000L);
        final TimeUnit unit = someEnum(TimeUnit.class);
        final Long durationInMilliseconds = unit.toMillis(duration);
        final Long difference = someLongBetween(0L, durationInMilliseconds);
        final Long date2 = date1 - difference;
        final Date expected = new Date(date1);
        final Date actual = new Date(date2);

        // Then
        assertThat(actual, fallsBefore(expected).within(duration, unit));
        assertThat(actual, fallsBefore(expected, within(duration, unit)));
    }

    @Test
    public void Can_check_that_a_date_falls_too_far_before_another_date() {

        // Given
        final Long date1 = somePositiveLong();
        final Long duration = someLongBetween(0L, 1000L);
        final TimeUnit unit = someEnum(TimeUnit.class);
        final long durationInMillis = unit.toMillis(duration);
        final Long difference = durationInMillis + 1;
        final Long date2 = date1 - difference;
        final Date expected = new Date(date1);
        final Date actual = new Date(date2);
        beforeWithinErrorTemplate.execute(writer, new HashMap<String, Object>() {{
            put("duration", duration);
            put("unit", unit);
            put("expected", expected);
            put("actual", actual);
        }});
        expectedException.expect(AssertionError.class);
        expectedException.expectMessage(writer.toString());

        // Then
        assertThat(actual, fallsBefore(expected).within(duration, unit));
    }

    @Test
    public void Can_check_that_a_date_falls_after_another_date() {

        // Given
        final Date expected = someTime();
        final Date actual = new Date(expected.getTime() + 1);

        // Then
        assertThat(actual, fallsAfter(expected));
    }

    @Test
    public void Can_check_that_a_date_does_not_fall_after_another_date() {

        // Given
        final Date expected = someTime();
        final Date actual = new Date(expected.getTime() - 1);
        afterErrorTemplate.execute(writer, new HashMap<String, Object>() {{
            put("expected", expected);
            put("actual", actual);
        }});
        expectedException.expect(AssertionError.class);
        expectedException.expectMessage(writer.toString());

        // Then
        assertThat(actual, fallsAfter(expected));
    }

    @Test
    public void Can_check_that_a_date_falls_close_to_after_another_date() {

        // Given
        final Long date1 = somePositiveLong();
        final Long duration = someLongBetween(0L, 1000L);
        final TimeUnit unit = someEnum(TimeUnit.class);
        final Long durationInMilliseconds = unit.toMillis(duration);
        final Long difference = someLongBetween(0L, durationInMilliseconds);
        final Long date2 = date1 + difference;
        final Date expected = new Date(date1);
        final Date actual = new Date(date2);

        // Then
        assertThat(actual, fallsAfter(expected).within(duration, unit));
        assertThat(actual, fallsAfter(expected, within(duration, unit)));
    }

    @Test
    public void Can_check_that_a_date_falls_too_far_after_another_date() {

        // Given
        final Long date1 = somePositiveLong();
        final Long duration = someLongBetween(0L, 1000L);
        final TimeUnit unit = someEnum(TimeUnit.class);
        final long durationInMillis = unit.toMillis(duration);
        final Long difference = durationInMillis + 1;
        final Long date2 = date1 + difference;
        final Date expected = new Date(date1);
        final Date actual = new Date(date2);
        afterWithinErrorTemplate.execute(writer, new HashMap<String, Object>() {{
            put("duration", duration);
            put("unit", unit);
            put("expected", expected);
            put("actual", actual);
        }});
        expectedException.expect(AssertionError.class);
        expectedException.expectMessage(writer.toString());

        // Then
        assertThat(actual, fallsAfter(expected).within(duration, unit));
    }
}