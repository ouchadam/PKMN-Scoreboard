package com.ouchadam.psr.read.domain;

import java.util.List;

public class Team  {

    public final int count;
    public final List<Pokemon> pokemons;

    public Team(int count, List<Pokemon> pokemons) {
        this.count = count;
        this.pokemons = pokemons;
    }

    @Override
    public String toString() {
        return "\n" + pokemons.toString() + "\n";
    }

}
