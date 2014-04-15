package com.ouchadam.psr.watcher;

import java.io.File;
import java.nio.file.Path;

public class FileTouchedWatcher implements FileTouchListener {

    private final DirectoryChangeListener fileListener;

    public FileTouchedWatcher(DirectoryChangeListener fileListener) {
        this.fileListener = fileListener;
    }

    @Override
    public void onFileTouched(Path file, Path directory) {
        String path = directory.toString() + File.separator + file.toString();
        blockUntilFileIsReady(path, fileListener);
    }

    private void blockUntilFileIsReady(final String path, final DirectoryChangeListener directoryChangeListener) {
        File sourceFile = new File(path);
        int counter = 0;
        while (!sourceFile.renameTo(sourceFile)) {
            try {
                counter++;
                Thread.sleep(1000);
                if (counter > 10) {
                    System.out.println("File read timed out");
                    break;
                }
            } catch (InterruptedException e) {
                System.out.println(e);
            }
        }
        directoryChangeListener.onFileChange(path);
    }
}
