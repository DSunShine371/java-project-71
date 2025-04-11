package hexlet.code;

import hexlet.code.formatters.Json;
import hexlet.code.formatters.Plain;
import hexlet.code.formatters.Stylish;
import java.io.IOException;
import java.util.Map;

import static hexlet.code.Parser.readFile;

public final class Differ {
    private Differ() {
        throw new IllegalStateException("Utility class");
    }

    public static String generate(String filePath1, String filePath2, String format) throws IOException {
        Map<String, Object> mapOfFile1 = readFile(filePath1);
        Map<String, Object> mapOfFile2 = readFile(filePath2);

        return switch (format) {
            case "stylish" -> Stylish.generateDiff(mapOfFile1, mapOfFile2);
            case "plain" -> Plain.generateDiff(mapOfFile1, mapOfFile2);
            case "json" -> Json.generateDiff(mapOfFile1, mapOfFile2);
            default -> throw new UnsupportedOperationException("Unsupported format: " + format);
        };
    }
}
