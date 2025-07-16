package hipravin.samples;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class Playground {
    public static void main(String[] args) throws InterruptedException {
        List<String> strings = new CopyOnWriteArrayList<>(Arrays.asList("1", "2", "3")); //Element-changing operations on iterators themselves (remove, set, and add) are not supported. These methods throw UnsupportedOperationException.


        Iterator<String> i1 = strings.iterator();
        Iterator<String> i2 = strings.iterator();

        i1.next();
        i1.remove();

        i2.next();
        i2.remove();


        System.out.println(strings);



    }


}
