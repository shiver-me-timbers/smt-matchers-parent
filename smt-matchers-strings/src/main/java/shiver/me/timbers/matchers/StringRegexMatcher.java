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

import org.hamcrest.Description;
import org.hamcrest.Factory;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;

/**
 * A matcher to check that a {@link String} matches the supplied regex pattern.
 *
 * @author Karl Bennett
 */
public class StringRegexMatcher extends TypeSafeMatcher<String> {

    private final RegexMatcher regexMatcher;
    private final String pattern;
    private String string;

    /**
     * Check the that the regex pattern matches the supplied value.
     */
    @Factory
    public static Matcher<String> matches(String pattern) {
        return new StringRegexMatcher(pattern);
    }

    public StringRegexMatcher(String pattern) {
        this(new RegexMatcher(), pattern);
    }

    StringRegexMatcher(RegexMatcher regexMatcher, String pattern) {
        this.regexMatcher = regexMatcher;
        this.pattern = pattern;
    }

    @Override
    protected boolean matchesSafely(String string) {
        this.string = string;
        return regexMatcher.matches(pattern, string);
    }

    @Override
    public void describeTo(Description description) {
        description.appendText("the string to match the pattern ").appendValue(pattern).appendText(".");
    }
}
