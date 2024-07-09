package hipravin.samples.reloadableconfig;

public record ApplicationConfig(
        String name,
        Network network) {

    public record Network(
            int port,
            String host) {
    }
}
