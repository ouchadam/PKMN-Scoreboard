package com.ouchadam.psr.watcher;

public class PokemonSaveFilter implements Filter {

    private static final String POKEMON_SAV_TYPE = ".sav";

    @Override
    public boolean isWantedFileType(String fileType) {
        return POKEMON_SAV_TYPE.equals(fileType);
    }
}
