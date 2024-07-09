package hipravin.samples.reloadableconfig;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import hipravin.samples.file.DirectoryWatchUtil;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.function.Supplier;

public class ApplicationConfigManager {
    private ObjectMapper yamlMapper;
    private final Path applicationConfigPath;

    private volatile ApplicationConfig applicationConfig;

    public ApplicationConfigManager(Path applicationConfigPath) {
        this.applicationConfigPath = applicationConfigPath;

        initYamlMapper();
        startConfigWatcher();
        this.applicationConfig = loadConfig();
    }

    private void startConfigWatcher() {
        Thread watchThread = new Thread(DirectoryWatchUtil.fileWatch(
                applicationConfigPath, this::handleConfigReload));
        watchThread.setDaemon(true);
        watchThread.setName("updates." + applicationConfigPath.getFileName());

        watchThread.start();
    }

    private void handleConfigReload() {
        ApplicationConfig applicationConfigUpdated = loadConfig();
        if(this.applicationConfig == null || !this.applicationConfig.equals(applicationConfigUpdated)) {
            this.applicationConfig = applicationConfigUpdated;
            System.out.println("Config updated: " + this.applicationConfig);
        }
    }

    private ApplicationConfig loadConfig() {
        return loadYamlFromPath(applicationConfigPath, ApplicationConfig.class);
    }

    private void initYamlMapper() {
        yamlMapper = new ObjectMapper(new YAMLFactory());
        //mapper configuration
    }

    public <T> T loadYamlFromPath(Path configPath, Class<T> valueType) {
        try (BufferedReader reader = Files.newBufferedReader(configPath, StandardCharsets.UTF_8)) {
            String fileContent = Files.readString(configPath);
            return yamlMapper.readValue(reader, valueType);
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    public ApplicationConfig getApplicationConfig() {
        return applicationConfig;
    }
}
