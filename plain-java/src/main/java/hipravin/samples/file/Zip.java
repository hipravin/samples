package hipravin.samples.file;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.zip.GZIPOutputStream;
import java.util.zip.ZipOutputStream;

public class Zip {
    public static void main(String[] args) throws IOException {
        String sampleContent = """
                Sample zip
                file
                content
                .
                """;

        zip(sampleContent, Path.of("sampleZipped.gz"));
    }

    public static void zip(String content, Path targetFile)
            throws IOException {

        try (BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(
                new GZIPOutputStream(Files.newOutputStream(targetFile))))) {
            writer.write(content);
        }
    }
}
