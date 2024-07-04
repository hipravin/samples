package hipravin.samples.concurrency.pingpong;

import java.util.Map;
import java.util.concurrent.*;

/**
 * is it just a stupid trick or something more?
 */
public class CyclicBarrierSolution {
    public static void main(String[] args) throws BrokenBarrierException, InterruptedException {
        final CyclicBarrier barrier = new CyclicBarrier(2);
        int iterations = 1_000;
        //from CyclicBarrier javadoc: zero indicates the last to arrive
        Map<Integer, String> threadNumToMessage = Map.of(0, "pong", 1, "ping");

        Runnable pingPongRunnable = () -> {
            for (int i = 0; i < iterations; i++) {
                try {
                    int threadNum = barrier.await();
                    String message = threadNumToMessage.get(threadNum);
                    System.out.println(message + ", " + Thread.currentThread().getName());
                } catch (InterruptedException e) {
                    return;
                } catch (BrokenBarrierException e) {
                    throw new RuntimeException(e);
                }
            }
        };
        ExecutorService executor = Executors.newFixedThreadPool(2);

        CompletableFuture.runAsync(pingPongRunnable, executor);
        CompletableFuture.runAsync(pingPongRunnable, executor);

        if (!executor.awaitTermination(100, TimeUnit.MILLISECONDS)) {
            executor.shutdownNow();
            if (!executor.awaitTermination(1, TimeUnit.SECONDS)) {
                throw new RuntimeException("Failed to finish tasks");
            }
        }
    }
}
