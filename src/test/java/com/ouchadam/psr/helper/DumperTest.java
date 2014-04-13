package com.ouchadam.psr.helper;

import com.ouchadam.psr.read.PokemonFile;
import com.ouchadam.psr.read.reader.PlayerNameReader;
import com.ouchadam.psr.read.reader.ReaderTestHelper;
import com.ouchadam.psr.read.text.TextReader;

import org.junit.Ignore;
import org.junit.Test;

public class DumperTest extends ReaderTestHelper {

    @Test
    public void dumpLinuxStateTeam() {
//        Dumper.dumpPokemon(LINUX_STATE, "DRAGONITE", 100);
        // 0xce1d   0x12bea
    }

    @Test
    public void dumpLinuxStateMoney() {
//        Dumper.dumpMoney(LINUX_STATE, new Money("999999"));
        // 0xd001   0x122b1
    }

    @Test
    public void dumpLinuxStatePlaytime() {
//        Dumper.dumpPlaytime(LINUX_STATE, new Playtime(255, 0, 0));
    }

    @Test
    public void dumpWindowsStateTeam() {
//        Dumper.dumpPokemon(WINDOWS_STATE, "DRAGONITE", 100);
        // 0x54db   0xb2a8
    }

    @Test
    public void dumpWindowsStateMoney() {
//        Dumper.dumpMoney(WIN_STATE, new Money("999999"));
        // 0x56bf   0xa96f
    }

    @Test
    public void dumpWindowsStatePlaytime() {
//        Dumper.dumpPlaytime(WINDOWS_STATE, new Playtime(255, 0, 0));
    }

    @Ignore
    @Test
    public void match_os_values() {

        PokemonFile windows = FileHelper.loadState("save1bk.sgm");
        PokemonFile linux = FileHelper.loadState("linux_save1bk.sgm");

        TextReader textReader = new TextReader();

        for (int i = 0x7942; i < windows.length(); i++) {

            try {
//                String winPlaytime = new PlaytimeStateReader(i - 0x7942).read(windows).formatted();
//                String linuxPlaytime = new PlaytimeStateReader(i).read(linux).formatted();
                String winName = new PlayerNameReader(textReader, i - 0x7942).read(windows).text();
                String linuxName = new PlayerNameReader(textReader, i).read(linux).text();
                if (winName.equals("ASH") && winName.equals(linuxName)) {
                    System.out.println("Boom! : " + Integer.toHexString(i));
                }
            } catch (Exception e) {
            }
        }

    }
}
