package hipravin.samples.concurrency;

import java.util.List;
import java.util.SplittableRandom;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.IntStream;

public class WorkStealingOrder {

    private static CountDownLatch startLatch = new CountDownLatch(1);

    static void main() {
//        var executor = Executors.newFixedThreadPool(4);
        var executor = Executors.newWorkStealingPool(4);

        int ntasks = 100;
        List<Long> results = new CopyOnWriteArrayList<>();
        try (executor) {
            for (int i = 0; i < ntasks; i++) {
                final long taskid = i;
                executor.submit(() -> {
                    try {
                        startLatch.await();
                        Thread.sleep(ThreadLocalRandom.current().nextInt(3, 100));
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                    results.add(taskid);
                });
            }
            startLatch.countDown();
        }

        record PositionedValue(long val, long position) {
        }

        var positionedValues = IntStream.range(0, results.size())
                .mapToObj(pos -> new PositionedValue(results.get(pos), pos))
                .toList();

        long collectiveDiff = positionedValues.stream()
                        .reduce(0L, (diff, pv) -> (diff + Math.abs (pv.position() - pv.val())),
                                Long::sum);

        System.out.println(collectiveDiff);

        System.out.println(results);
    }
}
