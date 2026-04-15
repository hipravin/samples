package hipravin.samples;

public class Playground {
    public static void main(String[] args) throws InterruptedException {

        try {
            suppressedException();
        } catch (RuntimeException e) {
            e.printStackTrace();
        }
    }

    static void suppressedException() {
        try {
            throw new RuntimeException("Root exception");
        } catch (RuntimeException e) {
            throw new RuntimeException("Catch exception");
        } finally {
            throw new RuntimeException("Finally exception");
        }
    }
}
