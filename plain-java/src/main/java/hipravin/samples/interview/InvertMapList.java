package hipravin.samples.interview;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class InvertMapList {
    public static void main(String[] args) throws InterruptedException {

        Map<Long, List<Long>> data = new HashMap<>();
        data.put(1L, List.of(0L, 1L, 2L));
        data.put(2L, List.of(3L, 4L));

        ///
        record Pair(Long k, Long v) {
        }
        Map<Long, Long> inverted = data.entrySet().stream()
                .flatMap(e -> e.getValue().stream()
                        .map(v -> new Pair(e.getKey(), v)))
                .collect(Collectors.toMap(Pair::v, Pair::k, (a, b) -> b));
        ///

        System.out.println(inverted);
        Map<Long, Long> inverted2 = data.entrySet().stream()
                .collect(HashMap::new, (result, entry) -> {
                    entry.getValue().forEach(v -> result.put(v, entry.getKey()));
                }, HashMap::putAll);

        System.out.println(inverted2);
    }
}
