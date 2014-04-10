package com.ouchadam.psr.read.reader;

import com.ouchadam.psr.read.PokemonSave;
import com.ouchadam.psr.read.SaveReader;
import com.ouchadam.psr.read.domain.PlayerName;
import com.ouchadam.psr.read.text.TextReader;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.RandomAccessFile;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLDecoder;

import org.junit.Before;
import org.junit.Test;

import static org.fest.assertions.api.Assertions.assertThat;

public class PlayerNameReaderTest {

    SaveReader<PlayerName> playerNameReader;

    @Before
    public void setUp() throws Exception {
        playerNameReader = new PlayerNameReader(new TextReader());
    }

    @Test
    public void parse_the_correct_player_name() {
        PlayerName playerName = playerNameReader.read(loadTestSave());

        assertThat(playerName.text()).isEqualTo("ASH");
    }

    public static PokemonSave loadTestSave() {
        try {
            URL url = Thread.currentThread().getContextClassLoader().getResource("save1.sav");
            File file = new File(URLDecoder.decode(url.getFile(), "UTF-8"));
            return new PokemonSave(new RandomAccessFile(file, "r"));
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return null;
    }
}
