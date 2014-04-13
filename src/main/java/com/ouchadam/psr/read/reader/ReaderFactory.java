package com.ouchadam.psr.read.reader;

import com.ouchadam.psr.read.SpeciesToPokedex;
import com.ouchadam.psr.read.domain.Money;
import com.ouchadam.psr.read.domain.PlayerName;
import com.ouchadam.psr.read.domain.Playtime;
import com.ouchadam.psr.read.domain.Team;
import com.ouchadam.psr.read.text.TextReader;

public class ReaderFactory {

    private final TextReader textReader;
    private final SpeciesToPokedex speciesToPokedex;

    public ReaderFactory(TextReader textReader, SpeciesToPokedex speciesToPokedex) {
        this.textReader = textReader;
        this.speciesToPokedex = speciesToPokedex;
    }

    public Reader save() {
        return new Sav();
    }

    public Reader state() {
        return new State();
    }

    public class Sav implements Reader {
        @Override
        public PokemonFileReader<Money> money() {
            return new MoneyReader(Offsets.SAV_MONEY);
        }

        @Override
        public PokemonFileReader<PlayerName> playerName() {
            return new PlayerNameReader(textReader, Offsets.SAV_PLAYER_NAME);
        }

        @Override
        public PokemonFileReader<Team> playerTeam() {
            return new TeamReader(textReader, speciesToPokedex, new TeamOffsets(Offsets.SAV_TEAM));
        }

        @Override
        public PokemonFileReader<Playtime> playTime() {
            return new PlaytimeReader(Offsets.SAV_PLAYTIME);
        }
    }

    public class State implements Reader {
        @Override
        public PokemonFileReader<Money> money() {
            return new MoneyReader(Offsets.STATE_MONEY);
        }

        @Override
        public PokemonFileReader<PlayerName> playerName() {
            return new PlayerNameReader(textReader, Offsets.STATE_PLAYER_NAME);
        }

        @Override
        public PokemonFileReader<Team> playerTeam() {
            return new TeamReader(textReader, speciesToPokedex, new TeamOffsets(Offsets.STATE_TEAM));
        }

        @Override
        public PokemonFileReader<Playtime> playTime() {
            return new PlaytimeReader(Offsets.STATE_PLAYTIME);
        }
    }

    public interface Reader {
        PokemonFileReader<Money> money();

        PokemonFileReader<PlayerName> playerName();

        PokemonFileReader<Team> playerTeam();

        PokemonFileReader<Playtime> playTime();
    }

}
