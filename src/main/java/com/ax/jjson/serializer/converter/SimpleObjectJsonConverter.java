package com.ax.jjson.serializer.converter;

import com.ax.jjson.serializer.JsonConverter;
import com.ax.jjson.serializer.Validator;
import com.ax.jjson.serializer.validator.SimpleObjectTypeValidator;
import com.ax.jjson.serializer.validator.exception.ValidationException;

import java.io.BufferedWriter;
import java.io.IOException;
import java.lang.reflect.Field;

public class SimpleObjectJsonConverter<T> extends JsonConverter<T> {

    public void convert(T object, BufferedWriter bufferedWriter) throws ValidationException, IOException, IllegalAccessException {
        Validator<T> validator = getValidator();

        //check if the object is a SimpleObject
        if(validator.validate(object)) {
            writeBeginningOfFile(bufferedWriter);

            Field[] fields = object.getClass().getDeclaredFields();
            for(int i = 0; i < fields.length -1; i++) {
                writeFieldWithComma(object, fields[i], bufferedWriter);
            }

            writeTheLastFieldWithoutCommaAtTheEnd(object, fields[fields.length - 1], bufferedWriter);
            bufferedWriter.close();
        }
    }

    @Override
    public Validator<T> getValidator() {
        return new SimpleObjectTypeValidator<T>();
    }

    @Override
    public void writeBeginningOfFile(BufferedWriter bufferedWriter) throws IOException {
        bufferedWriter.write("{");
        bufferedWriter.newLine();
    }
}
