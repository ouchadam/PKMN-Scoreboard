package com.ouchadam.psr.read.domain;

import com.ouchadam.psr.read.PokemonFile;

import java.io.FileNotFoundException;

public class PokemonFileOpener {

    public PokemonFile from(String path) throws FileNotFoundException {
        String type = path.substring(path.lastIndexOf('.'));
        for (PokemonFileType pokemonFileType : PokemonFileType.values()) {
            if (pokemonFileType.fileType().equals(type)) {
                return pokemonFileType.file(path);
            }
        }
        throw new RuntimeException("Unhandled file type : " + path);
    }

}
