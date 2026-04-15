package hipravin.samples.regex;


import org.openjdk.jmh.annotations.*;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.regex.Pattern;

public class PolynomialRegexBenchmarkConfig {
    private static final Path resourcesPath = Paths.get("file-lines/src/main/resources");

    private static final Pattern LEADING_SPACES = Pattern.compile("^(\\s+).*$");

    //not any better though, sonar finding for potential polynomial complexity is false positive
    private static final Pattern LEADING_SPACES_BETTER = Pattern.compile("^(\\s+)(\\S.*)?$");
    private static final Pattern OTHER_REGEX = Pattern.compile("^abcdefgh\\s\\d{3}$");

    private static int leadingSpaceCharCount(String s) {
        var m = LEADING_SPACES.matcher(s);
        if (m.find()) {
            return m.group(1).length();
        } else {
            return 0;
        }
    }
    private static int leadingSpaceCharCountBetter(String s) {
        var m = LEADING_SPACES_BETTER.matcher(s);
        if (m.find()) {
            return m.group(1).length();
        } else {
            return 0;
        }
    }

    @State(Scope.Benchmark)
    public static class ExecutionPlan {

        @Param({"10", "100", "1000", "10000", "100000", "1000000"})
//        @Param({"10", "100", "1000"})
        public String sizeString;

        public int size;
        public String testString;
        public String testStringTrailing;

        @Setup(Level.Invocation)
        public void setUp() {
            size = Integer.parseInt(sizeString);
            testString = (" ".repeat(size)).concat("a".repeat(size));
        }
    }

    @Benchmark
    public boolean benchOtherRegex(ExecutionPlan plan) {
        return OTHER_REGEX.matcher(plan.testString).find();
    }

    @Benchmark
    public int benchLeadingSpaces(ExecutionPlan plan) {
        int leadingSpaces = leadingSpaceCharCount(plan.testString);
        if (leadingSpaces != plan.size) {
            throw new IllegalStateException("Wrong result for size: %d/%d".formatted(plan.size, leadingSpaces));
        }
        return leadingSpaces;
    }

    @Benchmark
    public int benchLeadingSpacesBetter(ExecutionPlan plan) {
        int leadingSpaces = leadingSpaceCharCountBetter(plan.testString);
        if (leadingSpaces != plan.size) {
            throw new IllegalStateException("Wrong result for size: %d/%d".formatted(plan.size, leadingSpaces));
        }
        return leadingSpaces;
    }
}
