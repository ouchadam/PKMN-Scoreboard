package com.ouchadam.psr.read.file;

import com.ouchadam.psr.read.PokemonFile;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.RandomAccessFile;

public class PokemonSave extends RandomFileAccessWrapper implements PokemonFile {

    public PokemonSave(RandomAccessFile file) {
        super(file);
    }

    public static PokemonSave from(String filename) throws FileNotFoundException {
        validate(filename);
        return new PokemonSave(new RandomAccessFile(new File(filename), "r"));
    }

    private static void validate(String filename) {
        if (!filename.endsWith(".sav")) throw new IllegalArgumentException("File is not a .sav");
    }

    @Override
    public int getInt(int index) {
        return super.getInt(index);
    }
}
