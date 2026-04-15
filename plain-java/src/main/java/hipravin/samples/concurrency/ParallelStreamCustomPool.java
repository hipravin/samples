package hipravin.samples.concurrency;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.stream.LongStream;

public class ParallelStreamCustomPool {
    static void main() throws ExecutionException, InterruptedException {
        var pool = Executors.newWorkStealingPool(1);
//        var pool = Executors.newFixedThreadPool(1);

        Callable<Long> parallelSum = () -> {
            var sum = LongStream.range(0, 10).parallel()
                    .peek(l -> System.out.printf("Value: %d, thread: %s%n", l, Thread.currentThread()))
                    .sum();

            return sum;
        };

        var future = pool.submit(parallelSum);
        pool.shutdown();

        System.out.println(future.get());
    }
}
