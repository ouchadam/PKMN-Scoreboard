package com.ouchadam.psr.read.reader;

import com.ouchadam.psr.read.PokemonFile;
import com.ouchadam.psr.read.domain.Playtime;

public class PlaytimeReader implements PokemonFileReader<Playtime> {

    private static final int OFFSET_MINUTES = 0x02;
    private static final int OFFSET_SECONDS = 0x03;

    private final int baseOffset;

    public PlaytimeReader(int baseOffset) {
        this.baseOffset = baseOffset;
    }

    @Override
    public Playtime read(PokemonFile save) {
        int hours = save.getTwoInt(baseOffset, baseOffset + 1);
        int minutes = save.getInt(baseOffset + OFFSET_MINUTES);
        int seconds = save.getInt(baseOffset + OFFSET_SECONDS);
        return new Playtime(hours, minutes, seconds);
    }

}
