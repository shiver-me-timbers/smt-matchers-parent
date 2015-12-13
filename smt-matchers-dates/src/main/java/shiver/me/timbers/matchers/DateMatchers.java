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

import java.util.Date;

/**
 * Matchers that can be used to verify if a date is on or close to another specified date.
 *
 * @author Karl Bennett
 */
public class DateMatchers {

    /**
     * Check that they actual date is equal to the expected.
     */
    public static OnDateMatcher fallsOn(final Date expected) {
        return new OnDateMatcher(new TimeOperations(), expected);
    }

    public static AfterDateMatcher fallsAfter(Date expected) {
        return new AfterDateMatcher(expected);
    }

    public static BeforeDateMatcher fallsBefore(Date expected) {
        return new BeforeDateMatcher(expected);
    }
}
