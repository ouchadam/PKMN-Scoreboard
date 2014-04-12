package com.ouchadam.psr.read.file;

import com.ouchadam.psr.read.PokemonFile;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.RandomAccessFile;

public class PokemonState extends RandomFileAccessWrapper implements PokemonFile {

    public PokemonState(RandomAccessFile file) {
        super(file);
    }

    public static PokemonState from(String filename) throws FileNotFoundException {
        validate(filename);
        return new PokemonState(new RandomAccessFile(new File(filename), "r"));
    }

    private static void validate(String filename) {
        if (!filename.endsWith(".sgm")) throw new IllegalArgumentException("File is not an .sgm");
    }

    @Override
    public int getInt(int index) {
        return super.getInt(index);
    }

    @Override
    public int getTwoInt(int offset, int offset2) {
        return super.getTwoInt(offset, offset2);
    }

    @Override
    public String getHex(int offset) {
        return super.getHex(offset);
    }

    @Override
    public long length() {
        return super.length();
    }
}
