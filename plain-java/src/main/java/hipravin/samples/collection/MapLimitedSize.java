package hipravin.samples.collection;

import java.util.LinkedHashMap;
import java.util.Map;

public class MapLimitedSize {
    public static void main(String[] args) {
        final int MAX_SIZE = 3;
        Map<Integer, Integer> limitedMap = new LinkedHashMap<>() {
            @Override
            protected boolean removeEldestEntry(Map.Entry<Integer, Integer> eldest) {
                return (this.size() > MAX_SIZE);
            }
        };

        for (int i = 0; i < 10; i++) {
             limitedMap.put(i,i);
        }

        System.out.println(limitedMap);
    }
}
