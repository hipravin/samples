package hipravin.samples.file;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.*;
import java.util.Set;
import java.util.function.Consumer;
import java.util.stream.Collectors;

public abstract class DirectoryWatchUtil {

    private DirectoryWatchUtil() {
    }

    public static Runnable fileWatch(Path file, Runnable onFileUpdated) {
        if(!Files.isRegularFile(file) || !Files.isReadable(file)) {
            throw new IllegalArgumentException("Path is not a regular file: " + file);
        }
        Path parent = file.getParent();
        if(!Files.isDirectory(parent) || !Files.isReadable(parent)) {
            throw new IllegalArgumentException("Parent path is not directory: " + parent);
        }

        Consumer<Set<Path>> searchForSpecificFileUpdate = (paths) -> {
            boolean fileUpdated = paths.stream()
                    .anyMatch(p -> {
                        try {
                            return Files.isSameFile(p, file);
                        } catch (IOException e) {
                            //log?
//                            throw new RuntimeException(e);
                            return false;
                        }
                    });

            if(fileUpdated) {
                onFileUpdated.run();
            }
        };

        return watchForUpdatesRunnable(parent, searchForSpecificFileUpdate);
    }

    /**
     * When any sub-path (either file or directory) under <code>dir</code> is modified then
     * <code>onUpdateConsumer</code> is called passing set of modified entries without recursive traversal.
     * This means that if file 'f1.txt' is modified at location 'dir/subpath1/f1.txt' then set will contain only 'subpath1'
     * but not 'subpath1/f1.txt'.
     *
     * @return Returns a runnable that needs to be submitted for execution.
     */
    public static Runnable watchForUpdatesRunnable(Path dir, Consumer<Set<Path>> onUpdateConsumer) {
        return () -> watchForUpdates(dir, onUpdateConsumer);
    }

    public static void watchForUpdates(Path dir, Consumer<Set<Path>> onUpdateConsumer) {
//        log.info("Start watching updates on dir: {}", dir.toString());

        try (WatchService watchService = startWatchService(dir)) {
            while (!Thread.interrupted()) {
                try {
                    WatchKey key = watchService.take();

                    Set<Path> paths = extractModifiedRelativePaths(key).stream()
                            .map(dir::resolve)
                            .collect(Collectors.toSet());

                    if(!paths.isEmpty()) {
                        onUpdateConsumer.accept(paths);
                    }

                    boolean valid = key.reset();
                    if (!valid) {
//                        log.warn("Stop watching dir '{}' due to key.reset() returned false", dir);
                        throw new IllegalStateException("key.reset() returned false on dir '%s'".formatted(dir));
                    }
                } catch (InterruptedException e) {
//                    log.info("FileWatch has been terminated due to interrupt at dir '{}'", dir);
                    Thread.currentThread().interrupt();
                }
            }
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    private static WatchService startWatchService(Path dir) throws IOException {
        WatchService watchService = FileSystems.getDefault().newWatchService();

        dir.register(watchService,
                StandardWatchEventKinds.ENTRY_CREATE,
                StandardWatchEventKinds.ENTRY_DELETE,
                StandardWatchEventKinds.ENTRY_MODIFY);

        return watchService;
    }

    @SuppressWarnings("unchecked")
    private static Set<Path> extractModifiedRelativePaths(WatchKey key) {
        return key.pollEvents().stream()
                .filter(e -> e.kind() != StandardWatchEventKinds.OVERFLOW)
                .map(e -> ((WatchEvent<Path>) e).context())
                .collect(Collectors.toSet());
    }
}
