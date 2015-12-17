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
}
