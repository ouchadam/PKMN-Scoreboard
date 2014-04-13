package com.ouchadam.psr.read.domain;

public class ParsedPokemonData {

    private final PlayerName playerName;
    private final Playtime playtime;
    private final Money money;
    private final Team team;

    public ParsedPokemonData(PlayerName playerName, Playtime playtime, Money money, Team team) {
        this.playerName = playerName;
        this.playtime = playtime;
        this.money = money;
        this.team = team;
    }

    public PlayerName getPlayerName() {
        return playerName;
    }

    public Playtime getPlaytime() {
        return playtime;
    }

    public Money getMoney() {
        return money;
    }

    public Team getTeam() {
        return team;
    }

    @Override
    public String toString() {
        return "PokemonSave{" +
                "playerName='" + playerName + '\'' +
                ", playtime=" + playtime +
                ", money=" + money +
                ", team=" + team +
                '}';
    }
}
