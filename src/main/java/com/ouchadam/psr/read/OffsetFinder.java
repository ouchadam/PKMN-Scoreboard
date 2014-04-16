package com.ouchadam.psr.read;

import com.ouchadam.psr.read.domain.Money;
import com.ouchadam.psr.read.domain.PlayerName;
import com.ouchadam.psr.read.domain.Team;
import com.ouchadam.psr.read.reader.*;
import com.ouchadam.psr.read.text.TextReader;
import com.ouchadam.psr.read.validate.*;

public class OffsetFinder {

    private final Validator<PlayerName> playerNameValidator;
    private final Validator<Money> moneyValidator;
    private final Validator<Team> teamValidator;

    private final TextReader textReader;
    private final Pokedex pokedex;

    public static OffsetFinder newInstance() {
        Pokedex pokedex = new Pokedex();
        return new OffsetFinder(new PlayerNameValidator(),
                new MoneyValidator(),
                new TeamValidator(new PokemonValidator(pokedex)),
                new TextReader(), pokedex);
    }

    public OffsetFinder(Validator<PlayerName> playerNameValidator, Validator<Money> moneyValidator, Validator<Team> teamValidator, TextReader textReader, Pokedex pokedex) {
        this.playerNameValidator = playerNameValidator;
        this.moneyValidator = moneyValidator;
        this.teamValidator = teamValidator;
        this.textReader = textReader;
        this.pokedex = pokedex;
    }

    public int findBaseOffset(PokemonFile file) {
        for (int index = 0; index < file.length(); index++) {
            try {
                boolean nameIsValid = playerNameValidator.isValid(readPlayerName(file, index));
                boolean moneyIsValid = moneyValidator.isValid(readMoney(file, index));
                boolean teamIsValid = teamValidator.isValid(readTeam(file, index));
                if (nameIsValid && teamIsValid && moneyIsValid) {
                    return index;
                }
            } catch (Exception e) {
                continue;
            }
        }
        throw new RuntimeException("Never found a offset");
    }

    private PlayerName readPlayerName(PokemonFile file, int index) {
        return new PlayerNameReader(new TextReader(), index + DeltaOffsets.STATE_DELTA_PLAYER_NAME).read(file);
    }

    private Money readMoney(PokemonFile file, int index) {
        return new MoneyReader(index + DeltaOffsets.STATE_DELTA_MONEY).read(file);
    }

    private Team readTeam(PokemonFile file, int index) {
        return new TeamReader(textReader, pokedex, new TeamOffsets(index + DeltaOffsets.STATE_DELTA_TEAM)).read(file);
    }

}
