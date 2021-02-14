package com.ax.jjson.serializer;

import com.ax.jjson.serializer.converter.SimpleObjectJsonConverter;
import com.ax.jjson.serializer.file.JsonFileCreator;
import com.ax.jjson.serializer.validator.FileNameExtensionValidator;
import com.ax.jjson.serializer.validator.exception.ValidationException;
import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class SimpleObjectJsonConverterTest extends Configuration {

    @Test
    public void testSimpleObjectSerializer() throws Exception {
        Customer customer = createCustomerInstance();
        JsonFileCreator jsonFileCreator = new JsonFileCreator(new FileNameExtensionValidator());
        BufferedWriter bufferedWriter = jsonFileCreator.createJsonFileWriter("customer.json");

        SimpleObjectJsonConverter<Customer> simpleObjectJsonConverter = new SimpleObjectJsonConverter<>();
        simpleObjectJsonConverter.convert(customer, bufferedWriter);
        simpleObjectJsonConverter.closeWriter(bufferedWriter);

        BufferedReader bufferedReader = new BufferedReader(new FileReader("customer.json"));

        String firstLine = bufferedReader.readLine();
        String secondLine = bufferedReader.readLine();
        String thirdLine = bufferedReader.readLine();
        String fourthLine = bufferedReader.readLine();
        String fifthLine = bufferedReader.readLine();

        assertEquals("{", firstLine);
        assertEquals("  \"id\" : \"1\",", secondLine);
        assertEquals("  \"name\" : \"Alex\",", thirdLine);
        assertEquals("  \"email\" : \"alex@mymail.com\"", fourthLine);
        assertEquals("}", fifthLine);

        //close the reader
        bufferedReader.close();

        //delete the file
        File file = new File("customer.json");
        file.delete();
    }

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

