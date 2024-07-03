package hipravin.samples.concurrency.pingpong;

import java.util.concurrent.*;

/**
 * Synchronous queues are similar to rendezvous channels used in CSP and Ada.
 * They are well suited for handoff designs, in which an object running in one thread must sync up with
 * an object running in another thread in order to hand it some information, event, or task
 */
public class SynchronousQueuePingPongSolution {
    private final SynchronousQueue<String> handOffQueue = new SynchronousQueue<>();

    private void ping() throws InterruptedException {
        while (!Thread.interrupted()) {
            String message = handOffQueue.take();
            System.out.println(message + " " + Thread.currentThread().getName());
            handOffQueue.put("pong");
        }
    }

    private void pong() throws InterruptedException {
        handOffQueue.put("ping");

        while (!Thread.interrupted()) {
            String message = handOffQueue.take();
            System.out.println(message + " " + Thread.currentThread().getName());
            handOffQueue.put("ping");
        }
    }

    public Runnable awaitAndPing() {
        return () -> {
            try {
                ping();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        };
    }
    public Runnable awaitAndPong() {
        return () -> {
            try {
                pong();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        };
    }

    public static void main(String[] args) {
        var solution = new SynchronousQueuePingPongSolution();
        ExecutorService executor = Executors.newFixedThreadPool(2);

        var pingFuture = CompletableFuture.runAsync(solution.awaitAndPing(), executor);
        var pongFuture = CompletableFuture.runAsync(solution.awaitAndPong(), executor);
        executor.shutdown();
        try {
            if(!executor.awaitTermination(100, TimeUnit.MILLISECONDS)) {
                System.out.println("Shutdown now");
                executor.shutdownNow();
                if(executor.awaitTermination(1, TimeUnit.SECONDS)) {
                    System.out.println("Completed");
                } else {
                    System.err.println("Yet not completed");
                }
            };
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        CompletableFuture.allOf(pingFuture, pongFuture).join();
    }
}
