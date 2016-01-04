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

import static java.util.concurrent.TimeUnit.HOURS;
import static java.util.concurrent.TimeUnit.MILLISECONDS;
import static java.util.concurrent.TimeUnit.MINUTES;
import static java.util.concurrent.TimeUnit.SECONDS;
import static org.junit.Assert.assertThat;
import static shiver.me.timbers.data.random.RandomLongs.someLongBetween;
import static shiver.me.timbers.data.random.RandomThings.someThing;
import static shiver.me.timbers.data.random.RandomTimes.someTime;
import static shiver.me.timbers.matchers.AfterDateMatcher.fallsAfter;
import static shiver.me.timbers.matchers.Within.within;

public class AfterDateMatcherIntegrationTest {

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    private Mustache afterWithinErrorTemplate;
    private Mustache afterErrorTemplate;
    private StringWriter writer;

    @Before
    public void setUp() {
        final DefaultMustacheFactory mustacheFactory = new DefaultMustacheFactory(new ClasspathResolver());
        afterWithinErrorTemplate = mustacheFactory.compile("invalid-after-within-date-error-message.mustache");
        afterErrorTemplate = mustacheFactory.compile("invalid-after-date-error-message.mustache");
        writer = new StringWriter();
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
        final Date actual = new Date(expected.getTime() - someLongBetween(0L, 1000L));
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
    public void Can_check_that_a_date_falls_too_far_after_another_date() {

        // Given
        final Long date1 = someLongBetween(0L, 1000L);
        final Long duration = someLongBetween(0L, 1000L);
        final TimeUnit unit = someThing(MILLISECONDS, SECONDS, MINUTES, HOURS);
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