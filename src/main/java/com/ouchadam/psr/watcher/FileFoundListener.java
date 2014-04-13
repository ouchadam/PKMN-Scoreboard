package com.ouchadam.psr.watcher;

import java.nio.file.Path;

interface FileFoundListener {
    void onFileFound(Path path);

}
