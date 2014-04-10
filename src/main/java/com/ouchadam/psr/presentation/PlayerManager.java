package com.ouchadam.psr.presentation;

import com.ouchadam.psr.read.PokemonSaveData;
import com.ouchadam.psr.watcher.DirectoryChangeListener;

import javax.swing.*;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class PlayerManager implements DirectoryChangeListener {

    private final JPanel parentPanel;
    private final Map<String, PlayerView> players;

    public PlayerManager(JPanel parentPanel) {
        this.parentPanel = parentPanel;
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
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                try {
                    PlayerView playerView = players.get(path);
                    playerView.updateFrom(PokemonSaveData.from(path));
                    playerView.revalidate();
                    playerView.repaint();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private boolean alreadyExists(String path) {
        return players.containsKey(path);
    }

    private void addPlayerView(final String path) {
        System.out.println("Sav file : " + path);
        try {
            PlayerView playerView = createPlayerView(PokemonSaveData.from(path));
            players.put(path, playerView);
            addPlayerViewToPanel(playerView);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private PlayerView createPlayerView(PokemonSaveData pokemonSaveData) {
        PlayerView playerView = new PlayerView();
        playerView.updateFrom(pokemonSaveData);
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
