package hipravin.samples.interview;


import java.util.Arrays;
import java.util.Iterator;
import java.util.Objects;

public class MultiIterator<T> implements Iterator<T> {
    private final Iterator<Iterator<T>> iteratorsIterator;
    private Iterator<T> currentIterator;

    public MultiIterator(Iterator<T>... iterators) {
        Objects.requireNonNull(iterators);
        if(iterators.length == 0) {
            throw new IllegalArgumentException("Empty iterators array");
        }

        this.iteratorsIterator = Arrays.stream(iterators).iterator();
        this.currentIterator = iteratorsIterator.next();
    }

    @Override
    public boolean hasNext() {
        advanceToNextIteratorIfRequired();
        return currentIterator.hasNext();
    }

    @Override
    public T next() {
        advanceToNextIteratorIfRequired();
        return currentIterator.next();
    }

    private void advanceToNextIteratorIfRequired() {
        while(!currentIterator.hasNext() && iteratorsIterator.hasNext()) {
            currentIterator = iteratorsIterator.next();
        }
    }
}
