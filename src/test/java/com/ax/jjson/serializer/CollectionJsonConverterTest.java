package com.ax.jjson.serializer;

import com.ax.jjson.serializer.converter.CollectionJsonConverter;
import com.ax.jjson.serializer.file.JsonFileCreator;
import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.Test;

import java.io.BufferedWriter;
import java.io.File;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class CollectionJsonConverterTest extends Configuration {

    @Test
    public void testPlayerCollection() throws Exception {
        Set<Player> Players = createSetOfPlayers();

        JsonFileCreator jsonFileCreator = new JsonFileCreator();
        BufferedWriter bufferedWriter = jsonFileCreator.createJsonFileWriter("players.json");

        CollectionJsonConverter<Set<Player>, Player> converter = new CollectionJsonConverter<>();
        converter.convert(Players, bufferedWriter);
        converter.closeWriter(bufferedWriter);

        assertTrue(FileUtils.contentEquals(new File("src/test/java/resources/fileWithPlayers.json"), new File("players.json")));

        File file = new File("players.json");
        file.delete();
    }
}
