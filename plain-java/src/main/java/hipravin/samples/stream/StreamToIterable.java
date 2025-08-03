package hipravin.samples.stream;

import java.util.stream.Stream;

/**
 * Stream is single use data structure thus doesn't implement iterable.
 * However, it provides exactly same .iterator() method.
 * Stream doesn't implement Iterable: The main reason is Iterable also has a re-iterable semantic, while Stream is not.
 */
public class StreamToIterable {
    public static void main(String[] args) {
        //solution 1
        for (String s : iterableOf(sampleStrings())) {
            System.out.println(s);
        }

        //solution 2
        for (String s : (Iterable<String>) sampleStrings()::iterator) {
            System.out.println(s);
        }
    }

    static Stream<String> sampleStrings() {
        return Stream.of("a", "b", "c");
    }

    public static <E> Iterable<E> iterableOf(Stream<E> stream) {
        return stream::iterator;
    }
}
