package hipravin.samples.concurrency;

import java.util.Arrays;
import java.util.concurrent.CompletableFuture;
import java.util.stream.IntStream;

public class CompletableFutureAllofThousands {
    public static void main(String[] args) {
        int taskCount = 10_000;

        CompletableFuture<?>[] futures = IntStream.range(0, taskCount)
                .mapToObj(i -> CompletableFuture.supplyAsync(() -> logThread(i)))
                .toArray(CompletableFuture[]::new);

        CompletableFuture.allOf(futures).join();

        Arrays.stream(futures).mapToInt(cf -> (Integer) cf.join())
                .average().ifPresent(avg -> System.out.println(avg));
    }

    public static <T> T logThread(T value) {
        var t = Thread.currentThread();
        System.out.printf("%s\tThread: %s%n", value, t.getName());

        return value;
    }
}
