package hipravin.samples.interview;

import java.util.regex.Pattern;

class StatsCalculator {
    public static final String RESULT_TEMPLATE = "выше нуля: %d, ниже нуля: %d, равна нулю: %d";

    public String calculateStats(String input) {
        record StatsSummary(int negative, int zero, int positive) {
            public StatsSummary add(Integer value) {
                if (value < 0) {
                    return new StatsSummary(negative() + 1, zero(), positive());
                } else if (value > 0) {
                    return new StatsSummary(negative(), zero(), positive() + 1);
                } else {
                    return new StatsSummary(negative(), zero() + 1, positive());
                }
            }
            public StatsSummary combine(StatsSummary other) {
                return new StatsSummary(other.negative() + negative(), other.zero() + zero(), other.positive() + positive());
            }

        }


        StatsSummary summary = Pattern.compile("\\s+").splitAsStream(input)
                .map(Integer::parseInt)
                .reduce(new StatsSummary(0, 0, 0), StatsSummary::add, StatsSummary::combine);



        //

        return RESULT_TEMPLATE.formatted(summary.positive(), summary.negative(), summary.zero());
    }
}
