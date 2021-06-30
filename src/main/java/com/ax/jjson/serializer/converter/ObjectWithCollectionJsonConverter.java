package com.ax.jjson.serializer.converter;

import com.ax.jjson.serializer.JsonConverter;
import com.ax.jjson.serializer.Validator;
import com.ax.jjson.serializer.validator.Validators;
import com.ax.jjson.serializer.validator.exception.ValidationException;

import java.io.BufferedWriter;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.Collection;

import static com.ax.jjson.serializer.validator.Validators.OBJECT_WITH_COLLECTION;

public final class ObjectWithCollectionJsonConverter<T> extends JsonConverter<T> {

    @Override
    public void convert(T object, BufferedWriter bufferedWriter) throws ValidationException, IOException, IllegalAccessException {
        //check if the object is ObjectWithCollection
        if(this.getClass().equals(((Validators)this.getValidator()).getCorrespondingClass()) && this.getValidator().validate(object)) {
            writeBeginningOfFile(bufferedWriter);

            JsonConverter<Object> jsonConverter = additionalConverter();

            Field[] fields = object.getClass().getDeclaredFields();
            for(int i = 0; i < fields.length -1; i++) {
                if(Collection.class.isAssignableFrom(fields[i].getType())) {
                    fields[i].setAccessible(true);
                    bufferedWriter.newLine();

                    bufferedWriter.write("\"" + fields[i].getName() + "\" : ");
                    bufferedWriter.newLine();

                    jsonConverter.convert(fields[i].get(object), bufferedWriter);
                    bufferedWriter.write(",");
                } else {
                    writeFieldWithComma(object, fields[i], bufferedWriter);
                }
            }

            if(!Collection.class.isAssignableFrom(fields[fields.length - 1].getType())) {
                writeTheLastFieldWithoutCommaAtTheEnd(object, fields[fields.length - 1], bufferedWriter);
            } else {
                fields[fields.length - 1].setAccessible(true);
                bufferedWriter.newLine();

                bufferedWriter.write("\"" + fields[fields.length - 1].getName() + "\" : ");
                bufferedWriter.newLine();

                jsonConverter.convert(fields[fields.length - 1].get(object), bufferedWriter);
                bufferedWriter.newLine();
                bufferedWriter.write("}");
            }
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    protected <V extends Enum<V> & Validator> V getValidator() {
        return (V) OBJECT_WITH_COLLECTION;
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
