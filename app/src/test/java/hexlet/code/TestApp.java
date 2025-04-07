package hexlet.code;

import org.junit.jupiter.api.Test;

import java.nio.file.Paths;
import java.util.Map;

import static hexlet.code.Differ.getDiff;
import static hexlet.code.JsonReader.readFile;
import static org.junit.jupiter.api.Assertions.assertEquals;

class TestApp {
    private final String extendedDiff = """
            {
              - follow: false
                host: hexlet.io
              - proxy: 123.234.53.22
              - timeout: 50
              + timeout: 20
              + verbose: true
            }""";
    private final Map<String, Object> expectedMap = Map.of(
            "host", "hexlet.io",
            "timeout", 50,
            "proxy", "123.234.53.22",
            "follow", false
    );
    private final Map<String, Object> expectedEmptyMap = Map.of();

    @Test
    void testDifferJson() throws Exception {
        String filePath1 = Paths.get(getClass().getClassLoader().getResource("file1.json").toURI()).toString();
        String filePath2 = Paths.get(getClass().getClassLoader().getResource("file2.json").toURI()).toString();
        try {
            String actual = getDiff(filePath1, filePath2);
            assertEquals(extendedDiff, actual);
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
        }
    }

    @Test
    void testReadJsonFile1() throws Exception {
        String filePath = Paths.get(getClass().getClassLoader().getResource("file1.json").toURI()).toString();
        Map<String, Object> actualMap = readFile(filePath);
        assertEquals(expectedMap, actualMap);
    }

    @Test
    void testReadEmptyJsonFile() throws Exception {
        String filePath = Paths.get(getClass().getClassLoader().getResource("emptyFile.json").toURI()).toString();
        Map<String, Object> actualMap = readFile(filePath);
        assertEquals(expectedEmptyMap, actualMap);
    }
}
