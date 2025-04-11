package hexlet.code.formatters;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.TreeSet;

public final class Json {
    private Json() {
        throw new IllegalStateException("Utility class");
    }

    public static String generateDiff(Map<String, Object> mapOfFile1,
                                      Map<String, Object> mapOfFile2) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        Set<String> keys = new TreeSet<>();
        keys.addAll(mapOfFile1.keySet());
        keys.addAll(mapOfFile2.keySet());

        Map<String, Object> report = new LinkedHashMap<>();
        String oldValue = "oldValue";
        String newValue = "newValue";

        for (String key : keys) {
            if (mapOfFile1.containsKey(key) && mapOfFile2.containsKey(key)) {
                Object value1 = mapOfFile1.get(key);
                Object value2 = mapOfFile2.get(key);

                if (Objects.equals(value1, value2)) {
                    report.put(key, value2);
                } else {
                    Map<String, Object> change = new LinkedHashMap<>();
                    change.put(oldValue, value1);
                    change.put(newValue, value2);
                    report.put(key, change);
                }
            } else if (mapOfFile1.containsKey(key)) {
                Map<String, Object> change = new LinkedHashMap<>();
                change.put(oldValue, mapOfFile1.get(key));
                report.put(key, change);
            } else {
                Map<String, Object> change = new LinkedHashMap<>();
                change.put(newValue, mapOfFile2.get(key));
                report.put(key, change);
            }
        }

        return objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(report);
    }
}
