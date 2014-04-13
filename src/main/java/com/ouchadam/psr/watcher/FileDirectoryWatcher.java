package com.ouchadam.psr.watcher;

import java.io.IOException;
import java.nio.file.*;

import static java.nio.file.LinkOption.NOFOLLOW_LINKS;
import static java.nio.file.StandardWatchEventKinds.*;

public class FileDirectoryWatcher {

    private final FileModifiedHandler fileModifiedHandler;
    private final Path watchDirectory;
    private final Filter filter;

    public FileDirectoryWatcher(FileModifiedHandler fileModifiedHandler, Path watchDirectory, Filter filter) {
        this.fileModifiedHandler = fileModifiedHandler;
        this.watchDirectory = watchDirectory;
        this.filter = filter;
        validateDir(watchDirectory);
    }

    public void startWatching() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    internalStartWatching();
                } catch (IOException | InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }).start();
    }

    public void internalStartWatching() throws IOException, InterruptedException {
        FileSystem fs = watchDirectory.getFileSystem();
        try (WatchService service = fs.newWatchService()) {
            watchDirectory.register(service, ENTRY_CREATE, ENTRY_MODIFY);
            WatchKey key = null;
            while (true) {
                key = service.take();
                WatchEvent.Kind<?> kind = null;
                for (WatchEvent<?> watchEvent : key.pollEvents()) {
                    kind = watchEvent.kind();
                    if (OVERFLOW == kind) {
                        continue;
                    } else if (ENTRY_CREATE == kind) {
                        Path newPath = getPath(watchEvent);
                        if (filter.accept(newPath.toString())) {
                            fileModifiedHandler.onNew(newPath, watchDirectory);
                        }
                    } else if (StandardWatchEventKinds.ENTRY_MODIFY == kind) {
                        Path newPath = getPath(watchEvent);
                        if (filter.accept(newPath.toString())) {
                            fileModifiedHandler.onModified(newPath, watchDirectory);
                        }
                    }
                }
                if (!key.reset()) {
                    break;
                }
            }
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }

    @SuppressWarnings("unchecked")
    private Path getPath(WatchEvent watchEvent) {
        return ((WatchEvent<Path>) watchEvent).context();
    }

    private void validateDir(Path path) {
        try {
            Boolean isFolder = (Boolean) Files.getAttribute(path, "basic:isDirectory", NOFOLLOW_LINKS);
            if (!isFolder) {
                throw new IllegalArgumentException("Path: " + path + " is not a folder");
            }
        } catch (IOException ioe) {
            throw new RuntimeException(ioe);
        }
    }

}
