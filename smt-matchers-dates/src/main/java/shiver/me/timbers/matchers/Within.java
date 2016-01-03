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

import java.util.concurrent.TimeUnit;

/**
 * This class is a simple holder for the within duration values.
 *
 * @author Karl Bennett
 */
public class Within {

    public static Within within(Long duration, TimeUnit unit) {
        return new Within(duration, unit);
    }

    private final Long duration;
    private final TimeUnit unit;

    private Within(Long duration, TimeUnit unit) {
        this.duration = duration;
        this.unit = unit;
    }

    public Long getDuration() {
        return duration;
    }

    public TimeUnit getUnit() {
        return unit;
    }
}
