package com.ouchadam.psr.watcher;

public class PokemonSaveFilter implements Filter {

    private static final String POKEMON_SAV_TYPE = ".sav";
    private static final String POKEMON_STATE_TYPE = ".sgm";

    @Override
    public boolean isWantedFileType(String fileType) {
        return POKEMON_SAV_TYPE.equals(fileType) || POKEMON_STATE_TYPE.equals(fileType);
    }
}
