package com.ouchadam.psr.read.reader;

import com.ouchadam.psr.read.PokemonFile;
import com.ouchadam.psr.read.SpeciesToPokedex;
import com.ouchadam.psr.read.domain.Money;
import com.ouchadam.psr.read.domain.PlayerName;
import com.ouchadam.psr.read.domain.Playtime;
import com.ouchadam.psr.read.domain.Team;
import com.ouchadam.psr.read.text.TextReader;

public class ReaderFactory {

    private final TextReader textReader;
    private final SpeciesToPokedex speciesToPokedex;

    private int offset;

    public ReaderFactory(TextReader textReader, SpeciesToPokedex speciesToPokedex) {
        this.textReader = textReader;
        this.speciesToPokedex = speciesToPokedex;
        this.offset = 0;
    }

    public Reader save() {
        return new Sav();
    }

    public Reader state() {
        return new State(offset);
    }

    public void findOffset(PokemonFile file) {
        try {
            new State(0).playerTeam().read(file);
            this.offset = 0;
        } catch (PokemonFileReadException e) {
            this.offset = Offsets.WINDOWS_STATE_OFFSET;
        }
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
        private final int offset;

        public State(int offset) {
            this.offset = offset;
        }

        @Override
        public PokemonFileReader<Money> money() {
            return new MoneyReader(Offsets.STATE_MONEY - offset);
        }

        @Override
        public PokemonFileReader<PlayerName> playerName() {
            return new PlayerNameReader(textReader, Offsets.STATE_PLAYER_NAME - offset);
        }

        @Override
        public PokemonFileReader<Team> playerTeam() {
            return new TeamReader(textReader, speciesToPokedex, new TeamOffsets(Offsets.STATE_TEAM - offset));
        }

        @Override
        public PokemonFileReader<Playtime> playTime() {
            return new PlaytimeReader(Offsets.STATE_PLAYTIME - offset);
        }
    }

    public interface Reader {
        PokemonFileReader<Money> money();

        PokemonFileReader<PlayerName> playerName();

        PokemonFileReader<Team> playerTeam();

        PokemonFileReader<Playtime> playTime();
    }

}
