package hexlet.code;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static hexlet.code.JsonReader.readFile;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestJsonReader {
    private Map<String, Object> expectedMap;
    private Map<String, Object> expectedEmptyMap;

    @BeforeEach
    public void generateMaps() {
        expectedMap = Map.of(
                "host", "hexlet.io",
                "timeout", 50,
                "proxy", "123.234.53.22",
                "follow", false
        );

        expectedEmptyMap = Map.of();
    }

    @Test
    public void testReadJsonFile1() throws Exception {
        Map<String, Object> actualMap = readFile("file1.json");
            assertEquals(expectedMap, actualMap);
    }

    @Test
    public void testReadEmptyJsonFile() throws Exception {
        Map<String, Object> actualMap = readFile("emptyFile.json");
            assertEquals(expectedEmptyMap, actualMap);
    }

}
