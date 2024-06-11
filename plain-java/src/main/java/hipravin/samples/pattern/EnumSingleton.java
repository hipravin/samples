package hipravin.samples.pattern;

public enum EnumSingleton {
    INSTANCE;

    public void doWhatSingletonsAreDesignedFor() {
        System.out.println("Doing stuff...");
    }
}
