package hipravin.samples.concurrency.quicksortperf.benchmark;

import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.infra.Blackhole;

import java.util.Arrays;
import java.util.concurrent.ThreadLocalRandom;

public class BenchmarkConfig {
    @State(Scope.Benchmark)
    public static class ExecutionPlan {

        @Param({"1000", "10000", "100000", "10000000"})
        public int arraySize;

        public int[] arrayUnsorted;

        @Setup(Level.Invocation)
        public void setUp() {
            arrayUnsorted = ThreadLocalRandom.current().ints(arraySize).toArray();
        }
    }

    @Benchmark
    public void benchSort(ExecutionPlan plan, Blackhole blackhole) {
        Arrays.sort(plan.arrayUnsorted);
        blackhole.consume(plan.arrayUnsorted);
    }

    @Benchmark
    public void benchParallelSort(ExecutionPlan plan, Blackhole blackhole) {
        Arrays.parallelSort(plan.arrayUnsorted);
        blackhole.consume(plan.arrayUnsorted);
    }
}
