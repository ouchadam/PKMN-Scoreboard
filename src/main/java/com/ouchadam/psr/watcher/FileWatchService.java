package com.ouchadam.psr.watcher;

import java.io.IOException;
import java.nio.file.*;

import static java.nio.file.LinkOption.NOFOLLOW_LINKS;
import static java.nio.file.StandardWatchEventKinds.ENTRY_CREATE;
import static java.nio.file.StandardWatchEventKinds.ENTRY_MODIFY;

class FileWatchService {

    private final WatchService service;

    FileWatchService(WatchService service) {
        this.service = service;
    }

    public static FileWatchService newInstance(Path watchDirectory) throws IOException {
        validateDir(watchDirectory);
        WatchService service = watchDirectory.getFileSystem().newWatchService();
        watchDirectory.register(service, ENTRY_CREATE, ENTRY_MODIFY);
        return new FileWatchService(service);
    }

    private static void validateDir(Path path) {
        try {
            Boolean isFolder = (Boolean) Files.getAttribute(path, "basic:isDirectory", NOFOLLOW_LINKS);
            if (!isFolder) {
                throw new IllegalArgumentException("Path: " + path + " is not a folder");
            }
        } catch (IOException ioe) {
            throw new RuntimeException(ioe);
        }
    }

    public void startWatching(FileFoundListener fileFoundListener) throws InterruptedException {
        while (true) {
            if (!handleIteration(fileFoundListener).reset()) {
                break;
            }
        }
    }

    WatchKey handleIteration(FileFoundListener fileFoundListener) throws InterruptedException {
        WatchKey key = service.take();
        for (WatchEvent<?> watchEvent : key.pollEvents()) {
            WatchEvent.Kind<?> kind = watchEvent.kind();
            if (ENTRY_CREATE == kind) {
                onFileFound(fileFoundListener, getPath(watchEvent));
            } else if (StandardWatchEventKinds.ENTRY_MODIFY == kind) {
                onFileFound(fileFoundListener, getPath(watchEvent));
            }
        }
        return key;
    }

    private void onFileFound(FileFoundListener fileFoundListener, Path path) {
        fileFoundListener.onFileFound(path);
    }

    @SuppressWarnings("unchecked")
    private Path getPath(WatchEvent watchEvent) {
        return ((WatchEvent<Path>) watchEvent).context();
    }
}
