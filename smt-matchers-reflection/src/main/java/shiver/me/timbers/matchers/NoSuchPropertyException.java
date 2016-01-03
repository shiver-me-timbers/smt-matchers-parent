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

import static java.lang.String.format;

/**
 * @author Karl Bennett
 */
public class NoSuchPropertyException extends ReflectiveOperationException {

    private final String property;
    private final String markedProperty;
    private final String missingField;
    private final Object originalObject;
    private final Object errorObject;

    public NoSuchPropertyException(String property, int index, Object original, Object error, Throwable e) {
        this(property, property.split("\\."), index, original, error, e);
    }

    public NoSuchPropertyException(String property, String[] fieldNames, int index, Object original, Object error, Throwable e) {
        super(buildMessage(fieldNames, index, original, error), e);
        this.property = property;
        this.markedProperty = buildMarkedProperty(fieldNames, index);
        this.missingField = fieldNames[index];
        this.originalObject = original;
        this.errorObject = error;
    }

    public String getProperty() {
        return property;
    }

    public String getMarkedProperty() {
        return markedProperty;
    }

    public String getMissingField() {
        return missingField;
    }

    public Object getOriginalObject() {
        return originalObject;
    }

    public Object getErrorObject() {
        return errorObject;
    }

    private static String buildMessage(String[] fieldNames, int index, Object original, Object error) {
        return format(
            "For class %s, property (%s) is invalid in composite class %s.",
            original.getClass().getName(),
            buildMarkedProperty(fieldNames, index),
            error.getClass().getName()
        );
    }

    private static String buildMarkedProperty(String[] fieldNames, int index) {
        final StringBuilder builder = new StringBuilder(fieldNames[0]);
        for (int i = 1; i < fieldNames.length; i++) {
            if (i != index) builder.append('.').append(fieldNames[i]);
            else builder.append(".[").append(fieldNames[i]).append(']');
        }
        return builder.toString();
    }
}
