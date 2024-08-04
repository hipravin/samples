package hipravin.samples.concurrency.pingpong;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.*;

public class SynchronousQueuePingPongSolution2 {
    private final SynchronousQueue<String> handoffQueue = new SynchronousQueue<>();
    private static final String PING = "ping";
    private static final String PONG = "pong";

//    public SynchronousQueuePingPongSolution2() throws InterruptedException { //naive approach
//        handoffQueue.put(PING);
//    }

    void handleMessage() throws InterruptedException {
        String message = handoffQueue.take();
        System.out.printf("%s, %s%n", message, Thread.currentThread().getName());
        handoffQueue.put(replyMessage(message));
    }

    public void kickOff() {
        new Thread(() -> {
            try {
                handoffQueue.put(PING);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }).start();
    }

    public Runnable infiniteHandleRunnable() {
        return () -> {
            while (true) {
                try {
                    handleMessage();
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    return;
                }
            }
        };
    }

    private static String replyMessage(String message) {
        if (PING.equals(message)) {
            return PONG;
        } else if (PONG.equals(message)) {
            return PING;
        } else {
            throw new IllegalArgumentException("Unsupported message: " + message);
        }
    }

    private static final Map<String, String> REPLY_MESSAGE_MAP = Map.of(PING, PONG, PONG, PING);

    private static String replyMessage2(String message) {
        return Optional.ofNullable(REPLY_MESSAGE_MAP.get(message))
                .orElseThrow(() -> new IllegalArgumentException("Unsupported message: " + message));
    }

    public static void main(String[] args) throws InterruptedException, ExecutionException {
        var solution = new SynchronousQueuePingPongSolution2();

        var executor = Executors.newFixedThreadPool(3);
        var cf1 = CompletableFuture.runAsync(solution.infiniteHandleRunnable(), executor);
        var cf2 = CompletableFuture.runAsync(solution.infiniteHandleRunnable(), executor);
        var cf3 = CompletableFuture.runAsync(solution.infiniteHandleRunnable(), executor);
        solution.kickOff();
        if (!executor.awaitTermination(100, TimeUnit.MILLISECONDS)) {
            executor.shutdownNow();
            if(!executor.awaitTermination(1, TimeUnit.SECONDS)) {
                System.out.println("Time out");
            } else {
                System.out.println("Completed");
            }
        }
        CompletableFuture.allOf(cf1, cf2, cf3).get();
    }
}
