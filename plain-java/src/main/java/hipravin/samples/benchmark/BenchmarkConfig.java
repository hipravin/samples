package hipravin.samples.benchmark;

import org.openjdk.jmh.annotations.*;

import java.security.SecureRandom;
import java.util.Random;
import java.util.UUID;

public class BenchmarkConfig {
    @State(Scope.Benchmark)
    public static class ExecutionPlan {

        @Param({"value1", "value2", "value3"})
        public String param1;

        public String param1Derived;

        @Setup(Level.Invocation)
        public void setUp() {
            param1Derived = "http://something" + param1;
            if (param1Derived.isEmpty()) {
                throw new IllegalArgumentException("bad parameter: " + param1Derived);
            }
        }
    }

    @Benchmark
    public long benchRandom(ExecutionPlan plan) {
        return new Random().nextLong();
    }

    @Benchmark
    public long benchSecureRandom(ExecutionPlan plan) {
        return new SecureRandom().nextLong();
    }

    @Benchmark
    public UUID benchRandomUuid(ExecutionPlan plan) {
        return UUID.randomUUID();
    }
}
