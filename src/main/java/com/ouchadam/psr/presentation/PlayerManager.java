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

    public PlayerManager(JPanel parentPanel, PokemonFileParser pokemonFileParser) {
        this.parentPanel = parentPanel;
        this.pokemonFileParser = pokemonFileParser;
        this.players = new HashMap<>();
    }

    @Override
    public void onFileChange(String path) {
        if (alreadyExists(path)) {
            updateExisting(path);
        } else {
            addPlayerView(path);
        }
    }

    private void updateExisting(final String path) {
        final PlayerView playerView = players.get(path);
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                try {
                    playerView.updateFrom(createData(path));
                    playerView.revalidate();
                    playerView.repaint();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private ParsedPokemonData createData(String path) throws FileNotFoundException {
        return pokemonFileParser.read(PokemonFileType.from(path));
    }

    private boolean alreadyExists(String path) {
        System.out.println(path + " Exists? : " + players.containsKey(path));
        return players.containsKey(path);
    }

    private void addPlayerView(final String path) {
        System.out.println("Adding file : " + path);
        try {
            PlayerView playerView = createPlayerView(createData(path));
            System.out.println("Adding : " + path);
            players.put(path, playerView);
            addPlayerViewToPanel(playerView);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private PlayerView createPlayerView(ParsedPokemonData parsedPokemonData) {
        PlayerView playerView = new PlayerView();
        playerView.updateFrom(parsedPokemonData);
        return playerView;
    }

    private void addPlayerViewToPanel(final PlayerView playerView) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                parentPanel.add(playerView);
                parentPanel.revalidate();
                parentPanel.repaint();
            }
        });
    }
}
