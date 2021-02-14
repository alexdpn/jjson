package com.ax.jjson.serializer;

import com.ax.jjson.serializer.converter.CollectionJsonConverter;
import com.ax.jjson.serializer.file.JsonFileCreator;
import com.ax.jjson.serializer.validator.FileNameExtensionValidator;
import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.Test;

import java.io.BufferedWriter;
import java.io.File;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class CollectionJsonConverterTest extends Configuration {

    @Test
    public void testCustomerCollection() throws Exception {
        Set<Customer> customers = createLisOfCustomers();

        JsonFileCreator jsonFileCreator = new JsonFileCreator(new FileNameExtensionValidator());
        BufferedWriter bufferedWriter = jsonFileCreator.createJsonFileWriter("customers.json");

        CollectionJsonConverter<Set<Customer>, Customer> converter = new CollectionJsonConverter<>();
        converter.convert(customers, bufferedWriter);
        converter.closeWriter(bufferedWriter);

        assertTrue(FileUtils.contentEquals(new File("fileWithCustomers.json"), new File("customers.json")));
    }
}
