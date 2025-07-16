package hipravin.samples.file;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.DigestInputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.Duration;
import java.util.HexFormat;

public class FileDigest {

    public static void main(String[] args) throws IOException {
        Path sampleLargePdf = Path.of("C:/Users/Alex/YandexDisk/books/developer/Docker.Deep.Dive.2024.pdf");

        for (int i = 0; i < 10; i++) {
            long start = System.nanoTime();
            String hash = sha256(InputStreamSupplier.forPath(sampleLargePdf));
            System.out.printf("Elapsed: %s, hash: %s%n", Duration.ofNanos(System.nanoTime() - start), hash);
        }
    }

    static String sha256(InputStreamSupplier supplier) throws IOException {
        try {
            try (DigestInputStream inputStream = new DigestInputStream(supplier.openStream(),
                    MessageDigest.getInstance("SHA-256"))) {
                inputStream.readAllBytes();
                return HexFormat.of().formatHex(inputStream.getMessageDigest().digest());
            }
        }
        catch (NoSuchAlgorithmException ex) {
            throw new IllegalStateException(ex);
        }
    }

    @FunctionalInterface
    interface InputStreamSupplier {

        InputStream openStream() throws IOException;

        static InputStreamSupplier forPath(Path path) {
            return () -> Files.newInputStream(path);
        }
    }
}
