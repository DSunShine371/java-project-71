package hexlet.code;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLMapper;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collections;
import java.util.Map;

import static hexlet.code.constants.ExtensionFormats.JSON;
import static hexlet.code.constants.ExtensionFormats.YAML;
import static hexlet.code.constants.ExtensionFormats.YML;

public final class Parser {
    public static Map<String, Object> readFile(String filePath) throws IOException {
        String fileExtension = getFileExtension(filePath);
        String content = Files.readString(Path.of(filePath));

        if (content == null || content.trim().isEmpty()) {
            return Collections.emptyMap();
        }
        return switch (fileExtension) {
            case JSON -> parseJson(content);
            case YML, YAML -> parseYaml(content);
            default -> throw new IllegalArgumentException("Unsupported file extension: " + fileExtension);
        };
    }

    private static Map<String, Object> parseJson(String content) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(content, new TypeReference<>() { });
    }

    private static Map<String, Object> parseYaml(String content) throws IOException {
        YAMLMapper yamlMapper = new YAMLMapper();
        return yamlMapper.readValue(content, new TypeReference<>() { });
    }

    private static String getFileExtension(String filePath) {
        String fileName = Path.of(filePath).getFileName().toString();
        int dotIndex = fileName.lastIndexOf('.');
        if (dotIndex > 0 && dotIndex < fileName.length() - 1) {
            return fileName.substring(dotIndex + 1).toLowerCase();
        }
        return "";
    }
}
