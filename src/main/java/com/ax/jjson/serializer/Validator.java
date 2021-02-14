package com.ax.jjson.serializer;

import com.ax.jjson.serializer.validator.exception.ValidationException;

public interface Validator<T> {
    /***
     *
     * @param t the object to be validated
     * @return true if the object is valid and false otherwise
     */
    boolean validate(T t) throws ValidationException;
}
