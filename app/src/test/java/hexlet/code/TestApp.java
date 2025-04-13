package hexlet.code;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static hexlet.code.Differ.generate;
import static hexlet.code.Parser.readFile;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class TestApp {
    private static final Logger LOG = LoggerFactory.getLogger(TestApp.class);
    private static final Map<String, Object> EXTENDED_MAP = new LinkedHashMap<>();
    private static final Map<String, Object> EXTENDED_EMPTY_MAP = Map.of();
    private static String getPathFor(String fileName) {
        return Paths.get("src", "test", "resources", fileName).toString();
    }

    @BeforeAll
    static void generateMap() {
        EXTENDED_MAP.put("setting1", "Some value");
        EXTENDED_MAP.put("setting2", 200);
        EXTENDED_MAP.put("setting3", true);
        EXTENDED_MAP.put("key1", "value1");
        EXTENDED_MAP.put("numbers1", List.of(1, 2, 3, 4));
        EXTENDED_MAP.put("numbers2", List.of(2, 3, 4, 5));
        EXTENDED_MAP.put("id", 45);
        EXTENDED_MAP.put("default", null);
        EXTENDED_MAP.put("checked", false);
        EXTENDED_MAP.put("numbers3", List.of(3, 4, 5));
        EXTENDED_MAP.put("chars1", List.of("a", "b", "c"));
        EXTENDED_MAP.put("chars2", List.of("d", "e", "f"));
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

    @Test
    void testReadUnsupportedFile() {
        String filePath = getPathFor("unsupportedFile.txt");
        assertThrows(IllegalArgumentException.class, () -> readFile(filePath));
    }

    @Test
    void testReadUnsupportedFile2() {
        String filePath = getPathFor("unsupportedFile2");
        assertThrows(IllegalArgumentException.class, () -> readFile(filePath));
    }

    @Test
    void testDifferJson() {
        String filePath1 = getPathFor("file1.json");
        String filePath2 = getPathFor("file2.json");
        try {
            String actual = generate(filePath1, filePath2, "stylish");
            String extended = Files.readString(Path.of(getPathFor("extendedStylishResult")));
            assertEquals(extended, actual);
        } catch (Exception e) {
            LOG.error("Error Json file: {}", e.getMessage());
        }
    }

    @Test
    void testDifferYAML() {
        String filePath1 = getPathFor("file1.yml");
        String filePath2 = getPathFor("file2.yml");
        try {
            String actual = generate(filePath1, filePath2, "stylish");
            String extended = Files.readString(Path.of(getPathFor("extendedStylishResult")));
            assertEquals(extended, actual);
        } catch (Exception e) {
            LOG.error("Error YAML file: {}", e.getMessage());
        }
    }

    @Test
    void testPlainDifferJson() {
        String filePath1 = getPathFor("file1.json");
        String filePath2 = getPathFor("file2.json");
        try {
            String actual = generate(filePath1, filePath2, "plain");
            String extended = Files.readString(Path.of(getPathFor("extendedPlainResult")));
            assertEquals(extended, actual);
        } catch (Exception e) {
            LOG.error("Error Json to Plain file: {}", e.getMessage());
        }
    }

    @Test
    void testPlainDifferYAML() {
        String filePath1 = getPathFor("file1.yml");
        String filePath2 = getPathFor("file2.yml");
        try {
            String actual = generate(filePath1, filePath2, "plain");
            String extended = Files.readString(Path.of(getPathFor("extendedPlainResult")));
            assertEquals(extended, actual);
        } catch (Exception e) {
            LOG.error("Error YAML to Plain file: {}", e.getMessage());
        }
    }

    @Test
    void testJsonDifferJson() {
        String filePath1 = getPathFor("file1.json");
        String filePath2 = getPathFor("file2.json");
        try {
            String actual = generate(filePath1, filePath2, "json");
            String extended = Files.readString(Path.of(getPathFor("extendedJsonResult")));
            assertEquals(extended, actual);
        } catch (Exception e) {
            LOG.error("Error Json to Json file: {}", e.getMessage());
        }
    }

    @Test
    void testJsonDifferYAML() {
        String filePath1 = getPathFor("file1.yml");
        String filePath2 = getPathFor("file2.yml");
        try {
            String actual = generate(filePath1, filePath2, "json");
            String extended = Files.readString(Path.of(getPathFor("extendedJsonResult")));
            assertEquals(extended, actual);
        } catch (Exception e) {
            LOG.error("Error YAML to Json file: {}", e.getMessage());
        }
    }

    @Test
    void testDifferUnsupportedOutputFormat() {
        String filePath1 = getPathFor("file1.json");
        String filePath2 = getPathFor("file2.json");
        assertThrows(UnsupportedOperationException.class, () -> generate(filePath1, filePath2, "yaml"));
    }
}
