package com.ouchadam.psr.read.reader;

import com.ouchadam.psr.read.PokemonFile;
import com.ouchadam.psr.read.text.TextReader;

import org.junit.Test;

import static org.fest.assertions.api.Assertions.assertThat;

public class PlayerNameReaderTest extends ReaderTestHelper {

    @Test
    public void sav() {
        assertPlayerName(Offsets.SAV_PLAYER_NAME, SAV);
    }

    @Test
    public void state_linux() {
        assertPlayerName(Offsets.STATE_PLAYER_NAME, LINUX_STATE);
    }

    @Test
    public void state_windows() {
        assertPlayerName(windowsOffset(Offsets.STATE_PLAYER_NAME), WIN_STATE);
    }

    private static void assertPlayerName(int playerNameOffset, PokemonFile file) {
        String playerName = new PlayerNameReader(new TextReader(), playerNameOffset).read(file).text();

        assertThat(playerName).isEqualTo("ASH");
    }

}
