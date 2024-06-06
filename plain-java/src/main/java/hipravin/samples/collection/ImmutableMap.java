package hipravin.samples.collection;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class ImmutableMap {
    public static void main(String[] args) {
        Map<Integer, Integer> mutable = new HashMap<>();
        mutable.put(1, 1);
        mutable.put(2, 2);

        var immutableCopy = Map.copyOf(mutable);
        var unmodifiableView = Collections.unmodifiableMap(mutable);
        mutable.put(3, 3);

        System.out.println(immutableCopy);
        System.out.println(unmodifiableView);

        try {
            immutableCopy.put(1, 1);
        } catch (RuntimeException e) {
            e.printStackTrace();
        }
        try {
            unmodifiableView.put(1, 1);
        } catch (RuntimeException e) {
            e.printStackTrace();
        }
    }
}
