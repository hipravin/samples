package hipravin.samples.file;

import java.nio.file.Path;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class Playground {
    public static void main(String[] args) throws ExecutionException, InterruptedException, TimeoutException {

        var watchTask = DirectoryWatchUtil.watchForUpdatesRunnable(Path.of("plain-java/src/main/resources/data"),
                System.out::println);

        CompletableFuture.runAsync(watchTask).get(10, TimeUnit.MINUTES);
    }
}
