package hexlet.code;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.file.Paths;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static hexlet.code.Differ.getDiff;
import static hexlet.code.Parser.readFile;
import static org.junit.jupiter.api.Assertions.assertEquals;

class TestApp {
    private static final Logger LOG = LoggerFactory.getLogger(TestApp.class);
    private static final String EXTENDED_DIFF = """
        {
            chars1: [a, b, c]
          - chars2: [d, e, f]
          + chars2: false
          - checked: false
          + checked: true
          - default: null
          + default: [value1, value2]
          - id: 45
          + id: null
          - key1: value1
          + key2: value2
            numbers1: [1, 2, 3, 4]
          - numbers2: [2, 3, 4, 5]
          + numbers2: [22, 33, 44, 55]
          - numbers3: [3, 4, 5]
          + numbers4: [4, 5, 6]
          + obj1: {nestedKey=value, isNested=true}
          - setting1: Some value
          + setting1: Another value
          - setting2: 200
          + setting2: 300
          - setting3: true
          + setting3: none
        }""";
    private static final Map<String, Object> extendedMap = new LinkedHashMap<>();
    private static final Map<String, Object> EXTENDED_EMPTY_MAP = Map.of();
    private static String getPathFor(String fileName) {
        return Paths.get("src", "test", "resources", fileName).toString();
    }

    @BeforeAll
    static void generateMap() {
        extendedMap.put("setting1", "Some value");
        extendedMap.put("setting2", 200);
        extendedMap.put("setting3", true);
        extendedMap.put("key1", "value1");
        extendedMap.put("numbers1", List.of(1, 2, 3, 4));
        extendedMap.put("numbers2", List.of(2, 3, 4, 5));
        extendedMap.put("id", 45);
        extendedMap.put("default", null);
        extendedMap.put("checked", false);
        extendedMap.put("numbers3", List.of(3, 4, 5));
        extendedMap.put("chars1", List.of("a", "b", "c"));
        extendedMap.put("chars2", List.of("d", "e", "f"));
    }

    @Test
    void testDifferJson() {
        String filePath1 = getPathFor("file1.json");
        String filePath2 = getPathFor("file2.json");
        try {
            String actual = getDiff(filePath1, filePath2, "stylish");
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
            String actual = getDiff(filePath1, filePath2, "stylish");
            assertEquals(EXTENDED_DIFF, actual);
        } catch (Exception e) {
            LOG.error("Error YAML file: {}", e.getMessage());
        }
    }

    @Test
    void testReadJsonFile1() throws Exception {
        String filePath = getPathFor("file1.json");
        Map<String, Object> actualMap = readFile(filePath);
        assertEquals(extendedMap, actualMap);
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
        assertEquals(extendedMap, actualMap);
    }

    @Test
    void testReadEmptyYAMLFile() throws Exception {
        String filePath = getPathFor("emptyFile.yml");
        Map<String, Object> actualMap = readFile(filePath);
        assertEquals(EXTENDED_EMPTY_MAP, actualMap);
    }
}
