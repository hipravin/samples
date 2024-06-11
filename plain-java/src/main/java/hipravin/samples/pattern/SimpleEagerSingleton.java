package hipravin.samples.pattern;

public class SimpleEagerSingleton {
    private static final SimpleEagerSingleton instance = new SimpleEagerSingleton();

    public static SimpleEagerSingleton getInstance() {
        return instance;
    }
}
