package com.ouchadam.psr.presentation;

import com.ouchadam.psr.read.PokemonFile;
import com.ouchadam.psr.read.PokemonFileParser;
import com.ouchadam.psr.read.domain.*;

import javax.swing.*;
import java.util.ArrayList;
import java.util.HashMap;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Spy;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

public class PlayerManagerTest {

    private static final String PATH = "path.sgm";

    @Mock JPanel panel;
    @Mock PokemonFileParser pokemonFileParser;
    @Mock UiInvoker uiInvoker;
    @Mock PokemonFileOpener fileOpener;
    @Spy HashMap<String, PlayerView> players;
    @Mock ParsedPokemonData pokemonData;

    private PlayerManager playerManager;

    @Before
    public void setUp() throws Exception {
        initMocks(this);
        pokemonData = createMockPokemonData();
        playerManager = new PlayerManager(panel, pokemonFileParser, uiInvoker, fileOpener, players);
    }

    private ParsedPokemonData createMockPokemonData() {
        when(pokemonData.getMoney()).thenReturn(new Money("999999"));
        when(pokemonData.getPlayerName()).thenReturn(new PlayerName("Test"));
        when(pokemonData.getPlaytime()).thenReturn(new Playtime(0,0,0));
        when(pokemonData.getTeam()).thenReturn(new Team(0, new ArrayList<Pokemon>()));
        return pokemonData;
    }

    @Test
    public void update_existing_when_path_is_already_within_the_map() {
        when(players.containsKey(PATH)).thenReturn(true);

        playerManager.onFileChange(PATH);

        verify(uiInvoker).invokeLater(playerManager.updateExisting, PATH);
    }

    @Test
    public void addNew_when_path_is_not_within_the_map() {
        when(players.containsKey(PATH)).thenReturn(false);
        when(pokemonFileParser.read(any(PokemonFile.class))).thenReturn(pokemonData);

        playerManager.onFileChange(PATH);

        verify(uiInvoker).invokeLater(playerManager.addPlayerViewToPanel, players.get(PATH));
    }

}
