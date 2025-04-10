package hexlet.code.formatters;

import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.TreeSet;

public final class Stylish {
    private Stylish() {
        throw new IllegalStateException("Utility class");
    }

    public static String generateDiff(Map<String, Object> mapOfFile1, Map<String, Object> mapOfFile2) {
        Set<String> keys = new TreeSet<>();
        keys.addAll(mapOfFile1.keySet());
        keys.addAll(mapOfFile2.keySet());

        StringBuilder result = new StringBuilder("{\n");

        for (String key : keys) {
            if (mapOfFile1.containsKey(key) && mapOfFile2.containsKey(key)) {
                Object value1 = mapOfFile1.get(key);
                Object value2 = mapOfFile2.get(key);

                if (Objects.equals(value1, value2)) {
                    result.append("    ").append(key).append(": ").append(normaliseValue(value1)).append("\n");
                } else {
                    result.append("  - ").append(key).append(": ").append(normaliseValue(value1)).append("\n");
                    result.append("  + ").append(key).append(": ").append(normaliseValue(value2)).append("\n");
                }
            } else if (mapOfFile1.containsKey(key)) {
                result.append("  - ").append(key).append(": ").append(normaliseValue(mapOfFile1.get(key))).append("\n");
            } else {
                result.append("  + ").append(key).append(": ").append(normaliseValue(mapOfFile2.get(key))).append("\n");
            }
        }
        result.append("}");

        return result.toString();
    }

    private static String normaliseValue(Object value) {
        return switch (value) {
            case null -> "null";
            case Boolean bool -> bool.toString().toLowerCase();
            default -> value.toString();
        };
    }
}
