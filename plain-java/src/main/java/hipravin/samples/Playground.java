package hipravin.samples;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;

public class Playground {
    public static void main(String[] args) throws IOException, URISyntaxException {
        probe("C:\\dev\\projects\\samples\\plain-java\\target\\classes\\hipravin\\samples\\concurrency\\CompletableFutureAllofThousands.class");
        probe("C:\\dev\\projects\\samples\\plain-java\\src\\main\\java\\hipravin\\samples\\file\\FindFilesRecursively.java");
    }

    public static void probe(String path) throws IOException {
        System.out.println(Files.probeContentType(Path.of(path)) + "\t <- " + path);
    }
}
