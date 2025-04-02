package hexlet.code;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collections;
import java.util.Map;

public final class JsonReader {
    private JsonReader() {
        throw new IllegalStateException("Utility class");
    }

    public static Map<String, Object> readFile(String filePath) throws IOException {
        String content = Files.readString(Path.of(filePath));

        if (content == null || content.trim().isEmpty()) {
            return Collections.emptyMap();
        }

        ObjectMapper parserJson = new ObjectMapper();
        return parserJson.readValue(content, new TypeReference<>() { });
    }
}
