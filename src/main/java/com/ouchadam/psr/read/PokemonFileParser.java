package com.ouchadam.psr.read;

import com.ouchadam.psr.read.domain.*;
import com.ouchadam.psr.read.reader.PokemonFileReader;
import com.ouchadam.psr.read.reader.ReaderFactory;

public class PokemonFileParser implements PokemonFileReader<ParsedPokemonData> {

    private final ReaderFactory readerFactory;

    public PokemonFileParser(ReaderFactory readerFactory) {
        this.readerFactory = readerFactory;
    }

    @Override
    public ParsedPokemonData read(PokemonFile file) {
        switch (file.getType()) {
            case SAV:
                return createParsedData(file, readerFactory.save());

            case STATE:
                readerFactory.findOffset(file);
                return createParsedData(file, readerFactory.state());

            default:
                throw new RuntimeException("Unhandled : " + file.getType());
        }
    }

    private ParsedPokemonData createParsedData(PokemonFile file, ReaderFactory.Reader reader) {
        PokemonFileReader<PlayerName> playerName = reader.playerName();
        PokemonFileReader<Money> money = reader.money();
        PokemonFileReader<Team> team = reader.playerTeam();
        PokemonFileReader<Playtime> playTime = reader.playTime();
        return new ParsedPokemonData(playerName.read(file), playTime.read(file), money.read(file), team.read(file));
    }

}
