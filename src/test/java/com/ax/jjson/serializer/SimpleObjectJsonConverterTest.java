package com.ax.jjson.serializer;

import com.ax.jjson.serializer.converter.SimpleObjectJsonConverter;
import com.ax.jjson.serializer.file.JsonFileCreator;
import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SimpleObjectJsonConverterTest extends Configuration {

    @Test
    public void testSimpleObjectSerializer() throws Exception {
        Player Player = createPlayerInstance();
        JsonFileCreator jsonFileCreator = new JsonFileCreator();
        BufferedWriter bufferedWriter = jsonFileCreator.createJsonFileWriter("player.json");

        JsonConverter<Player> simpleObjectJsonConverter = new SimpleObjectJsonConverter<>();
        simpleObjectJsonConverter.convert(Player, bufferedWriter);
        simpleObjectJsonConverter.closeWriter(bufferedWriter);

        BufferedReader bufferedReader = new BufferedReader(new FileReader("player.json"));

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
        File file = new File("player.json");
        file.delete();
    }
}

