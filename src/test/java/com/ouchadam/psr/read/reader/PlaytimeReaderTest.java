package com.ouchadam.psr.read.reader;

import com.ouchadam.psr.read.PokemonFile;

import org.junit.Test;

import static org.fest.assertions.api.Assertions.assertThat;

public class PlaytimeReaderTest extends ReaderTestHelper {

    @Test
    public void sav() {
        assertPlaytime(Offsets.SAV_PLAYTIME, SAV);
    }

    @Test
    public void state_linux() {
        assertPlaytime(Offsets.STATE_PLAYTIME, LINUX_STATE);
    }

    @Test
    public void state_windows() {
        assertPlaytime(windowsOffset(Offsets.STATE_PLAYTIME), WIN_STATE);
    }

    private static void assertPlaytime(int playtimeOffset, PokemonFile file) {
        String playtime = new PlaytimeReader(playtimeOffset).read(file).formatted();

        assertThat(playtime).isEqualTo("255:00:00");
    }

}
