package hipravin.samples.file;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.stream.Collectors;

public class ReadLInesJoinTest {


    public static void main(String[] args) {
        String line1 = "line1";
        String line2 = "line2";
        String line3 = "line3";
        String line4 = "line4";

        String strangelyLinedContent = line1 + "\r\n" + line2 + "\n" + line3 + "\r" + line4;

        Path testFile = Path.of("textReadLInesJoinTest.txt");

        writeTestFile(testFile, strangelyLinedContent);

        var readFullyContent = readFileFully(testFile);
        var joinLinesContent = readFileLines(testFile);

        System.out.println("readFullyContent eq original: " + readFullyContent.equals(strangelyLinedContent));
        System.out.println("joinLinesContent eq original: " + joinLinesContent.equals(strangelyLinedContent));

        System.out.println(readFullyContent);
        System.out.println(" ================= ");
        System.out.println(joinLinesContent);
        System.out.println(" ================= ");

    }

    static String readFileFully(Path file) {
        try {
            return Files.readString(file);
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    static String readFileLines(Path file) {
        try(var lines = Files.lines(file)) {
            return lines.collect(Collectors.joining("\n"));
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    static void writeTestFile(Path file, String content) {
        try {
            Files.writeString(file, content);
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }
}
