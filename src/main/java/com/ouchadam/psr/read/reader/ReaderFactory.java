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

    public Sav save() {
        return new Sav();
    }

    public State state() {
        return new State();
    }

    public class Sav {
        public PokemonFileReader<Money> money() {
            return new MoneyReader(Offsets.SAV_MONEY);
        }

        public PokemonFileReader<PlayerName> playerName() {
            return new PlayerNameReader(textReader, Offsets.SAV_PLAYER_NAME);
        }

        public PokemonFileReader<Team> playerTeam() {
            return new TeamReader(textReader, speciesToPokedex, new TeamOffsets(Offsets.SAV_TEAM));
        }

        public PokemonFileReader<Playtime> playTime() {
            return new PlaytimeReader(Offsets.SAV_PLAYTIME);
        }
    }

    public class State {
        public PokemonFileReader<Money> money() {
            return new MoneyReader(Offsets.STATE_MONEY);
        }

        public PokemonFileReader<PlayerName> playerName() {
            return new PlayerNameReader(textReader, Offsets.STATE_PLAYER_NAME);
        }

        public PokemonFileReader<Team> playerTeam() {
            return new TeamReader(textReader, speciesToPokedex, new TeamOffsets(Offsets.STATE_TEAM));
        }

        public PokemonFileReader<Playtime> playTime() {
            return new PlaytimeReader(Offsets.STATE_PLAYTIME);
        }
    }

}
