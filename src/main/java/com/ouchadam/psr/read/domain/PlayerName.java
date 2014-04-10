package com.ouchadam.psr.read.domain;

public class PlayerName {

    private final String playerName;

    public PlayerName(String playerName) {
        this.playerName = playerName;
    }

    public String text() {
        return playerName;
    }
}
