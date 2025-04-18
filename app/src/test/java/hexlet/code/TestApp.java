package hexlet.code;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;
import picocli.CommandLine;

import java.io.IOException;
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
    private static final String SOME_VALUE = "Some value";
    private static final int VALUE_200 = 200;
    private static final boolean TRUE_VALUE = true;
    private static final boolean FALSE_VALUE = false;
    private static final int VALUE_45 = 45;
    private static final List<Integer> NUMBERS1 = List.of(1, 2, 3, 4);
    private static final List<Integer> NUMBERS2 = List.of(2, 3, 4, 5);
    private static final List<Integer> NUMBERS3 = List.of(3, 4, 5);
    private static final List<String> CHARS1 = List.of("a", "b", "c");
    private static final List<String> CHARS2 = List.of("d", "e", "f");

    private static final Map<String, Object> EXTENDED_MAP = new LinkedHashMap<>();
    private static final Map<String, Object> EXTENDED_EMPTY_MAP = Map.of();
    private static String getPathFor(String fileName) {
        return Paths.get("src", "test", "resources", fileName).toString();
    }

    @BeforeAll
    static void generateMap() {
        EXTENDED_MAP.put("setting1", SOME_VALUE);
        EXTENDED_MAP.put("setting2", VALUE_200);
        EXTENDED_MAP.put("setting3", TRUE_VALUE);
        EXTENDED_MAP.put("key1", "value1");
        EXTENDED_MAP.put("numbers1", NUMBERS1);
        EXTENDED_MAP.put("numbers2", NUMBERS2);
        EXTENDED_MAP.put("id", VALUE_45);
        EXTENDED_MAP.put("default", null);
        EXTENDED_MAP.put("checked", FALSE_VALUE);
        EXTENDED_MAP.put("numbers3", NUMBERS3);
        EXTENDED_MAP.put("chars1", CHARS1);
        EXTENDED_MAP.put("chars2", CHARS2);
    }

    @ParameterizedTest(name = "{index} - {0}")
    @ValueSource(strings = {"file1.json", "file1.yml"})
    void testReadFile(String fileName) throws IOException {
        String filePath = getPathFor(fileName);
        Map<String, Object> actualMap = readFile(filePath);
        assertEquals(EXTENDED_MAP, actualMap);
    }

    @ParameterizedTest(name = "{index} - {0}")
    @ValueSource(strings = {"emptyFile.json", "emptyFile.yml"})
    void testReadEmptyFile(String fileName) throws IOException {
        String filePath = getPathFor(fileName);
        Map<String, Object> actualMap = readFile(filePath);
        assertEquals(EXTENDED_EMPTY_MAP, actualMap);
    }

    @ParameterizedTest(name = "{index} - {0}")
    @ValueSource(strings = {"unsupportedFile.txt", "unsupportedFile2"})
    void testReadUnsupportedFile(String fileName) {
        String filePath = getPathFor(fileName);
        assertThrows(IllegalArgumentException.class, () -> readFile(filePath));
    }

    @ParameterizedTest(name = "{index} - {0}")
    @CsvSource({
            "file1.json, file2.json, stylish, extendedStylishResult",
            "file1.yml, file2.yml, stylish, extendedStylishResult",
            "file1.json, file2.json, plain, extendedPlainResult",
            "file1.yml, file2.yml, plain, extendedPlainResult",
            "file1.json, file2.json, json, extendedJsonResult",
            "file1.yml, file2.yml, json, extendedJsonResult"})
    void testDiffer(String file1, String file2, String format, String resultFile) throws IOException {
        String filePath1 = getPathFor(file1);
        String filePath2 = getPathFor(file2);
        String actual = generate(filePath1, filePath2, format);
        String extended = Files.readString(Path.of(getPathFor(resultFile)));
        assertEquals(extended, actual);
    }

    @Test
    void testDifferUnsupportedOutputFormat() {
        String filePath1 = getPathFor("file1.json");
        String filePath2 = getPathFor("file2.json");
        assertThrows(UnsupportedOperationException.class, () -> generate(filePath1, filePath2, "yaml"));
    }

    @Test
    void testAppExecutionSuccess() {
        String[] args = {"--format=plain", getPathFor("file1.json"), getPathFor("file2.json")};
        App app = new App();
        CommandLine cmd = new CommandLine(app);
        int exitCode = cmd.execute(args);
        assertEquals(0, exitCode);
    }

    @Test
    void testAppException() {
        String[] args = {"--format=plain", getPathFor("file1.json"), getPathFor("unsupportedFile.txt")};
        App app = new App();
        CommandLine cmd = new CommandLine(app);
        int exitCode = cmd.execute(args);
        assertEquals(1, exitCode);
    }
}
