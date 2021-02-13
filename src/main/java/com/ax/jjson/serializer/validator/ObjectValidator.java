package com.ax.jjson.serializer.validator;

import com.ax.jjson.serializer.Validator;

import java.lang.reflect.Field;

public class ObjectValidator implements Validator<Object> {

    public boolean validate(Object object) {
        if(object == null) return false;

        Field[] objectFields = object.getClass().getDeclaredFields();

        //the class must have at least one instance variable
        return objectFields.length > 0 ;
    }
}
