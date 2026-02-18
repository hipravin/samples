package hipravin.samples.interview;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Semaphore;

public class PingPong {
    private final Semaphore pingMutex = new Semaphore(1);
    private final Semaphore pongMutex = new Semaphore(0);

    public void pingIndefinitely()  {
        for(;;) {
            try {
                pingMutex.acquire();
                System.out.println("ping");
                pongMutex.release();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void pongIndefinitely()  {
        for(;;) {
            try {
                pongMutex.acquire();
                System.out.println("\t\tpong");
                pingMutex.release();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public static void main(String[] args) {
        var pp = new PingPong();

        var cfPing = CompletableFuture.runAsync(pp::pingIndefinitely);
        var cfPong = CompletableFuture.runAsync(pp::pongIndefinitely);

        CompletableFuture.allOf(cfPing, cfPong).join();
    }
}
