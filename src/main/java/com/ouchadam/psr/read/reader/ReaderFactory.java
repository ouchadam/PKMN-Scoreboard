package com.ouchadam.psr.read.reader;

import com.ouchadam.psr.read.OffsetFinder;
import com.ouchadam.psr.read.Pokedex;
import com.ouchadam.psr.read.PokemonFile;
import com.ouchadam.psr.read.domain.Money;
import com.ouchadam.psr.read.domain.PlayerName;
import com.ouchadam.psr.read.domain.Playtime;
import com.ouchadam.psr.read.domain.Team;
import com.ouchadam.psr.read.text.TextReader;

public class ReaderFactory {

    private final TextReader textReader;
    private final Pokedex pokedex;
    private final OffsetFinder offsetFinder;

    private int offset;

    public ReaderFactory(TextReader textReader, Pokedex pokedex, OffsetFinder offsetFinder) {
        this.textReader = textReader;
        this.pokedex = pokedex;
        this.offsetFinder = offsetFinder;
    }

    public Reader save() {
        return new Sav();
    }

    public Reader state() {
        return new State(offset);
    }

    public void findOffset(PokemonFile file) {
        offset = offsetFinder.findBaseOffset(file);
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
            return new TeamReader(textReader, pokedex, new TeamOffsets(Offsets.SAV_TEAM));
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
            return new MoneyReader(DeltaOffsets.STATE_DELTA_MONEY + offset);
        }

        @Override
        public PokemonFileReader<PlayerName> playerName() {
            return new PlayerNameReader(textReader, DeltaOffsets.STATE_DELTA_PLAYER_NAME + offset);
        }

        @Override
        public PokemonFileReader<Team> playerTeam() {
            return new TeamReader(textReader, pokedex, new TeamOffsets(DeltaOffsets.STATE_DELTA_TEAM + offset));
        }

        @Override
        public PokemonFileReader<Playtime> playTime() {
            return new PlaytimeReader(DeltaOffsets.STATE_DELTA_PLAYTIME + offset);
        }
    }

    public interface Reader {
        PokemonFileReader<Money> money();

        PokemonFileReader<PlayerName> playerName();

        PokemonFileReader<Team> playerTeam();

        PokemonFileReader<Playtime> playTime();
    }

}
