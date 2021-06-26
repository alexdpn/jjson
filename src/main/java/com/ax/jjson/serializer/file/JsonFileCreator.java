package com.ax.jjson.serializer.file;

import com.ax.jjson.serializer.validator.exception.ValidationException;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

import static com.ax.jjson.serializer.validator.Validators.FILE_NAME;

public class JsonFileCreator {

    public BufferedWriter createJsonFileWriter(String fileName) throws ValidationException {
        //validate the file extension
        FILE_NAME.validate(fileName);

        try {
            return new BufferedWriter(new FileWriter(fileName));
        } catch (IOException exception) {
            return null;
        }
    }
}
