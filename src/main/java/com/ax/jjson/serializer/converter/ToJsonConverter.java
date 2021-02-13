package com.ax.jjson.serializer.converter;

import com.ax.jjson.serializer.Validator;
import com.ax.jjson.serializer.validator.exception.ValidationException;

import java.io.BufferedWriter;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.List;

public class ToJsonConverter<T> {

    private Validator validator;

    public ToJsonConverter(Validator validator) {
        this.validator = validator;
    }

    public void convert(T object, BufferedWriter jsonFileWriter) throws ValidationException,
            IOException, IllegalAccessException{
        //validate the object to be serialized
       if(!validator.validate(object))
                throw new ValidationException("The object is null or it has zero instance variables");

        //write for "simple" object
        if(!List.class.isInstance(object)) {
            jsonFileWriter.write("{");
            jsonFileWriter.newLine();

            Field[] fields = object.getClass().getDeclaredFields();

            for(int i = 0; i <fields.length -1; i++) {
                fields[i].setAccessible(true);
                jsonFileWriter.write("  " + "\"" + fields[i].getName() +"\" : \"" + fields[i].get(object) + "\",");
                jsonFileWriter.newLine();
            }

            //write the last field without comma at the end
            fields[fields.length - 1].setAccessible(true);
            jsonFileWriter.write("  " + "\"" + fields[fields.length -1].getName() +"\" : \"" + fields[fields.length - 1].get(object) + "\"");
            jsonFileWriter.newLine();

            jsonFileWriter.write("}");
            jsonFileWriter.close();
        }

    }
}
