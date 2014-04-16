package com.ouchadam.psr.read.validate;

import com.ouchadam.psr.read.Pokedex;
import com.ouchadam.psr.read.domain.Pokemon;

public class PokemonValidator implements Validator<Pokemon> {

    private static final int MAX_LEVEL = 100;
    private static final int MIN_LEVEL = 1;

    private static final int MIN_MAX_HP = 1;
    private static final int MAX_MAX_HP = 999;

    private static final int MAX_CURRENT_HP = 999;
    private static final int MIN_CURRENT_HP = 0;

    private final Pokedex pokedex;

    public PokemonValidator(Pokedex pokedex) {
        this.pokedex = pokedex;
    }

    @Override
    public boolean isValid(Pokemon pokemon) {
        return pokedex.has(pokemon.getName()) &&
                (pokemon.getLevel() >= MIN_LEVEL && pokemon.getLevel() <= MAX_LEVEL) &&
                (pokemon.getMaxHp() > MIN_MAX_HP && pokemon.getMaxHp() <= MAX_MAX_HP) &&
                (pokemon.getCurrentHp() >= MIN_CURRENT_HP && pokemon.getCurrentHp() <= MAX_CURRENT_HP);
    }
}
