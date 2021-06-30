package com.ax.jjson.serializer.converter;

import com.ax.jjson.serializer.JsonConverter;
import com.ax.jjson.serializer.Validator;
import com.ax.jjson.serializer.validator.Validators;
import com.ax.jjson.serializer.validator.exception.ValidationException;

import java.io.BufferedWriter;
import java.io.IOException;
import java.lang.reflect.Field;

import static com.ax.jjson.serializer.validator.Validators.SIMPLE_OBJECT;

public final class SimpleObjectJsonConverter<T> extends JsonConverter<T> {

    @Override
    public void convert(T object, BufferedWriter bufferedWriter) throws ValidationException, IOException, IllegalAccessException {
        //check if the object is a SimpleObject
        if(this.getClass().equals(((Validators)this.getValidator()).getCorrespondingClass()) && this.getValidator().validate(object)) {
            writeBeginningOfFile(bufferedWriter);

            Field[] fields = object.getClass().getDeclaredFields();
            for(int i = 0; i < fields.length -1; i++) {
                writeFieldWithComma(object, fields[i], bufferedWriter);
            }

            writeTheLastFieldWithoutCommaAtTheEnd(object, fields[fields.length - 1], bufferedWriter);
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    protected <V extends Enum<V> & Validator> V getValidator() {
        return (V) SIMPLE_OBJECT;
    }

    @Override
    public void writeBeginningOfFile(BufferedWriter bufferedWriter) throws IOException {
        bufferedWriter.write("{");
        bufferedWriter.newLine();
    }
}
