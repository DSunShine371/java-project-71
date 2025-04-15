package hexlet.code.formatters;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.TreeSet;

public final class Plain {
    private static final String PREFIX = "Property '";

    public static String generateDiff(Map<String, Object> mapOfFile1, Map<String, Object> mapOfFile2) {
        Set<String> keys = getAllKeys(mapOfFile1, mapOfFile2);

        StringBuilder result = new StringBuilder();

        for (String key : keys) {
            if (mapOfFile1.containsKey(key) && mapOfFile2.containsKey(key)) {
                result.append(diffForKey(key, mapOfFile1, mapOfFile2));
            } else if (mapOfFile1.containsKey(key)) {
                result.append(removedKey(key));
            } else {
                result.append(addedKey(key, mapOfFile2));
            }
        }
        if (result.charAt(result.length() - 1) == '\n') {
            result.deleteCharAt(result.length() - 1);
        }

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
            return "";
        }
        return PREFIX + key + "' was updated. From " + normaliseValue(value1) + " to " + normaliseValue(value2) + "\n";
    }

    private static String removedKey(String key) {
        return PREFIX + key + "' was removed\n";
    }

    private static String addedKey(String key, Map<String, Object> map2) {
        return PREFIX + key + "' was added with value: " + normaliseValue(map2.get(key)) + "\n";
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
