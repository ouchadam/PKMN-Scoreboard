package com.ouchadam.psr.read;

import com.ouchadam.psr.read.domain.Money;
import com.ouchadam.psr.read.domain.PlayerName;
import com.ouchadam.psr.read.domain.Playtime;
import com.ouchadam.psr.read.domain.Team;
import com.ouchadam.psr.read.reader.SaveReaderFactory;
import com.ouchadam.psr.read.text.TextReader;

import java.io.IOException;

public class PokemonSaveData {

    private final PlayerName playerName;
    private final Playtime playtime;
    private final Money money;
    private final Team team;

    PokemonSaveData(PlayerName playerName, Playtime playtime, Money money, Team team) {
        this.playerName = playerName;
        this.playtime = playtime;
        this.money = money;
        this.team = team;
    }

    public static PokemonSaveData from(String filename) throws IOException {
        return from(PokemonSave.from(filename));
    }

    static PokemonSaveData from(PokemonSave save) throws IOException {
        SaveReaderFactory saveReaderFactory = new SaveReaderFactory(new TextReader(), new SpeciesToPokedex());
        SaveReader<PlayerName> playerName = saveReaderFactory.playerName();
        SaveReader<Money> money = saveReaderFactory.money();
        SaveReader<Team> team = saveReaderFactory.playerTeam();
        SaveReader<Playtime> playTime = saveReaderFactory.playTime();
        return new PokemonSaveData(playerName.read(save), playTime.read(save), money.read(save), team.read(save));
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
