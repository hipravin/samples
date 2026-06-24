package hipravin.samples.benchmark;

import org.openjdk.jmh.annotations.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Stream;

/* https://thebackendguy.com/posts/performance-analysis-using-jmh/
Benchmarking Guidelines
Always have an empty base method to set a baseline.
If a benchmark is (unexpectedly) very close to the baseline, then there might be some JVM optimizations going on making the benchmark unreliable.
Don’t use loops to control benchmark method invocation, use batch size.
Avoid constants and locally scoped variables for which values are never changed to avoid JVM optimizations.
Benchmarking reports are just data, you need to understand patterns instead of relying on concrete values since they are specific to the underlying hardware.
 */

@Fork(value = 3)
@Warmup(iterations = 2)
@Measurement(iterations = 2)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
@State(Scope.Thread)
public class LinkedListArrayListBenchmark {
    private static AtomicLong counter = new AtomicLong();


    List<Integer> linkedList;
    List<Integer> arrayList;

    @Setup(Level.Iteration)
    public void setup() {
        counter.incrementAndGet();
        linkedList = new LinkedList<>();
        arrayList = new ArrayList<>();
    }

    @TearDown(Level.Iteration)
    public void tearDown() {
        linkedList.clear();
        arrayList.clear();
    }

    @Benchmark
    @BenchmarkMode(Mode.SingleShotTime)
    @Measurement(batchSize = 10_000)
    public void baseline() {
        counter.incrementAndGet();
        // baseline
    }

    @Benchmark
    @BenchmarkMode(Mode.SingleShotTime)
    @Measurement(batchSize = 10_000)
    public List<Integer> testLinkedListInsertions() {
        counter.incrementAndGet();
        linkedList.add(linkedList.size() + 1);
        return linkedList;
    }

    @Benchmark
    @BenchmarkMode(Mode.SingleShotTime)
    @Measurement(batchSize = 10_000)
    public List<Integer> testArrayListInsertions() {
        arrayList.add(arrayList.size() + 1);
        return arrayList;
    }

    public static void main(String[] args) throws Exception {
        System.out.printf("Counter before execution: %d%n", counter.get());
        var additionalArgs = new String[]{LinkedListArrayListBenchmark.class.getName()};
        var extendedArgs = Stream.concat(
                        Arrays.stream(additionalArgs),
                        Arrays.stream(args))
                .toArray(String[]::new);
        org.openjdk.jmh.Main.main(extendedArgs);

        System.out.printf("Counter after execution: %d%n", counter.get());
    }
}
