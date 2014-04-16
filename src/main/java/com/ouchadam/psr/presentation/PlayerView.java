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

    private int dataId;

    public PlayerView() {
        setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
        setPreferredSize(new Dimension(300, 500));
        setBackground(Color.BLACK);
    }

    public void updateFrom(ParsedPokemonData save) {
        this.dataId = save.hashCode();
        removeAll();
        add(createLabel("Player : " + save.getPlayerName().text()));
        add(createLabel("Time : " + save.getPlaytime().formatted()));
        add(createLabel("Monies : " + save.getMoney().formatted()));
        for (Pokemon pokemon : save.getTeam().pokemons) {
            addPokemon(pokemon);
        }
    }

    private JLabel createLabel(String text) {
        JLabel label = new JLabel(text);
        label.setFont(new Font("Serif", Font.PLAIN, 30));
        label.setForeground(Color.WHITE);
        return label;
    }

    private void addPokemon(Pokemon pokemon) {
        JPanel panel = new JPanel();
        panel.setBackground(Color.BLACK);
        panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
        panel.setPreferredSize(new Dimension(300, 300));
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
        textPanel.add(createLabel("LV: " + pokemon.getLevel()));
        textPanel.add(createLabel(pokemon.getCurrentHp() + "/" + pokemon.getMaxHp()));
        return textPanel;
    }

    private JLabel createSpriteImage(int pokedexId) {
        try {
            URL resource = this.getClass().getClassLoader().getResource("sprites/" + pokedexId + ".png");
            BufferedImage myPicture = ImageIO.read(resource);
            Image scaled = myPicture.getScaledInstance(150, 150, 0);
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
