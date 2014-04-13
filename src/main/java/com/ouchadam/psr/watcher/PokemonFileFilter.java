package com.ouchadam.psr.watcher;

import java.io.File;

public class PokemonFileFilter implements Filter {

    private static final String POKEMON_SAV_TYPE = ".sav";
    private static final String POKEMON_STATE_TYPE = ".sgm";

    @Override
    public boolean accept(File dir, String name) {
        return accept(name);
    }

    @Override
    public boolean accept(String fileType) {
        return POKEMON_SAV_TYPE.equals(getFileType(fileType)) || POKEMON_STATE_TYPE.equals(getFileType(fileType));
    }

    private String getFileType(String name) {
        return name.toString().substring(name.lastIndexOf('.'));
    }
}
