package hexlet.code;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collections;
import java.util.Map;

public class JsonReader {
    public static Map<String, Object> readFile(String filePath) throws Exception {
        String content = Files.readString(Path.of(filePath));

        if (content == null || content.trim().isEmpty()) {
            return Collections.emptyMap();
        }

        ObjectMapper parserJson = new ObjectMapper();
        return parserJson.readValue(content, new TypeReference<>() {});
    }
}
