package hexlet.code;

import org.junit.jupiter.api.Test;

import static hexlet.code.Differ.getDiff;
import static org.junit.jupiter.api.Assertions.assertEquals;

class TestDiffer {
    private static final String EXTENDED = """
            {
              - follow: false
                host: hexlet.io
              - proxy: 123.234.53.22
              - timeout: 50
              + timeout: 20
              + verbose: true
            }""";

    @Test
    void testDifferJson() {
        try {
            String expected = EXTENDED.replaceAll("\\s+", "");
            String actual = getDiff("file1.json", "file2.json").replaceAll("\\s+", "");
            assertEquals(expected, actual);
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
        }
    }
}
