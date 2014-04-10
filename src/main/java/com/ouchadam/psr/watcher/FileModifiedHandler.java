package com.ouchadam.psr.watcher;

import java.nio.file.Path;

public interface FileModifiedHandler {
    void onModified(Path filename, Path directory);
    void onNew(Path file, Path directory);
}
