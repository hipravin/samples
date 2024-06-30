package hipravin.samples.concurrency;

import java.time.Duration;
import java.util.Random;
import java.util.concurrent.*;

public class FixedVsWorkStealing {
    static final CountDownLatch startLatch = new CountDownLatch(1);

    public static void main(String[] args) throws InterruptedException {
        ExecutorService executor = Executors.newFixedThreadPool(8); //PT6.3930692S
//        ExecutorService executor = Executors.newWorkStealingPool(8); //PT6.3835344S

        int taskCount = 1000;
        final int MAX_DELAY_MILLIS = 100;
        Random random = new Random(0);
        for (int i = 0; i < taskCount; i++) {
            final int value = i;
//            executor.submit(() -> delayedReturn(value, random.nextInt(MAX_DELAY_MILLIS)));
            CompletableFuture.runAsync(() -> delayedReturn(value, random.nextInt(MAX_DELAY_MILLIS)), executor);
        }

        executor.shutdown();
        long start = System.nanoTime();
        startLatch.countDown();
        boolean completed = executor.awaitTermination(100, TimeUnit.SECONDS);
        if(!completed) {
            throw new RuntimeException("Time limit exceeded");
        }
        var elapsed = Duration.ofNanos(System.nanoTime() - start);
        System.out.println("Execution took: " + elapsed);
    }

    public static <T> T delayedReturn(T value, long millis) {
        try {
            startLatch.await();
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        return logThread(value);
    }


    public static <T> T logThread(T value) {
        var t = Thread.currentThread();
//        System.out.printf("%s\tThread: %s%n", value, t.getName());

        return value;
    }
}
