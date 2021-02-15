package com.ax.jjson.serializer.converter;

import com.ax.jjson.serializer.JsonConverter;
import com.ax.jjson.serializer.Validator;
import com.ax.jjson.serializer.validator.ObjectWithCollectionTypeValidator;
import com.ax.jjson.serializer.validator.exception.ValidationException;

import java.io.BufferedWriter;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.Collection;

public final class ObjectWithCollectionJsonConverter<T> extends JsonConverter<T> {

    @Override
    public void convert(T object, BufferedWriter bufferedWriter) throws ValidationException, IOException, IllegalAccessException {
        Validator<T> validator = getValidator();

        //check if the object is ObjectWithCollection
        if(validator.validate(object)) {
            writeBeginningOfFile(bufferedWriter);

            JsonConverter<Object> jsonConverter = additionalConverter();

            Field[] fields = object.getClass().getDeclaredFields();
            for(int i = 0; i < fields.length -1; i++) {
                if(fields[i].getType().isAssignableFrom(Collection.class)){
                    bufferedWriter.write("{");
                    bufferedWriter.newLine();

                    bufferedWriter.write("\"" + fields[i].getClass().getName() + "\" : ");
                    bufferedWriter.newLine();

                    jsonConverter.convert(fields[i].get(object), bufferedWriter);
                } else {
                    writeFieldWithComma(object, fields[i], bufferedWriter);
                }
            }

            if(!fields[fields.length - 1].getType().isAssignableFrom(Collection.class)) {
                writeTheLastFieldWithoutCommaAtTheEnd(object, fields[fields.length - 1], bufferedWriter);
            }
        }
    }

    @Override
    public Validator<T> getValidator() {
        return new ObjectWithCollectionTypeValidator<>();
    }

    @Override
    public void writeBeginningOfFile(BufferedWriter bufferedWriter) throws IOException {
        bufferedWriter.write("{");
        bufferedWriter.newLine();
    }

    private JsonConverter<Object> additionalConverter() {
        return new CollectionJsonConverter<>();
    }
}
