package hexlet.code;

import org.junit.jupiter.api.Test;

import static hexlet.code.Differ.getDiff;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestDiffer {
    private static final String expected = """
            {
              - follow: false
                host: hexlet.io
              - proxy: 123.234.53.22
              - timeout: 50
              + timeout: 20
              + verbose: true
            }""";

    @Test
    public void testDifferJson() {
        try {
            assertEquals(expected, getDiff("file1.json", "file2.json"));
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
        }
    }
}
