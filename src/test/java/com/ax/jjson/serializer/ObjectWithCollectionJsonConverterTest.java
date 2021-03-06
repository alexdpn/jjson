package com.ax.jjson.serializer;

import com.ax.jjson.serializer.converter.ObjectWithCollectionJsonConverter;
import com.ax.jjson.serializer.file.JsonFileCreator;
import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.Test;

import java.io.BufferedWriter;
import java.io.File;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class ObjectWithCollectionJsonConverterTest extends Configuration {

    @Test
    public void testOnlineGame() throws Exception {
        OnlineGame onlineGame = createOnlineGameInstance ();

        JsonFileCreator jsonFileCreator = new JsonFileCreator();
        BufferedWriter bufferedWriter = jsonFileCreator.createJsonFileWriter("onlineGame.json");

        JsonConverter<OnlineGame> converter = new ObjectWithCollectionJsonConverter<>();
        converter.convert(onlineGame, bufferedWriter);
        converter.closeWriter(bufferedWriter);

        assertTrue(FileUtils.contentEquals(new File("src/test/java/resources/fileOnlineGame.json"), new File("onlineGame.json")));

        File file = new File("onlineGame.json");
        file.delete();
    }
}
