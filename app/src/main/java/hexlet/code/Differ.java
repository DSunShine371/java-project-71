package hexlet.code;

import hexlet.code.formatters.Json;
import hexlet.code.formatters.Plain;
import hexlet.code.formatters.Stylish;
import java.io.IOException;
import java.util.Map;

import static hexlet.code.Parser.readFile;
import static hexlet.code.constants.ReportFormats.JSON;
import static hexlet.code.constants.ReportFormats.PLAIN;
import static hexlet.code.constants.ReportFormats.STYLISH;

public final class Differ {
    public static String generate(String filePath1, String filePath2, String format) throws IOException {
        Map<String, Object> mapOfFile1 = readFile(filePath1);
        Map<String, Object> mapOfFile2 = readFile(filePath2);

        return switch (format) {
            case STYLISH -> Stylish.generateDiff(mapOfFile1, mapOfFile2);
            case PLAIN -> Plain.generateDiff(mapOfFile1, mapOfFile2);
            case JSON -> Json.generateDiff(mapOfFile1, mapOfFile2);
            default -> throw new UnsupportedOperationException("Unsupported format: " + format);
        };
    }
}
