package hipravin.samples.reloadableconfig;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

class ApplicationConfigManagerTest {
    @Test
    void sampleLoad() {
        ApplicationConfigManager manager = new ApplicationConfigManager(
                Path.of("src/main/resources/data/application-custom-config.yml"));

        System.out.println(manager.getApplicationConfig());
    }

    @Test
    void sampleWatch() throws InterruptedException {
        Path configPath = Path.of("src/main/resources/data/application-custom-config.yml");
        ApplicationConfigManager manager = new ApplicationConfigManager(
                configPath);

        String template = """
                name: hipravin-samples
                network:
                  port: 8080
                  host: localhost%d
                """;

        AtomicLong counter = new AtomicLong(0);

        ScheduledExecutorService scheduledExecutor = Executors.newScheduledThreadPool(8);
        scheduledExecutor.scheduleAtFixedRate(() -> {
            try {
                Files.writeString(configPath, template.formatted(counter.incrementAndGet()));
            } catch (IOException e) {
                e.printStackTrace();
                throw new RuntimeException(e);
            }
        }, 100, 500, TimeUnit.MILLISECONDS);


        TimeUnit.MINUTES.sleep(1);
    }
}