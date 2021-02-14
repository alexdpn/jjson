package com.ax.jjson.serializer.converter;

import com.ax.jjson.serializer.JsonConverter;
import com.ax.jjson.serializer.Validator;
import com.ax.jjson.serializer.validator.CollectionTypeValidator;
import com.ax.jjson.serializer.validator.exception.ValidationException;

import java.io.BufferedWriter;
import java.io.IOException;
import java.util.Collection;

public final class CollectionJsonConverter<T, C> extends JsonConverter<T> {

    @SuppressWarnings("unchecked")
    @Override
    public void convert(T object, BufferedWriter bufferedWriter) throws ValidationException, IOException, IllegalAccessException {
        Validator<T> validator = getValidator();

        //check if the object is Collection
        if(validator.validate(object)) {
            writeBeginningOfFile(bufferedWriter);
            JsonConverter<C> jsonConverter = additionalConverter();

            Collection collection =(Collection) object;
            C[] array = (C[])collection.toArray();

            for(int i = 0; i < array.length -1; i++) {
                jsonConverter.convert(array[i], bufferedWriter);
                bufferedWriter.write(",");
                bufferedWriter.newLine();
            }

            jsonConverter.convert(array[array.length - 1], bufferedWriter);
            bufferedWriter.newLine();
            bufferedWriter.write("]");
        }
    }

    @Override
    public Validator<T> getValidator() {
        return new CollectionTypeValidator<>();
    }

    @Override
    public void writeBeginningOfFile(BufferedWriter bufferedWriter) throws IOException {
        bufferedWriter.write("[");
        bufferedWriter.newLine();
    }

    private JsonConverter<C> additionalConverter() {
        return new SimpleObjectJsonConverter<C>();
    }
}
