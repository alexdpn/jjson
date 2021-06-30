package com.ax.jjson.serializer.converter;

import com.ax.jjson.serializer.JsonConverter;
import com.ax.jjson.serializer.Validator;
import com.ax.jjson.serializer.validator.Validators;
import com.ax.jjson.serializer.validator.exception.ValidationException;

import java.io.BufferedWriter;
import java.io.IOException;
import java.util.Collection;

import static com.ax.jjson.serializer.validator.Validators.COLLECTION;

public final class CollectionJsonConverter<T, C> extends JsonConverter<T> {

    @SuppressWarnings("unchecked")
    @Override
    public void convert(T object, BufferedWriter bufferedWriter) throws ValidationException, IOException, IllegalAccessException {
        //check if the object is Collection
        if(this.getClass().equals(((Validators)this.getValidator()).getCorrespondingClass()) && this.getValidator().validate(object)) {
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

    @SuppressWarnings("unchecked")
    @Override
    protected <V extends Enum<V> & Validator> V getValidator() {
        return (V) COLLECTION;
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
