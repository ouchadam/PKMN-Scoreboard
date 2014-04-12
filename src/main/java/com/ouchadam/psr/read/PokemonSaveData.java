package com.ouchadam.psr.read;

import com.ouchadam.psr.read.domain.Money;
import com.ouchadam.psr.read.domain.PlayerName;
import com.ouchadam.psr.read.domain.Playtime;
import com.ouchadam.psr.read.domain.Team;
import com.ouchadam.psr.read.file.PokemonSave;
import com.ouchadam.psr.read.reader.PokemonFileReader;
import com.ouchadam.psr.read.reader.ReaderFactory;
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
        ReaderFactory readerFactory = new ReaderFactory(new TextReader(), new SpeciesToPokedex());
        PokemonFileReader<PlayerName> playerName = readerFactory.save().playerName();
        PokemonFileReader<Money> money = readerFactory.save().money();
        PokemonFileReader<Team> team = readerFactory.save().playerTeam();
        PokemonFileReader<Playtime> playTime = readerFactory.save().playTime();
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
