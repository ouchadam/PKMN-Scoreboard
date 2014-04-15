package com.ouchadam.psr.presentation;

import com.ouchadam.psr.read.PokemonFileParser;
import com.ouchadam.psr.read.domain.ParsedPokemonData;
import com.ouchadam.psr.read.domain.PokemonFileType;
import com.ouchadam.psr.watcher.DirectoryChangeListener;

import javax.swing.*;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class PlayerManager implements DirectoryChangeListener {

    private final JPanel parentPanel;
    private final Map<String, PlayerView> players;
    private final PokemonFileParser pokemonFileParser;
    private final UiInvoker uiInvoker;

    public PlayerManager(JPanel parentPanel, PokemonFileParser pokemonFileParser, UiInvoker uiInvoker) {
        this.parentPanel = parentPanel;
        this.pokemonFileParser = pokemonFileParser;
        this.uiInvoker = uiInvoker;
        this.players = new HashMap<>();
    }

    @Override
    public void onFileChange(String path) {
        if (alreadyExists(path)) {
            uiInvoker.invokeLater(updateExisting, path);
        } else {
            addPlayerView(path);
        }
    }

    private final UiInvoker.InvokeLater<String> updateExisting = new UiInvoker.InvokeLater<String>() {
        @Override
        public void invokeLater(String what) {
            try {
                final PlayerView playerView = players.get(what);
                playerView.updateFrom(createData(what));
                playerView.revalidate();
                playerView.repaint();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    };

    private ParsedPokemonData createData(String path) throws FileNotFoundException {
        return pokemonFileParser.read(PokemonFileType.from(path));
    }

    private boolean alreadyExists(String path) {
        return players.containsKey(path);
    }

    private void addPlayerView(final String path) {
        System.out.println("Adding file : " + path);
        try {
            PlayerView playerView = createPlayerView(createData(path));
            players.put(path, playerView);
            uiInvoker.invokeLater(addPlayerViewToPanel, playerView);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private PlayerView createPlayerView(ParsedPokemonData parsedPokemonData) {
        PlayerView playerView = new PlayerView();
        playerView.updateFrom(parsedPokemonData);
        return playerView;
    }

    private final UiInvoker.InvokeLater<PlayerView> addPlayerViewToPanel = new UiInvoker.InvokeLater<PlayerView>() {
        @Override
        public void invokeLater(PlayerView playerView) {
            parentPanel.add(playerView);
            parentPanel.revalidate();
            parentPanel.repaint();
        }
    };

}
