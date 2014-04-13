package com.ouchadam.psr.read.domain;

import com.ouchadam.psr.read.PokemonFile;
import com.ouchadam.psr.read.file.PokemonSave;
import com.ouchadam.psr.read.file.PokemonState;

import java.io.FileNotFoundException;

public enum PokemonFileType {
    SAV {
        @Override
        public String fileType() {
            return ".sav";
        }

        @Override
        public PokemonFile file(String path) throws FileNotFoundException {
            return PokemonSave.from(path);
        }
    },
    STATE {
        @Override
        public String fileType() {
            return ".sgm";
        }

        @Override
        public PokemonFile file(String path) throws FileNotFoundException {
            return PokemonState.from(path);
        }
    };

    public abstract String fileType();
    public abstract PokemonFile file(String path) throws FileNotFoundException;

    public static PokemonFile from(String path) throws FileNotFoundException {
        String type = path.substring(path.lastIndexOf('.'));
        for (PokemonFileType pokemonFileType : values()) {
            if (pokemonFileType.fileType().equals(type)) {
                return pokemonFileType.file(path);
            }
        }
        throw new RuntimeException("Unhandled file type : " + path);
    }

}
