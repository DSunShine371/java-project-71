package hexlet.code.formatters;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.TreeSet;

public final class Json {
    private static final String OLD_VALUE = "oldValue";
    private static final String NEW_VALUE = "newValue";

    public static String generateDiff(Map<String, Object> mapOfFile1,
                                      Map<String, Object> mapOfFile2) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        Set<String> keys = getAllKeys(mapOfFile1, mapOfFile2);
        Map<String, Object> report = new LinkedHashMap<>();

        for (String key : keys) {
            if (mapOfFile1.containsKey(key) && mapOfFile2.containsKey(key)) {
                report.put(key, diffForKey(key, mapOfFile1, mapOfFile2));
            } else if (mapOfFile1.containsKey(key)) {
                report.put(key, removedKey(key, mapOfFile1));
            } else {
                report.put(key, addedKey(key, mapOfFile2));
            }
        }

        return objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(report);
    }

    private static Set<String> getAllKeys(Map<String, Object> map1, Map<String, Object> map2) {
        Set<String> keys = new TreeSet<>();
        keys.addAll(map1.keySet());
        keys.addAll(map2.keySet());
        return keys;
    }

    private static Object diffForKey(String key, Map<String, Object> map1, Map<String, Object> map2) {
        Object value1 = map1.get(key);
        Object value2 = map2.get(key);

        if (Objects.equals(value1, value2)) {
            return value2;
        } else {
            Map<String, Object> change = new LinkedHashMap<>();
            change.put(OLD_VALUE, value1);
            change.put(NEW_VALUE, value2);
            return change;
        }
    }

    private static Map<String, Object> removedKey(String key, Map<String, Object> map1) {
        Map<String, Object> change = new LinkedHashMap<>();
        change.put(OLD_VALUE, map1.get(key));
        return change;
    }

    private static Map<String, Object> addedKey(String key, Map<String, Object> map2) {
        Map<String, Object> change = new LinkedHashMap<>();
        change.put(NEW_VALUE, map2.get(key));
        return change;
    }
}
