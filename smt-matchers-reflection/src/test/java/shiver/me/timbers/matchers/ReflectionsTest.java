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

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.isA;
import static org.junit.Assert.assertThat;
import static shiver.me.timbers.data.random.RandomStrings.someString;

public class ReflectionsTest {

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    private Reflections reflections;

    @Before
    public void setUp() {
        reflections = new Reflections();
    }

    @Test
    public void Can_get_the_value_of_a_field() throws NoSuchFieldException, IllegalAccessException {

        // Given
        final String expected = someString();
        class AClass {
            private final String fieldName = expected;
        }

        // When
        final String actual = reflections.getFieldValue("fieldName", new AClass());

        // Then
        assertThat(actual, is(expected));
    }

    @Test
    public void Can_get_the_value_of_a_field_from_a_super_class() throws NoSuchFieldException, IllegalAccessException {

        // Given
        final String expected = someString();
        class AClass {
            private final String fieldName = expected;
        }
        class BClass extends AClass {
        }
        class CClass extends BClass {
        }

        // When
        final String actual = reflections.getFieldValue("fieldName", new CClass());

        // Then
        assertThat(actual, is(expected));
    }

    @Test(expected = NoSuchFieldException.class)
    public void Can_fail_to_get_the_value_of_a_field() throws NoSuchFieldException, IllegalAccessException {

        // Given
        class AClass {
        }

        // When
        reflections.getFieldValue("fieldName", new AClass());
    }

    @Test(expected = NoSuchFieldException.class)
    public void Can_fail_to_get_the_value_of_a_field_from_a_super_class()
        throws NoSuchFieldException, IllegalAccessException {

        // Given
        class AClass {
        }
        class BClass extends AClass {
        }
        class CClass extends BClass {
        }

        // When
        reflections.getFieldValue("fieldName", new CClass());
    }

    @Test
    public void Can_get_the_value_of_a_property() throws NoSuchPropertyException, IllegalAccessException {

        // Given
        final String expected = someString();
        class AClass {
            private final String three = expected;
        }
        class BClass {
            private final AClass two = new AClass();
        }
        class CClass {
            private final BClass one = new BClass();
        }

        // When
        final String actual = reflections.getPropertyValue("one.two.three", new CClass());

        // Then
        assertThat(actual, is(expected));
    }

    @Test
    public void Can_get_the_value_of_a_property_from_a_super_class()
        throws NoSuchPropertyException, IllegalAccessException {

        // Given
        final String expected = someString();
        class AClass {
            private final String three = expected;
        }
        class BClass {
            private final AClass two = new AClass();
        }
        class CClass extends BClass {
        }
        class DClass extends CClass {
        }
        class EClass {
            private final DClass one = new DClass();
        }
        class FClass extends EClass {
        }

        // When
        final String actual = reflections.getPropertyValue("one.two.three", new FClass());

        // Then
        assertThat(actual, is(expected));
    }

    @Test
    public void Can_fail_to_get_the_value_of_a_property() throws NoSuchPropertyException, IllegalAccessException {

        // Given
        class AClass {
        }
        class BClass {
            private final AClass one = new AClass();
        }
        expectedException.expect(NoSuchPropertyException.class);
        expectedException.expectCause(isA(NoSuchFieldException.class));
        expectedException.expectMessage(
            String.format(
                "For class %s, property (one.[two].three) is invalid in composite class %s.",
                BClass.class.getName(),
                AClass.class.getName()
            )
        );

        // When
        reflections.getPropertyValue("one.two.three", new BClass());
    }

    @Test
    public void Can_fail_to_get_the_value_of_a_property_from_a_super_class()
        throws NoSuchPropertyException, IllegalAccessException {

        // Given
        class AClass {
        }
        class BClass {
            private final AClass two = new AClass();
        }
        class CClass extends BClass {
        }
        class DClass extends CClass {
        }
        class EClass {
            private final DClass one = new DClass();
        }
        class FClass extends EClass {
        }
        expectedException.expect(NoSuchPropertyException.class);
        expectedException.expectCause(isA(NoSuchFieldException.class));
        expectedException.expectMessage(
            String.format(
                "For class %s, property (one.two.[three]) is invalid in composite class %s.",
                FClass.class.getName(),
                AClass.class.getName()
            )
        );

        // When
        reflections.getPropertyValue("one.two.three", new FClass());
    }
}