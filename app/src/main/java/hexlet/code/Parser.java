package hexlet.code;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLMapper;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collections;
import java.util.Map;

public final class Parser {
    private Parser() {
        throw new IllegalStateException("Utility class");
    }

    public static Map<String, Object> readFile(String filePath) throws IOException {
        String fileExtension = getFileExtension(filePath);
        String content = Files.readString(Path.of(filePath));

        if (content == null || content.trim().isEmpty()) {
            return Collections.emptyMap();
        }
        ObjectMapper parser = switch (fileExtension) {
            case "json" -> new ObjectMapper();
            case "yml", "yaml" -> new YAMLMapper();
            default -> throw new IllegalArgumentException("Unsupported file extension: " + fileExtension);
        };
        return parser.readValue(content, new TypeReference<>() { });
    }

    private static String getFileExtension(String filePath) {
        String fileName = Path.of(filePath).getFileName().toString();
        int dotIndex = fileName.lastIndexOf('.');
        if (dotIndex > 0 && dotIndex < fileName.length() - 1) {
            return fileName.substring(dotIndex + 1).toLowerCase();
        }
        return ""; // No extension
    }
}
