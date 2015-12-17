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

import org.hamcrest.Description;
import org.hamcrest.TypeSafeMatcher;

/**
 * Matcher tha can have it's describing behaviour overridden programmatically.
 *
 * @author Karl Bennett
 */
public abstract class DescribingMatcher<T> extends TypeSafeMatcher<T> {

    private DescribeTo describeTo;
    private DescribeMismatch describeMismatch;

    @Override
    public final void describeTo(Description description) {
        describeTo.describeTo(description);
    }

    @Override
    protected final void describeMismatchSafely(T item, Description mismatchDescription) {
        describeMismatch.describeMismatch(mismatchDescription);
    }

    protected final void assignDescribers(DescribeTo describeTo, DescribeMismatch describeMismatch) {
        this.describeTo = describeTo;
        this.describeMismatch = describeMismatch;
    }

    protected interface DescribeTo {
        void describeTo(Description description);
    }

    protected interface DescribeMismatch {
        void describeMismatch(Description description);
    }
}
