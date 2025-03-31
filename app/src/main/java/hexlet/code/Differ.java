package hexlet.code;

import java.io.IOException;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.TreeSet;

import static hexlet.code.JsonReader.readFile;

public class Differ {
    private Differ() {
        throw new IllegalStateException("Utility class");
    }

    public static String getDiff(String filePath1, String filePath2) throws IOException {
        Map<String, Object> mapOfFile1 = readFile(filePath1);
        Map<String, Object> mapOfFile2 = readFile(filePath2);

        return generateDiff(mapOfFile1, mapOfFile2);
    }

    private static String generateDiff(Map<String, Object> mapOfFile1, Map<String, Object> mapOfFile2) {
        Set<String> keys = new TreeSet<>();
        keys.addAll(mapOfFile1.keySet());
        keys.addAll(mapOfFile2.keySet());

        StringBuilder result = new StringBuilder("{\n");

        for (String key : keys) {
            if (mapOfFile1.containsKey(key) && mapOfFile2.containsKey(key)) {
                Object value1 = mapOfFile1.get(key);
                Object value2 = mapOfFile2.get(key);

                if (Objects.equals(value1, value2)) {
                    result.append(String.format("    %s: %s%n", key, normaliseValue(value1)));
                } else {
                    result.append(String.format("  - %s: %s%n", key, normaliseValue(value1)));
                    result.append(String.format("  + %s: %s%n", key, normaliseValue(value2)));
                }
            } else if (mapOfFile1.containsKey(key)) {
                result.append(String.format("  - %s: %s%n", key, normaliseValue(mapOfFile1.get(key))));
            } else {
                result.append(String.format("  + %s: %s%n", key, normaliseValue(mapOfFile2.get(key))));
            }
        }
        result.append("}");

        return result.toString();
    }

    private static String normaliseValue(Object value) {
        if (value == null) {
            return "null";
        } else if (value instanceof Boolean) {
            return value.toString().toLowerCase();
        } else {
            return value.toString();
        }
    }
}
