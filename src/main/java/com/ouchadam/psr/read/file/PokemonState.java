package com.ouchadam.psr.read.file;

import com.ouchadam.psr.read.domain.PokemonFileType;

import java.io.*;
import java.util.zip.GZIPInputStream;

import org.apache.commons.io.FileUtils;

import static com.ouchadam.psr.read.domain.PokemonFileType.STATE;

public class PokemonState extends RandomFileAccessWrapper {

    public PokemonState(RandomAccessFile file, String filename) {
        super(file, filename);
    }

    public static PokemonState from(String filename) throws FileNotFoundException {
        validate(filename);
        return new PokemonState(new RandomAccessFile(readGzip(filename), "r"), filename);
    }

    private static void validate(String filename) {
        if (!filename.endsWith(STATE.fileType()))
            throw new IllegalArgumentException("File is not an " + STATE.fileType());
    }

    public static File readGzip(String path) {
        try {
            File file = File.createTempFile("pkstate", ".gz");
            FileUtils.copyFile(new File(path), file);
            InputStream inStream = new GZIPInputStream(new FileInputStream(file));
            ByteArrayOutputStream baoStream2 = new ByteArrayOutputStream();
            byte[] buffer = new byte[8192];
            int len;
            while ((len = inStream.read(buffer)) > 0) {
                baoStream2.write(buffer, 0, len);
            }
            FileUtils.writeByteArrayToFile(file, baoStream2.toByteArray());
            return file;
        } catch (IOException ex) {
            throw new RuntimeException("Couldn't read file", ex);
        }
    }

    @Override
    public PokemonFileType getType() {
        return STATE;
    }
}
