package hipravin.samples.interview;

import java.util.List;

public class MultiIteratorMain {
    public static void main(String[] args) {
        var l1 = List.of(1,2,3);
        List<Integer> l10 = List.of();
        List<Integer> l11 = List.of();
        var l2 = List.of(4,5,6);
        var l3 = List.of(7,8);
        var ls = List.of("a","b");

        @SuppressWarnings("unchecked")
        MultiIterator<Integer> mit = new MultiIterator<>(l1.iterator(), l10.iterator(), l11.iterator(), l2.iterator(), l3.iterator());

        mit.forEachRemaining(System.out::println);
    }
}
