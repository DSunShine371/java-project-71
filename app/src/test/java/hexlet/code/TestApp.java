package hexlet.code;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.file.Paths;
import java.util.Map;

import static hexlet.code.Differ.getDiff;
import static hexlet.code.Parser.readFile;
import static org.junit.jupiter.api.Assertions.assertEquals;

class TestApp {
    private static final Logger LOG = LoggerFactory.getLogger(TestApp.class);
    private static final String EXTENDED_DIFF = """
            {
              - follow: false
                host: hexlet.io
              - proxy: 123.234.53.22
              - timeout: 50
              + timeout: 20
              + verbose: true
            }""";
    private static final Map<String, Object> EXTENDED_MAP = Map.of(
            "host", "hexlet.io",
            "timeout", 50,
            "proxy", "123.234.53.22",
            "follow", false
    );
    private static final Map<String, Object> EXTENDED_EMPTY_MAP = Map.of();
    private static String getPathFor(String fileName) {
        return Paths.get("src", "test", "resources", fileName).toString();
    }

    @Test
    void testDifferJson() {
        String filePath1 = getPathFor("file1.json");
        String filePath2 = getPathFor("file2.json");
        try {
            String actual = getDiff(filePath1, filePath2);
            assertEquals(EXTENDED_DIFF, actual);
        } catch (Exception e) {
            LOG.error("Error Json file: {}", e.getMessage());
        }
    }

    @Test
    void testDifferYAML() {
        String filePath1 = getPathFor("file1.yml");
        String filePath2 = getPathFor("file2.yml");
        try {
            String actual = getDiff(filePath1, filePath2);
            assertEquals(EXTENDED_DIFF, actual);
        } catch (Exception e) {
            LOG.error("Error YAML file: {}", e.getMessage());
        }
    }

    @Test
    void testReadJsonFile1() throws Exception {
        String filePath = getPathFor("file1.json");
        Map<String, Object> actualMap = readFile(filePath);
        assertEquals(EXTENDED_MAP, actualMap);
    }

    @Test
    void testReadEmptyJsonFile() throws Exception {
        String filePath = getPathFor("emptyFile.json");
        Map<String, Object> actualMap = readFile(filePath);
        assertEquals(EXTENDED_EMPTY_MAP, actualMap);
    }

    @Test
    void testReadYAMLFile1() throws Exception {
        String filePath = getPathFor("file1.yml");
        Map<String, Object> actualMap = readFile(filePath);
        assertEquals(EXTENDED_MAP, actualMap);
    }

    @Test
    void testReadEmptyYAMLFile() throws Exception {
        String filePath = getPathFor("emptyFile.yml");
        Map<String, Object> actualMap = readFile(filePath);
        assertEquals(EXTENDED_EMPTY_MAP, actualMap);
    }
}
