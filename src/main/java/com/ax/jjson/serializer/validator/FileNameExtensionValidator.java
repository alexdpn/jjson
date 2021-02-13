package com.ax.jjson.serializer.validator;

import com.ax.jjson.serializer.Validator;

public class FileNameExtensionValidator implements Validator<String> {
    private final static String JSON_EXTENSION = ".json";

    public boolean validate(String fileName) {
        String extension = fileName.substring(fileName.length() - 5);

        return extension.equals(JSON_EXTENSION);
    }
}
