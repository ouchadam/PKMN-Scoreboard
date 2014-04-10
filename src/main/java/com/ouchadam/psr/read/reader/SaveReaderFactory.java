package com.ouchadam.psr.read.reader;

import com.ouchadam.psr.read.SaveReader;
import com.ouchadam.psr.read.SpeciesToPokedex;
import com.ouchadam.psr.read.text.TextReader;
import com.ouchadam.psr.read.domain.Money;
import com.ouchadam.psr.read.domain.PlayerName;
import com.ouchadam.psr.read.domain.Playtime;
import com.ouchadam.psr.read.domain.Team;

public class SaveReaderFactory {

    private final TextReader textReader;
    private final SpeciesToPokedex speciesToPokedex;

    public SaveReaderFactory(TextReader textReader, SpeciesToPokedex speciesToPokedex) {
        this.textReader = textReader;
        this.speciesToPokedex = speciesToPokedex;
    }

    public SaveReader<PlayerName> playerName() {
        return new PlayerNameReader(textReader);
    }

    public SaveReader<Money> money() {
        return new MoneyReader();
    }

    public SaveReader<Team> playerTeam() {
        return new TeamPokemonReader(textReader, speciesToPokedex);
    }

    public SaveReader<Playtime> playTime() {
        return new PlaytimeReader();
    }
}
