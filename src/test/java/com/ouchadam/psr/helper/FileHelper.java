package com.ouchadam.psr.helper;

import com.ouchadam.psr.read.PokemonFile;
import com.ouchadam.psr.read.file.PokemonSave;
import com.ouchadam.psr.read.file.PokemonState;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.RandomAccessFile;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLDecoder;

public class FileHelper {

    public static PokemonFile loadState(String filename) {
        try {
            URL url = Thread.currentThread().getContextClassLoader().getResource(filename);
            return PokemonState.from(URLDecoder.decode(url.getFile(), "UTF-8"));
        } catch (FileNotFoundException | UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }

    public static PokemonFile loadSave(String filename) {
        try {
            URL url = Thread.currentThread().getContextClassLoader().getResource(filename);
            File file = new File(URLDecoder.decode(url.getFile(), "UTF-8"));
            return new PokemonSave(new RandomAccessFile(file, "r"), filename);
        } catch (FileNotFoundException | UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }
}
