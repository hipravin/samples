package hipravin.samples.file;

import org.junit.jupiter.api.Test;

import java.nio.file.Path;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

class DirectoryWatchUtilTest {
    @Test
    void sampleSingleFile() throws ExecutionException, InterruptedException, TimeoutException {
        var watchTask = DirectoryWatchUtil.fileWatch(Path.of("src/main/resources/data/application-custom-config.yml"),
                () -> System.out.println("file updated"));

        CompletableFuture.runAsync(watchTask).get(10, TimeUnit.MINUTES);
    }
}