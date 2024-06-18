package hipravin.samples;

import java.io.IOException;
import java.io.InputStream;
import java.io.UncheckedIOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

public class Playground {
    public static void main(String[] args) throws IOException, URISyntaxException {


        String fileName = "data/sample.txt";
        System.out.println(loadFromClasspath1(fileName));
        System.out.println(loadFromClasspath2(fileName));

        System.out.println(loadFromClasspath3(fileName));
        System.out.println(loadFromClasspath4(fileName));
    }

    private static String loadFromClasspath1(String fileName) {
        try (InputStream is = ClassLoader.getSystemResourceAsStream(fileName)) {
            if (is != null) {
                return new String(is.readAllBytes(), StandardCharsets.UTF_8);
            } else {
                throw new RuntimeException("Not found in classpath: " + fileName);
            }
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    private static String loadFromClasspath2(String fileName) {
        URL url = ClassLoader.getSystemResource(fileName);
        if (url != null) {
            try (InputStream is = url.openStream()) {
                return new String(is.readAllBytes(), StandardCharsets.UTF_8);
            } catch (IOException e) {
                throw new UncheckedIOException(e);
            }
        } else {
            throw new RuntimeException("Not found in classpath: " + fileName);
        }
    }

    private static String loadFromClasspath3(String fileName) throws IOException, URISyntaxException {
        URL url = ClassLoader.getSystemResource(fileName);
        Path path = Path.of(url.toURI());
        return Files.readString(path);
    }

    private static String loadFromClasspath4(String fileName) throws IOException {

        URI uri = URI.create("classpath:" + fileName);
        try(InputStream is = uri.toURL().openStream()) {
            return new String(is.readAllBytes(), StandardCharsets.UTF_8);
        }

    }
}
