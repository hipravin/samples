package hipravin.samples.cleaner;

import java.lang.ref.Cleaner;
import java.lang.ref.PhantomReference;
import java.lang.ref.SoftReference;
import java.lang.ref.WeakReference;
import java.util.List;
import java.util.stream.LongStream;

public class Playground {

    static Object ref = null;

    public static void main(String[] args) {
        Cleaner cleaner = Cleaner.create();
        Cleaner cleaner2 = Cleaner.create();

        PhantomReference<Object> phantomRef;
        WeakReference<Object> weakRef;
        SoftReference<Object> softRef;
//        Object ref = null;
        for (int i = 0; i < 1; i++) {
            String someObject = new String("Object internal state");
//            Object someObject = new Object();
            phantomRef = new PhantomReference<Object>(someObject, null);
            weakRef = new WeakReference<>(someObject);
            softRef = new SoftReference<>(someObject);
//            ref = someObject;
            String someObjectState = new String(someObject);
            cleaner.register(someObject, () -> {
                System.out.println("Cleaning object by cleaner 1: " + someObjectState); // don't use reference to object here!
            });
            cleaner2.register(someObject, () -> {
                System.out.println("Cleaning object by cleaner 2: " + someObjectState); // don't use reference to object here!
            });
        }

        List<String> consumeMemoryList = LongStream.range(0, 10_000_000).mapToObj(String::valueOf)
                .toList();

        System.out.println(consumeMemoryList.size());
        System.out.printf("%d / %d ", Runtime.getRuntime().freeMemory(), Runtime.getRuntime().maxMemory());
        //TODO: finish
    }

}
