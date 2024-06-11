package hipravin.samples.pattern;

public class SingletonDoubleCheckedLocking {
    private static volatile SingletonDoubleCheckedLocking instance;

    private final String someContent;

    private SingletonDoubleCheckedLocking() {
        someContent = "singleton is supposed to contain something";
    }

    public static SingletonDoubleCheckedLocking getInstance() {
        SingletonDoubleCheckedLocking result = instance;

        if(result == null) {
            synchronized (SingletonDoubleCheckedLocking.class) {
                if(instance == null) {
                    result = new SingletonDoubleCheckedLocking();
                    result.init();

                    instance = result;
                }
            }
        }

        return result;
    }

    public void init() {
        //some initialization
    }
}
