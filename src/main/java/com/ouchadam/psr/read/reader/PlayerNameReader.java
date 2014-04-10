package com.ouchadam.psr.read.reader;

import com.ouchadam.psr.read.PokemonSave;
import com.ouchadam.psr.read.SaveReader;
import com.ouchadam.psr.read.text.TextReader;
import com.ouchadam.psr.read.domain.PlayerName;
import com.ouchadam.psr.read.text.TextTerminatedException;

class PlayerNameReader implements SaveReader<PlayerName> {

    private static final int OFFSET_PLAYER_NAME = 0x2598;
    private static final int PLAYER_NAME_SIZE = 8;
    private final TextReader textReader;

    PlayerNameReader(TextReader textReader) {
        this.textReader = textReader;
    }

    @Override
    public PlayerName read(PokemonSave save) {
        String playerName = "";
        for (int index = OFFSET_PLAYER_NAME; index < OFFSET_PLAYER_NAME + PLAYER_NAME_SIZE; index++) {
            int saveByte = save.getInt(index);
            try {
                playerName += textReader.toLetter(saveByte);
            } catch (TextTerminatedException e) {
                break;
            }
        }
        return new PlayerName(playerName);
    }

}
