package com.ax.jjson.serializer.validator;

import com.ax.jjson.serializer.Validator;
import com.ax.jjson.serializer.validator.exception.ValidationException;

import java.lang.reflect.Field;
import java.util.Collection;

public enum Validators implements Validator {

    SIMPLE_OBJECT {
        @Override
        public boolean validate(Object object) {
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
    },

    OBJECT_WITH_COLLECTION {
        @Override
        public boolean validate(Object object) {
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
    },

    COLLECTION {
        @Override
        public boolean validate(Object object) {
            return Collection.class.isAssignableFrom(object.getClass());
        }
    },

    FILE_NAME {
        @Override
        public boolean validate(Object object) throws ValidationException {
            String JSON_EXTENSION = ".json";
            String extension = null;

            if(object instanceof String) {
                extension = ((String) object).substring(((String)object).length() - 5);
            }

            if(!JSON_EXTENSION.equals(extension))
                throw new ValidationException("The file extension is wrong. Please use the .json extension");

            return true;
        }
    };
}
