package com.ouchadam.psr.presentation;

import com.ouchadam.psr.read.domain.ParsedPokemonData;
import com.ouchadam.psr.read.domain.Pokemon;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;

public class PlayerView extends JPanel {

    private static final int VIEW_WIDTH = 300;
    private static final int VIEW_HEIGHT = 500;

    private static final int POK_IMAGE_WIDTH = 150;
    private static final int POK_IMAGE_HEIGHT = 150;

    private static final int TEXT_SIZE = 30;

    private static final int POKEMON_VIEW_WIDTH = 300;
    private static final int POKEMON_VIEW_HEIGHT = 300;

    private static final String PREFIX_PLAYER = "Player : ";
    private static final String PREFIX_TIME = "Time : ";
    private static final String PREFIX_MONIES = "Monies : ";
    private static final String PREFIX_LEVEL = "LV: ";
    private static final String HP_SEPARATOR = "/";

    private static final String DIR_SPRITES = "sprites/";
    private static final String TYPE_SPRITE = ".png";

    private int dataId;

    public PlayerView() {
        setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
        setPreferredSize(new Dimension(VIEW_WIDTH, VIEW_HEIGHT));
        setBackground(Color.BLACK);
    }

    public void updateFrom(ParsedPokemonData save) {
        this.dataId = save.hashCode();
        removeAll();
        add(createLabel(PREFIX_PLAYER + save.getPlayerName().text()));
        add(createLabel(PREFIX_TIME + save.getPlaytime().formatted()));
        add(createLabel(PREFIX_MONIES + save.getMoney().formatted()));
        for (Pokemon pokemon : save.getTeam().pokemons) {
            addPokemon(pokemon);
        }
    }

    private JLabel createLabel(String text) {
        JLabel label = new JLabel(text);
        label.setFont(new Font("Serif", Font.PLAIN, TEXT_SIZE));
        label.setForeground(Color.WHITE);
        return label;
    }

    private void addPokemon(Pokemon pokemon) {
        JPanel panel = new JPanel();
        panel.setBackground(Color.BLACK);
        panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
        panel.setPreferredSize(new Dimension(POKEMON_VIEW_WIDTH, POKEMON_VIEW_HEIGHT));
        panel.setAlignmentX(JPanel.LEFT_ALIGNMENT);
        panel.add(createSpriteImage(pokemon.getPokedexId()));
        panel.add(createPokemonText(pokemon));
        add(panel);
    }

    private JPanel createPokemonText(Pokemon pokemon) {
        JPanel textPanel = new JPanel();
        textPanel.setBackground(Color.BLACK);
        textPanel.setLayout(new BoxLayout(textPanel, BoxLayout.Y_AXIS));
        textPanel.add(createLabel(pokemon.getName()));
        textPanel.add(createLabel(PREFIX_LEVEL + pokemon.getLevel()));
        textPanel.add(createLabel(pokemon.getCurrentHp() + HP_SEPARATOR + pokemon.getMaxHp()));
        return textPanel;
    }

    private JLabel createSpriteImage(int pokedexId) {
        try {
            URL resource = this.getClass().getClassLoader().getResource(DIR_SPRITES + pokedexId + TYPE_SPRITE);
            BufferedImage myPicture = ImageIO.read(resource);
            Image scaled = myPicture.getScaledInstance(POK_IMAGE_WIDTH, POK_IMAGE_HEIGHT, 0);
            return new JLabel(new ImageIcon(scaled));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PlayerView that = (PlayerView) o;
        if (dataId != that.dataId) return false;
        return true;
    }

    @Override
    public int hashCode() {
        return dataId;
    }
}
