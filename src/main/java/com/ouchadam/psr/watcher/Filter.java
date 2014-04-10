package com.ouchadam.psr.watcher;

public interface Filter {
    boolean isWantedFileType(String fileType);
}
