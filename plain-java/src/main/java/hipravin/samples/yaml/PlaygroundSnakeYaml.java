package hipravin.samples.yaml;

import org.yaml.snakeyaml.LoaderOptions;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.Constructor;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.UncheckedIOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * Jackson also works with yaml but internally uses snakeyaml.
 * Thus, it seems reasonable to use jackson. Additionally jackson provides mapping to java record type which snakeyaml doesn't.
 *
 * However, if java record is not needed and jackson is never used in the project then it's better to operate with snakeyaml directly.
 */
public class PlaygroundSnakeYaml {
//    https://bitbucket.org/snakeyaml/snakeyaml/wiki/Documentation
    public static void main(String[] args) {
        CustomConfig customConfig = loadFromPath(Path.of("plain-java/src/main/resources/data/custom-config-1.yaml"));

        System.out.println(customConfig);

        CustomConfig customConfig2 = loadFromClasspath("data/custom-config-1.yaml");
        System.out.println(customConfig2);
    }

    public static CustomConfig loadFromPath(Path configPath) {
        Yaml yaml = new Yaml(new Constructor(CustomConfig.class, new LoaderOptions()));
        try (BufferedReader reader = Files.newBufferedReader(configPath, StandardCharsets.UTF_8)) {
            return yaml.load(reader);
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    public static CustomConfig loadFromClasspath(String file) {
        Yaml yaml = new Yaml(new Constructor(CustomConfig.class, new LoaderOptions()));
        try (InputStream is = ClassLoader.getSystemResourceAsStream(file)) {
            if (is != null) {
                return yaml.load(is);
            } else {
                throw new RuntimeException("Not found in classpath: " + file);
            }
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }
}
