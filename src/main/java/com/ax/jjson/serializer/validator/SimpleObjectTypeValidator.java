package com.ax.jjson.serializer.validator;

import com.ax.jjson.serializer.Validator;
import com.ax.jjson.serializer.validator.exception.ValidationException;

import java.lang.reflect.Field;

public class SimpleObjectTypeValidator<T> implements Validator<T> {

    @Override
    public boolean validate(T object) throws ValidationException {
        if (Iterable.class.isAssignableFrom(object.getClass()))
            return false;

        Field[] fields = object.getClass().getDeclaredFields();

        for (Field field : fields) {
            if (Iterable.class.isAssignableFrom(field.getType())) {
                return false;
            }
        }

        return true;
    }
}
