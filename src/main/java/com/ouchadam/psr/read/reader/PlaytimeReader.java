package com.ouchadam.psr.read.reader;

import com.ouchadam.psr.read.PokemonSave;
import com.ouchadam.psr.read.SaveReader;
import com.ouchadam.psr.read.domain.Playtime;

public class PlaytimeReader implements SaveReader<Playtime> {

    private static final int OFFSET_PLAYTIME = 0x2CEE;
    private static final int OFFSET_MINUTES = OFFSET_PLAYTIME + 0x02;
    private static final int OFFSET_SECONDS = OFFSET_PLAYTIME + 0x03;

    PlaytimeReader() {}

    @Override
    public Playtime read(PokemonSave save) {
        int hours = save.getTwoInt(OFFSET_PLAYTIME, OFFSET_PLAYTIME + 1);
        int minutes = save.getInt(OFFSET_MINUTES);
        int seconds = save.getInt(OFFSET_SECONDS);
        return new Playtime(hours, minutes, seconds);
    }

}
