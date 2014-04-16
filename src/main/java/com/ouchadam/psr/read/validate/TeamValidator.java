package com.ouchadam.psr.read.validate;

import com.ouchadam.psr.read.domain.Pokemon;
import com.ouchadam.psr.read.domain.Team;

public class TeamValidator implements Validator<Team> {

    private final Validator<Pokemon> pokemonValidator;

    public TeamValidator(Validator<Pokemon> pokemonValidator) {
        this.pokemonValidator = pokemonValidator;
    }

    @Override
    public boolean isValid(Team what) {
        for (Pokemon pokemon : what.pokemons) {
            if (!pokemonValidator.isValid(pokemon)) {
                return false;
            }
        }
        return true;
    }
}
