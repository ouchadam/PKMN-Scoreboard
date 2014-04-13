package com.ouchadam.psr;

import com.ouchadam.psr.presentation.MainFrame;
import com.ouchadam.psr.presentation.PlayerManager;
import com.ouchadam.psr.presentation.UiReadyListener;
import com.ouchadam.psr.read.PokemonFileParser;
import com.ouchadam.psr.read.SpeciesToPokedex;
import com.ouchadam.psr.read.reader.ReaderFactory;
import com.ouchadam.psr.read.text.TextReader;
import com.ouchadam.psr.watcher.FileDirectoryWatcher;
import com.ouchadam.psr.watcher.FileModifiedHandler;
import com.ouchadam.psr.watcher.FileTouchedWatcher;
import com.ouchadam.psr.watcher.PokemonFileFilter;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.nio.file.Paths;

public class Main {

    private static final String POKEMON_SAVES_DIRECTORY = "saves/";

    public static void main(String... args) throws IOException {
        MainFrame.newInstance(uiReady);
    }

    private static final UiReadyListener uiReady = new UiReadyListener() {
        @Override
        public void onUiReady(JFrame frame) {
            JPanel parentPanel = new JPanel(new GridLayout(1, 4));
            parentPanel.setBackground(Color.BLACK);
            frame.add(parentPanel);
            FileModifiedHandler fileTouchedWatcher = new FileTouchedWatcher(new PlayerManager(parentPanel, new PokemonFileParser(new ReaderFactory(new TextReader(), new SpeciesToPokedex()))));
            new FileDirectoryWatcher(fileTouchedWatcher, Paths.get(POKEMON_SAVES_DIRECTORY), new PokemonFileFilter()).startWatching();
        }
    };

}
