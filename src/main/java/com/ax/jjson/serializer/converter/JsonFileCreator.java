package com.ax.jjson.serializer.converter;

import com.ax.jjson.serializer.Validator;
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
        if(!validator.validate(fileName))
            throw new ValidationException("The file extension is wrong. Please use the .json extension");

        try {
            return new BufferedWriter(new FileWriter(fileName));
        } catch (IOException exception) {
            return null;
        }
    }
}
