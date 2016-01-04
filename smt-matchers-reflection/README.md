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
smt-matchers-refection
===========
[![Build Status](https://travis-ci.org/shiver-me-timbers/smt-matchers-parent.svg?branch=master)](https://travis-ci.org/shiver-me-timbers/smt-matchers-parent) [![Coverage Status](https://coveralls.io/repos/shiver-me-timbers/smt-matchers-parent/badge.svg?branch=master&service=github)](https://coveralls.io/github/shiver-me-timbers/smt-matchers-parent?branch=master) [![Maven Central](https://maven-badges.herokuapp.com/maven-central/com.github.shiver-me-timbers/smt-matchers-refection/badge.svg)](https://maven-badges.herokuapp.com/maven-central/com.github.shiver-me-timbers/smt-matchers-refection/)

This library contains Hamcrest style matchers that can be used to apply matchers to the internals of a class using
reflection.

### Usage

##### Fields

All the field matcher methods can be found in the
[`FieldMatcher`](src/main/java/shiver/me/timbers/matchers/FieldMatcher.java) class.

Check that an object contains a matching field.
```java
assertThat(object, hasFieldThat("fieldName", equalTo(expected)));
```

Check that an object contains a field that equals a certain value.
```java
assertThat(object, hasField("fieldName", expected));
```

##### Properties

All the property matcher methods can be found in the
[`PropertyMatcher`](src/main/java/shiver/me/timbers/matchers/PropertyMatcher.java) class.

Check that an object contains a matching property.
```java
assertThat(object, hasPropertyThat("one.two.three", equalTo(expected)));
```

Check that an object contains a property that equals a certain value.
```java
assertThat(object, hasProperty("one.two.three", expected));
```