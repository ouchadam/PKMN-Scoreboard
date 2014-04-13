package com.ouchadam.psr.watcher;

import java.io.FilenameFilter;

public interface Filter extends FilenameFilter {
    boolean accept(String fileType);
}
