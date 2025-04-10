package hexlet.code;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.file.Paths;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static hexlet.code.Differ.generate;
import static hexlet.code.Parser.readFile;
import static org.junit.jupiter.api.Assertions.assertEquals;

class TestApp {
    private static final Logger LOG = LoggerFactory.getLogger(TestApp.class);
    private static final String EXTENDED_STYLISH_DIFF = """
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
    private static final String EXTENDED_PLAIN_DIFF = """

            Property 'chars2' was updated. From [complex value] to false
            Property 'checked' was updated. From false to true
            Property 'default' was updated. From null to [complex value]
            Property 'id' was updated. From 45 to null
            Property 'key1' was removed
            Property 'key2' was added with value: 'value2'
            Property 'numbers2' was updated. From [complex value] to [complex value]
            Property 'numbers3' was removed
            Property 'numbers4' was added with value: [complex value]
            Property 'obj1' was added with value: [complex value]
            Property 'setting1' was updated. From 'Some value' to 'Another value'
            Property 'setting2' was updated. From 200 to 300
            Property 'setting3' was updated. From true to 'none'
            """;
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
    void testDifferJson() {
        String filePath1 = getPathFor("file1.json");
        String filePath2 = getPathFor("file2.json");
        try {
            String actual = generate(filePath1, filePath2, "stylish");
            assertEquals(EXTENDED_STYLISH_DIFF, actual);
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
            assertEquals(EXTENDED_STYLISH_DIFF, actual);
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
            assertEquals(EXTENDED_PLAIN_DIFF, actual);
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
            assertEquals(EXTENDED_PLAIN_DIFF, actual);
        } catch (Exception e) {
            LOG.error("Error YAML to Plain file: {}", e.getMessage());
        }
    }
}
