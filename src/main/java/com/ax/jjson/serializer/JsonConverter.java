package com.ax.jjson.serializer;

import com.ax.jjson.serializer.validator.FileNameExtensionValidator;
import com.ax.jjson.serializer.validator.exception.ValidationException;

import java.io.BufferedWriter;
import java.io.IOException;
import java.lang.reflect.Field;


public abstract class JsonConverter<T> {

    /**
     * This method is used to convert the object to a json file
     * @param object the object to be converted to JSON
     */
    public abstract void convert(T object, BufferedWriter bufferedWriter) throws ValidationException, IOException, IllegalAccessException;

    /**
     * This method is used to create a validator
     * @return the validator used to validate the fields
     */
    protected abstract Validator<T> getValidator();

    /**
     * This method is used to write the beggining of the json file
     * @param bufferedWriter the writer used to write de file
     * @throws IOException
     */
    protected abstract void writeBeginningOfFile(BufferedWriter bufferedWriter) throws IOException;

    /**
     * This method is used to write the last field without a comma at the end.
     * @param object theobject used to be serialized
     * @param field the instance variable obtained using reflection
     * @param bufferedWriter the writer used to write to the json file
     * @throws IOException
     * @throws IllegalAccessException
     */
    protected final void writeTheLastFieldWithoutCommaAtTheEnd(T object, Field field, BufferedWriter bufferedWriter)
            throws IOException, IllegalAccessException {
        field.setAccessible(true);
        bufferedWriter.write("  " + "\"" + field.getName() +"\" : \"" + field.get(object) + "\"");
        bufferedWriter.newLine();
        bufferedWriter.write("}");
    }

    protected final void writeFieldWithComma(T object, Field field, BufferedWriter bufferedWriter)
            throws IOException, IllegalAccessException {
        field.setAccessible(true);
        bufferedWriter.write("  " + "\"" + field.getName() + "\" : \"" + field.get(object) + "\",");
        bufferedWriter.newLine();
    }

    /**
     * This method is used to create a JsonFileCreator object
     * @return the JsonFileCreator used to create the json file
     */
    protected final JsonFileCreator getJsonFileCreator() {
        return new JsonFileCreator(new FileNameExtensionValidator());
    }

    public final void closeWriter(BufferedWriter bufferedWriter) throws IOException {
        bufferedWriter.close();
    }
}
