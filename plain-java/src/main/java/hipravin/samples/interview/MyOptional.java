package hipravin.samples.interview;

import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Predicate;

public class MyOptional <T> {
    private final T value;

    private MyOptional() {
        value = null;
    }

    public MyOptional<T> filter(Predicate<? super T> predicate) {
        return (predicate.test(value)) ? this : empty();
    }

    public <U> MyOptional<U> map(Function<? super T, ? extends U> transform) {
        return new MyOptional<>(transform.apply(value));
    }

    public static <T>MyOptional<T> empty() {
        return new MyOptional<>();
    }

    public MyOptional(T value) {
        this.value = Objects.requireNonNull(value);
    }

    public boolean isPresent() {
        return value != null;
    }

    @Override
    public String toString() {
        return "MyOptional{" +
                "value=" + value +
                '}';
    }

    public static void main(String[] args) {
        MyOptional<String> mo = new MyOptional<>("123");
        var f = mo.filter(s -> !s.isEmpty()).map(s -> s + "4");

        System.out.println(f);

        var o = Optional.of("123").map(s -> null);

    }
}
