package com.ax.jjson.serializer;

import com.ax.jjson.serializer.validator.exception.ValidationException;

public interface Validator {
    /***
     *
     * @param object the object to be validated
     * @return true if the object is valid and false otherwise
     */
    boolean validate(Object object) throws ValidationException;
}
