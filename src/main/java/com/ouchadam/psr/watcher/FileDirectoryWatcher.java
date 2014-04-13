package com.ouchadam.psr.watcher;

import java.io.IOException;
import java.nio.file.Path;

public class FileDirectoryWatcher {

    private final FileModifiedHandler fileModifiedHandler;
    private final Path watchDirectory;
    private final Filter filter;

    public FileDirectoryWatcher(FileModifiedHandler fileModifiedHandler, Path watchDirectory, Filter filter) {
        this.fileModifiedHandler = fileModifiedHandler;
        this.watchDirectory = watchDirectory;
        this.filter = filter;
    }

    public void startWatching() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    FileWatchService.newInstance(watchDirectory).startWatching(onFileFound);
                } catch (IOException | InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }).start();
    }

    private final FileFoundListener onFileFound = new FileFoundListener() {
        @Override
        public void onFileFound(Path path) {
            if (filter.accept(path.toString())) {
                fileModifiedHandler.onNew(path, watchDirectory);
            }
        }
    };

}
