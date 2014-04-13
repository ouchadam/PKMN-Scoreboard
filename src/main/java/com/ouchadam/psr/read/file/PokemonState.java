package com.ouchadam.psr.read.file;

import com.ouchadam.psr.read.domain.PokemonFileType;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.RandomAccessFile;

public class PokemonState extends RandomFileAccessWrapper {

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
    public PokemonFileType getType() {
        return PokemonFileType.STATE;
    }
}
