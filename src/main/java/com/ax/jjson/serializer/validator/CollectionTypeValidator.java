package com.ax.jjson.serializer.validator;

import com.ax.jjson.serializer.Validator;
import com.ax.jjson.serializer.validator.exception.ValidationException;

import java.util.Collection;

public class CollectionTypeValidator<T> implements Validator<T> {
    @Override
    public boolean validate(T object) throws ValidationException {
        return Collection.class.isAssignableFrom(object.getClass());
    }
}
