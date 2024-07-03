package hipravin.samples.concurrency.pingpong;

import java.util.concurrent.*;

public class WaitNotifyPingPongSolution {
    enum State {
        PING("ping"), PONG("pong");

        final String message;

        State(String message) {
            this.message = message;
        }

        public State switchState() {
            return switch (this) {
                case PING -> PONG;
                case PONG -> PING;
            };
        }

        public String getMessage() {
            return message;
        }
    }

    private final Object monitor = new Object();
    State state = State.PING;

    public void pingCycle() {
        try {
            workCycle(State.PING);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    public void pongCycle() {
        try {
            workCycle(State.PONG);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    private void workCycle(State cycleState) throws InterruptedException {
        while (!Thread.interrupted()) {
            synchronized (monitor) {
                while (state != cycleState) {
                    monitor.wait();
                }

                System.out.println(state.getMessage() + ", " + Thread.currentThread().getName());
                monitor.notifyAll();
                state = state.switchState();
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        var solution = new WaitNotifyPingPongSolution();

        ExecutorService executor = Executors.newFixedThreadPool(2);

        var cf1 = CompletableFuture.runAsync(solution::pongCycle, executor)
                .thenAccept(v -> System.out.println("Pong finished"));
        var cf2 = CompletableFuture.runAsync(solution::pingCycle, executor)
                .thenAccept(v -> System.out.println("Ping finished"));

        if (!executor.awaitTermination(100, TimeUnit.MILLISECONDS)) {
            executor.shutdownNow();
            if (!executor.awaitTermination(1, TimeUnit.SECONDS)) {
                throw new RuntimeException("failed to finish tasks");
            }
        }

        CompletableFuture.allOf(cf1, cf2).join();
    }
}
