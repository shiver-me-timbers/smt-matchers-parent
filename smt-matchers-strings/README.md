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
[![Build Status](https://travis-ci.org/shiver-me-timbers/smt-matchers-parent.svg?branch=master)](https://travis-ci.org/shiver-me-timbers/smt-matchers-parent) [![Coverage Status](https://coveralls.io/repos/shiver-me-timbers/smt-matchers-parent/badge.svg?branch=master&service=github)](https://coveralls.io/github/shiver-me-timbers/smt-matchers-parent?branch=master) [![Maven Central](https://maven-badges.herokuapp.com/maven-central/com.github.shiver-me-timbers/smt-matchers-strings/badge.svg)](https://maven-badges.herokuapp.com/maven-central/com.github.shiver-me-timbers/smt-matchers-strings/)

This library contains Hamcrest style matchers that can be used to verify strings.

### Usage

##### Regex Matching

Check that a string matches a specific regex pattern.
```java
assertThat(actual, matches(regex));
```
