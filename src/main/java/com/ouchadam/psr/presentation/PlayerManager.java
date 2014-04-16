package com.ouchadam.psr.presentation;

import com.ouchadam.psr.read.PokemonFileParser;
import com.ouchadam.psr.read.domain.ParsedPokemonData;
import com.ouchadam.psr.read.domain.PokemonFileOpener;
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
    private final PokemonFileOpener fileOpener;

    public PlayerManager(JPanel parentPanel, PokemonFileParser pokemonFileParser) {
        this(parentPanel, pokemonFileParser, new UiInvoker(), new PokemonFileOpener(), new HashMap<String, PlayerView>());
    }

    PlayerManager(JPanel parentPanel, PokemonFileParser pokemonFileParser, UiInvoker uiInvoker, PokemonFileOpener fileOpener, Map<String, PlayerView> players) {
        this.parentPanel = parentPanel;
        this.pokemonFileParser = pokemonFileParser;
        this.uiInvoker = uiInvoker;
        this.fileOpener = fileOpener;
        this.players = players;
    }

    @Override
    public void onFileChange(String path) {
        if (alreadyExists(path)) {
            uiInvoker.invokeLater(updateExisting, path);
        } else {
            addPlayerView(path);
        }
    }

    final UiInvoker.InvokeLater<String> updateExisting = new UiInvoker.InvokeLater<String>() {
        @Override
        public void invokeLater(final String what) {
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
        return pokemonFileParser.read(fileOpener.from(path));
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

    final UiInvoker.InvokeLater<PlayerView> addPlayerViewToPanel = new UiInvoker.InvokeLater<PlayerView>() {
        @Override
        public void invokeLater(PlayerView playerView) {
            parentPanel.add(playerView);
            parentPanel.revalidate();
            parentPanel.repaint();
        }
    };

}
