package com.ax.jjson.serializer.validator;

import com.ax.jjson.serializer.Validator;
import com.ax.jjson.serializer.validator.exception.ValidationException;

public class FileNameExtensionValidator implements Validator<String> {
    private final static String JSON_EXTENSION = ".json";

    public boolean validate(String fileName) throws ValidationException {
        String extension = fileName.substring(fileName.length() - 5);

        if(!extension.equals(JSON_EXTENSION))
            throw new ValidationException("The file extension is wrong. Please use the .json extension");

        return true;
    }
}
