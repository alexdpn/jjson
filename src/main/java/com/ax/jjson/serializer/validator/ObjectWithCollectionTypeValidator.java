package com.ax.jjson.serializer.validator;

import com.ax.jjson.serializer.Validator;
import com.ax.jjson.serializer.validator.exception.ValidationException;

import java.lang.reflect.Field;
import java.util.Collection;

public class ObjectWithCollectionTypeValidator<T> implements Validator<T> {

    @Override
    public boolean validate(T object) throws ValidationException {
        boolean isValid = false;

        //the object must not be a collection, but it has to have a field as a collection
        if (Collection.class.isAssignableFrom(object.getClass()))
            return false;

        Field[] fields = object.getClass().getDeclaredFields();

        for (Field field : fields) {
            if (Collection.class.isAssignableFrom(field.getType())) {
                isValid = true;
            }
        }

        return isValid;
    }
}

