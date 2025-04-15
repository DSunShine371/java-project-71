package hexlet.code.formatters;

import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.TreeSet;

public final class Stylish {
    private static final String COMMON = "    ";
    private static final String PLUS = "  + ";
    private static final String MINUS = "  - ";
    private static final String SPLIT = ": ";

    public static String generateDiff(Map<String, Object> mapOfFile1, Map<String, Object> mapOfFile2) {
        Set<String> keys = getAllKeys(mapOfFile1, mapOfFile2);
        StringBuilder result = new StringBuilder("{\n");

        for (String key : keys) {
            if (mapOfFile1.containsKey(key) && mapOfFile2.containsKey(key)) {
                result.append(diffForKey(key, mapOfFile1, mapOfFile2));
            } else if (mapOfFile1.containsKey(key)) {
                result.append(removedKey(key, mapOfFile1));
            } else {
                result.append(addedKey(key, mapOfFile2));
            }
        }
        result.append("}");

        return result.toString();
    }

    private static Set<String> getAllKeys(Map<String, Object> map1, Map<String, Object> map2) {
        Set<String> keys = new TreeSet<>();
        keys.addAll(map1.keySet());
        keys.addAll(map2.keySet());
        return keys;
    }

    private static String diffForKey(String key, Map<String, Object> map1, Map<String, Object> map2) {
        Object value1 = map1.get(key);
        Object value2 = map2.get(key);

        if (Objects.equals(value1, value2)) {
            return COMMON + key + SPLIT + normaliseValue(value1) + "\n";
        } else {
            return MINUS + key + SPLIT + normaliseValue(value1) + "\n"
                    + PLUS + key + SPLIT + normaliseValue(value2) + "\n";
        }
    }

    private static String removedKey(String key, Map<String, Object> map1) {
        return MINUS + key + SPLIT + normaliseValue(map1.get(key)) + "\n";
    }

    private static String addedKey(String key, Map<String, Object> map2) {
        return PLUS + key + SPLIT + normaliseValue(map2.get(key)) + "\n";
    }

    private static String normaliseValue(Object value) {
        return switch (value) {
            case null -> "null";
            case Boolean bool -> bool.toString().toLowerCase();
            default -> value.toString();
        };
    }
}
