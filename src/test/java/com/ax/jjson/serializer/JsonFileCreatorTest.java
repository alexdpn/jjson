package com.ax.jjson.serializer;

import com.ax.jjson.serializer.file.JsonFileCreator;
import com.ax.jjson.serializer.validator.FileNameExtensionValidator;
import com.ax.jjson.serializer.validator.exception.ValidationException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class JsonFileCreatorTest {

    @Test
    public void testSimpleObjectSerializerThrowsValidationExceptionForFileName() {
        JsonFileCreator jsonFileCreator = new JsonFileCreator(new FileNameExtensionValidator());

        assertThrows(
                ValidationException.class,
                () -> jsonFileCreator.createJsonFileWriter("fileNameWithoutExtension"),
                "The file extension is wrong. Please use the .json extension");

        assertThrows(
                ValidationException.class,
                () -> jsonFileCreator.createJsonFileWriter("myfile.xml"),
                "The file extension is wrong. Please use the .json extension");

        assertThrows(
                ValidationException.class,
                () -> jsonFileCreator.createJsonFileWriter("file.java"),
                "The file extension is wrong. Please use the .json extension");
    }
}
