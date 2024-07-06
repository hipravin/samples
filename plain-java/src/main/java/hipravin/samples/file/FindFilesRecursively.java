package hipravin.samples.file;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.PathMatcher;
import java.util.List;
import java.util.stream.Stream;

public class FindFilesRecursively {

    public static void main(String[] args) {
        List<Path> javaFiles = findFilesRecursivelyDepthFirst(Path.of(""), "java");

        javaFiles.forEach(System.out::println);
    }

    public static List<Path> findFilesRecursivelyDepthFirst(Path root, String extension) {
        PathMatcher matcher = FileSystems.getDefault().getPathMatcher("glob:**." + extension);
        try (Stream<Path> walkRoot = Files.walk(root)) {
            return walkRoot
                    .filter(Files::isRegularFile)
                    .filter(matcher::matches)
                    .toList();
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }
}
