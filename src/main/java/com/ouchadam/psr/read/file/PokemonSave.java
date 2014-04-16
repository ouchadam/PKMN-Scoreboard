package com.ouchadam.psr.read.file;

import com.ouchadam.psr.read.domain.PokemonFileType;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.RandomAccessFile;

import static com.ouchadam.psr.read.domain.PokemonFileType.SAV;

public class PokemonSave extends RandomFileAccessWrapper {

    public PokemonSave(RandomAccessFile file, String filename) {
        super(file, filename);
    }

    public static PokemonSave from(String filename) throws FileNotFoundException {
        validate(filename);
        return new PokemonSave(new RandomAccessFile(new File(filename), "r"), filename);
    }

    private static void validate(String filename) {
        if (!filename.endsWith(SAV.fileType())) throw new IllegalArgumentException("File is not a " + SAV.fileType());
    }

    @Override
    public PokemonFileType getType() {
        return SAV;
    }
}
