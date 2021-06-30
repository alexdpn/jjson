package com.ax.jjson.serializer.validator;

import com.ax.jjson.serializer.Validator;
import com.ax.jjson.serializer.converter.CollectionJsonConverter;
import com.ax.jjson.serializer.converter.ObjectWithCollectionJsonConverter;
import com.ax.jjson.serializer.converter.SimpleObjectJsonConverter;
import com.ax.jjson.serializer.file.JsonFileCreator;
import com.ax.jjson.serializer.validator.exception.ValidationException;

import java.lang.reflect.Field;
import java.util.Collection;
import java.util.function.Supplier;

public enum Validators implements Validator {

    SIMPLE_OBJECT(() -> SimpleObjectJsonConverter.class) {
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

    OBJECT_WITH_COLLECTION (() -> ObjectWithCollectionJsonConverter.class){
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

    COLLECTION(() -> CollectionJsonConverter.class) {
        @Override
        public boolean validate(Object object) {
            return Collection.class.isAssignableFrom(object.getClass());
        }
    },

    FILE_NAME(()-> JsonFileCreator.class) {
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

    private final Supplier<Class<?>> correspondingClass;

    Validators(Supplier<Class<?>> correspondingClass) {
        this.correspondingClass = correspondingClass;
    }

    public Class<?> getCorrespondingClass() {
        return correspondingClass.get();
    }
}
