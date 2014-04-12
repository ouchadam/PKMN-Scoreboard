package com.ouchadam.psr.read.reader;

import com.ouchadam.psr.read.PokemonFile;
import com.ouchadam.psr.read.domain.PlayerName;
import com.ouchadam.psr.read.text.TextReader;
import com.ouchadam.psr.read.text.TextTerminatedException;

public class PlayerNameReader implements PokemonFileReader<PlayerName> {

    private static final int PLAYER_NAME_SIZE = 8;
    private final TextReader textReader;
    private final int baseOffset;

    public PlayerNameReader(TextReader textReader, int baseOffset) {
        this.textReader = textReader;
        this.baseOffset = baseOffset;
    }

    @Override
    public PlayerName read(PokemonFile save) {
        String playerName = "";
        for (int index = baseOffset; index < baseOffset + PLAYER_NAME_SIZE; index++) {
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
