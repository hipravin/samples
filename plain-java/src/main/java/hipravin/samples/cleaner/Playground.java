package hipravin.samples.cleaner;

import java.lang.ref.Cleaner;

public class Playground {
    public static void main(String[] args) {
        Cleaner cleaner = Cleaner.create();

        Object o = new Object();
        cleaner.register(o, () -> {
            System.out.println("Cleaning object: " + o);
        });
        //TODO: finish
    }

}
