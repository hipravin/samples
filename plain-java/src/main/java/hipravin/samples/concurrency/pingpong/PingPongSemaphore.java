package hipravin.samples.concurrency.pingpong;

import java.util.concurrent.*;

public class PingPongSemaphore {
    private final Semaphore pingSemaphore = new Semaphore(1);
    private final Semaphore pongSemaphore = new Semaphore(0);

    public void pingCycle() {
        try {
            while(!Thread.interrupted()) {
                ping();
            }
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public void pongCycle() {
        try {
            while(!Thread.interrupted()) {
                pong();
            }
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    private void ping() throws InterruptedException {
        pingSemaphore.acquire();
        System.out.println("ping " + Thread.currentThread().getName());
        pongSemaphore.release();
    }

    private void pong() throws InterruptedException {
        pongSemaphore.acquire();
        System.out.println("pong " + Thread.currentThread().getName());
        pingSemaphore.release();
    }

    public static void main(String[] args) {
        var pingPong = new PingPongSemaphore();

        var cf1 = CompletableFuture.runAsync(() -> pingPong.pingCycle());
        var cf2 = CompletableFuture.runAsync(() -> pingPong.pongCycle());

        try {
            CompletableFuture.allOf(cf1, cf2).get(100, TimeUnit.MILLISECONDS);
        } catch (InterruptedException | ExecutionException | TimeoutException e) {
            throw new RuntimeException(e);
        }
    }
}
