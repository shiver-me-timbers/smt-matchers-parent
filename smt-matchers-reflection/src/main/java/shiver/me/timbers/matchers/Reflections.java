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

import java.lang.reflect.Field;

/**
 * @author Karl Bennett
 */
public class Reflections {

    @SuppressWarnings("unchecked")
    public <T> T getFieldValue(String name, Object object) throws NoSuchFieldException, IllegalAccessException {
        final Field field = findDeclaredField(name, object.getClass());
        field.setAccessible(true);
        return (T) field.get(object);
    }

    @SuppressWarnings("unchecked")
    public <T> T getPropertyValue(String property, Object object)
        throws NoSuchPropertyException {
        return (T) findProperty(property, property.split("\\."), 0, object, object);
    }

    private Field findDeclaredField(String name, Class type) throws NoSuchFieldException {
        try {
            return type.getDeclaredField(name);
        } catch (NoSuchFieldException e) {
            final Class superType = type.getSuperclass();
            if (superType != null) {
                return findDeclaredField(name, superType);
            }
            throw e;
        }
    }

    private Object findProperty(String property, String[] fieldNames, int index, Object original, Object actual)
        throws NoSuchPropertyException {
        final Object value = getFieldValueForProperty(property, fieldNames, index, original, actual);
        if (index == fieldNames.length - 1) {
            return value;
        }
        return findProperty(property, fieldNames, ++index, original, value);
    }

    private Object getFieldValueForProperty(
        String property,
        String[] fieldNames,
        int index,
        Object original,
        Object actual
    )
        throws NoSuchPropertyException {
        try {
            return getFieldValue(fieldNames[index], actual);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new NoSuchPropertyException(property, index, original, actual, e);
        }
    }
}
