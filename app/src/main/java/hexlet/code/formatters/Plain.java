package hexlet.code.formatters;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.TreeSet;

public final class Plain {
    private Plain() {
        throw new IllegalStateException("Utility class");
    }

    public static String generateDiff(Map<String, Object> mapOfFile1, Map<String, Object> mapOfFile2) {
        Set<String> keys = new TreeSet<>();
        keys.addAll(mapOfFile1.keySet());
        keys.addAll(mapOfFile2.keySet());
        String prefix = "Property '";

        StringBuilder result = new StringBuilder();

        for (String key : keys) {
            if (mapOfFile1.containsKey(key) && mapOfFile2.containsKey(key)) {
                Object value1 = mapOfFile1.get(key);
                Object value2 = mapOfFile2.get(key);

                if (!Objects.equals(value1, value2)) {
                    result.append(prefix)
                            .append(key)
                            .append("' was updated. From ")
                            .append(normaliseValue(value1))
                            .append(" to ")
                            .append(normaliseValue(value2))
                            .append("\n");
                }
            } else if (mapOfFile1.containsKey(key)) {
                result.append(prefix)
                        .append(key)
                        .append("' was removed")
                        .append("\n");
            } else {
                result.append(prefix)
                        .append(key)
                        .append("' was added with value: ")
                        .append(normaliseValue(mapOfFile2.get(key)))
                        .append("\n");
            }
        }
        if (result.charAt(result.length() - 1) == '\n') {
            result.deleteCharAt(result.length() - 1);
        }

        return result.toString();
    }

    private static String normaliseValue(Object value) {
        return switch (value) {
            case List list -> "[complex value]";
            case Map map -> "[complex value]";
            case String string -> "'" + string + "'";
            case null -> "null";
            case Boolean bool -> bool.toString().toLowerCase();
            default -> value.toString();
        };
    }
}
