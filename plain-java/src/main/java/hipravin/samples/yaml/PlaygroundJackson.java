package hipravin.samples.yaml;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

public class PlaygroundJackson {
    public static void main(String[] args) {
        CustomConfigRecord customConfig = loadFromPath(Path.of("plain-java/src/main/resources/data/custom-config-1.yaml"));

        System.out.println(customConfig);
    }

    public static CustomConfigRecord loadFromPath(Path configPath) {
        final ObjectMapper objectMapper = new ObjectMapper(new YAMLFactory());

        try (BufferedReader reader = Files.newBufferedReader(configPath, StandardCharsets.UTF_8)) {
            return objectMapper.readValue(reader, CustomConfigRecord.class);
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }
}
