package hipravin.samples.collection;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class ImmutableList {
    public static void main(String[] args) {
        List<Integer> mutable = new ArrayList<>(Arrays.asList(1, 2, 3));
        var immutableCopy = List.copyOf(mutable);
        var unmodifiableView = Collections.unmodifiableList(mutable);

        mutable.add(4);

        System.out.println(immutableCopy);
        System.out.println(unmodifiableView);

        try {
            immutableCopy.add(1);
        } catch (RuntimeException e) {
            e.printStackTrace();
        }
        try {
            unmodifiableView.add(1);
        } catch (RuntimeException e) {
            e.printStackTrace();
        }
    }

}
