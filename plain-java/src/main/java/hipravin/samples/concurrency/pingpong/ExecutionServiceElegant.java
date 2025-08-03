package hipravin.samples.concurrency.pingpong;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ExecutionServiceElegant {
    public static void main(String[] args) throws InterruptedException {

        ExecutorService executorLeft = Executors.newSingleThreadExecutor();
        ExecutorService executorRight = Executors.newSingleThreadExecutor();

        Runnable stepLeft = () -> System.out.println("left, " + Thread.currentThread());
        Runnable stepRight = () -> System.out.println("right, " + Thread.currentThread());

        for (int i = 0; i < 1000; i++) {
            CompletableFuture.runAsync(stepLeft, executorLeft).join();
            CompletableFuture.runAsync(stepRight, executorRight).join();
        }

        executorLeft.shutdown();
        executorRight.shutdown();

        System.out.println("Completed");
    }
}
