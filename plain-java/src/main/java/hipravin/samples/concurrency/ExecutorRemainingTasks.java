package hipravin.samples.concurrency;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ExecutorRemainingTasks {
    public static void main(String[] args) {
        ExecutorService executor = Executors.newFixedThreadPool(1);

        Runnable correctTask = () -> {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                System.out.println("Interrupted!");
                Thread.currentThread().interrupt();
            }
            System.out.println("Completed, " + Thread.currentThread().getName());
        };

        Runnable exceptionalTask = () -> {
            System.out.println("Going to fail...");
            throw new RuntimeException("Async task failed");
        };

        List<CompletableFuture<?>> futures = new CopyOnWriteArrayList<>();

        var cf1 = CompletableFuture.runAsync(correctTask, executor);
        var cf2 = CompletableFuture.runAsync(correctTask, executor);
        var cf3 = CompletableFuture.runAsync(exceptionalTask, executor).exceptionally(e -> {
            futures.forEach(f -> f.cancel(true));
            return null;
        });
        var cf4 = CompletableFuture.runAsync(exceptionalTask, executor);
        var cf5 = CompletableFuture.runAsync(correctTask, executor);
        var cf6 = CompletableFuture.runAsync(correctTask, executor);
        var cf7 = CompletableFuture.runAsync(correctTask, executor);
        var cf8 = CompletableFuture.runAsync(correctTask, executor);
        futures.addAll(Arrays.asList(cf1, cf2, cf3, cf4, cf5, cf6, cf7, cf8));

        executor.shutdown();

        CompletableFuture.allOf(cf1, cf2, cf3, cf4, cf5, cf6, cf7, cf8).join();

    }


}
