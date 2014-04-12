package com.ouchadam.psr.read.reader;

import com.ouchadam.psr.read.PokemonFile;

import org.junit.Test;

import static org.fest.assertions.api.Assertions.assertThat;

public class MoneyReaderTest extends ReaderTestHelper {

    @Test
    public void sav() {
        assertMoney(Offsets.SAV_MONEY, SAV);
    }

    @Test
    public void state_linux() {
        assertMoney(Offsets.STATE_MONEY, LINUX_STATE);
    }

    @Test
    public void state_windows() {
        assertMoney(windowsOffset(Offsets.STATE_MONEY), WIN_STATE);
    }

    private static void assertMoney(int moneyOffset, PokemonFile pokemonFile) {
        String money = new MoneyReader(moneyOffset).read(pokemonFile).formatted();

        assertThat(money).isEqualTo("$999999");
    }
}
