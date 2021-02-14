package com.ax.jjson.serializer.validator;

import com.ax.jjson.serializer.Validator;
import com.ax.jjson.serializer.validator.exception.ValidationException;

import java.lang.reflect.Field;
import java.util.Collection;

public class SimpleObjectTypeValidator<T> implements Validator<T> {

    @Override
    public boolean validate(T object) throws ValidationException {
        if (Collection.class.isAssignableFrom(object.getClass()))
            return false;

        Field[] fields = object.getClass().getDeclaredFields();

        for (Field field : fields) {
            if (Collection.class.isAssignableFrom(field.getType())) {
                return false;
            }
        }

        return true;
    }
}
