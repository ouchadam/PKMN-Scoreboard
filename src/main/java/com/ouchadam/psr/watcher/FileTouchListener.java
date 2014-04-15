package com.ouchadam.psr.watcher;

import java.nio.file.Path;

public interface FileTouchListener {
    void onFileTouched(Path file, Path directory);
}
