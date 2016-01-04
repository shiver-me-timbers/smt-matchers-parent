<!---
Copyright 2015 Karl Bennett

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
-->
smt-matchers-dates
===========
[![Build Status](https://travis-ci.org/shiver-me-timbers/smt-matchers-parent.svg?branch=master)](https://travis-ci.org/shiver-me-timbers/smt-matchers-parent) [![Coverage Status](https://coveralls.io/repos/shiver-me-timbers/smt-matchers-parent/badge.svg?branch=master&service=github)](https://coveralls.io/github/shiver-me-timbers/smt-matchers-parent?branch=master) [![Maven Central](https://maven-badges.herokuapp.com/maven-central/com.github.shiver-me-timbers/smt-matchers-dates/badge.svg)](https://maven-badges.herokuapp.com/maven-central/com.github.shiver-me-timbers/smt-matchers-dates/)

This library contains Hamcrest style matchers that can be used to verify dates.

### Usage

##### Before Date

All the before date matcher methods can be found in the
[`BeforeDateMatcher`](src/main/java/shiver/me/timbers/matchers/BeforeDateMatcher.java) class.

Check that a date falls before but not on a specific date.
```java
assertThat(actual, fallsBefore(expected));
```

Check that a date falls before a specific date within a certain threshold.
```java
assertThat(actual, fallsBefore(expected).within(duration, unit));
assertThat(actual, fallsBefore(expected, within(duration, unit)));
```

##### On Date

All the before date matcher methods can be found in the
[`OnDateMatcher`](src/main/java/shiver/me/timbers/matchers/OnDateMatcher.java) class.

Check that a date falls on a specific date.
```java
assertThat(actual, fallsOn(expected));
```

Check that a date falls near a specific date.
```java
assertThat(actual, fallsOn(expected).within(duration, unit));
assertThat(actual, fallsOn(expected, within(duration, unit)));
```

##### After Date

All the before date matcher methods can be found in the
[`AfterDateMatcher`](src/main/java/shiver/me/timbers/matchers/AfterDateMatcher.java) class.

Check that a date falls after but not on a specific date.
```java
assertThat(actual, fallsAfter(expected));
```

Check that a date falls after a specific date within a certain threshold.
```java
assertThat(actual, fallsAfter(expected).within(duration, unit));
assertThat(actual, fallsAfter(expected, within(duration, unit)));
```

