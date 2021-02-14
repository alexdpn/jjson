package com.ax.jjson.serializer;

import com.ax.jjson.serializer.converter.JsonFileCreator;
import com.ax.jjson.serializer.converter.ToJsonConverter;
import com.ax.jjson.serializer.validator.FileNameExtensionValidator;
import com.ax.jjson.serializer.validator.ObjectValidator;
import com.ax.jjson.serializer.validator.exception.ValidationException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class JsonConverterTest extends Configuration {

    private JsonFileCreator jsonFileCreator;
    private ToJsonConverter<Customer> toJsonConverter;

    @BeforeEach
    public void init() {
        jsonFileCreator = new JsonFileCreator(new FileNameExtensionValidator());
        toJsonConverter = new ToJsonConverter<Customer>(new ObjectValidator());
    }

    @Test
    public void testSimpleObjectSerializer() throws Exception {
        Customer customer = createCustomerInstance();
        BufferedWriter bufferedWriter = jsonFileCreator.createJsonFileWriter("customer.json");

        toJsonConverter.convert(customer, bufferedWriter);

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

    @Test
    public void testSimpleObjectSerializerThrowsValidationExceptionForObject() {
        assertThrows(
                ValidationException.class,
                () -> toJsonConverter.convert(null, null),
                "The object is null or it has zero instance variables");

        assertThrows(
                ValidationException.class,
                () -> new ToJsonConverter<ClassWithNoFields>(new ObjectValidator()).convert(new ClassWithNoFields(), null),
                "The object is null or it has zero instance variables");
    }
}
