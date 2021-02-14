package com.ax.jjson.serializer;

import com.ax.jjson.serializer.validator.exception.ValidationException;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class JsonFileCreator {

    private Validator validator;

    public JsonFileCreator(Validator validator) {
        this.validator = validator;
    }

    public BufferedWriter createJsonFileWriter(String fileName) throws ValidationException {
        //validate the file extension
        validator.validate(fileName);

        try {
            return new BufferedWriter(new FileWriter(fileName));
        } catch (IOException exception) {
            return null;
        }
    }
}
