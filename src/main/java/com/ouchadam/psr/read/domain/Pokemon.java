package com.ouchadam.psr.read.domain;

public class Pokemon {

    private final String name;
    private final int maxHp;
    private final int currentHp;
    private final int level;
    private final int species;
    private final int pokedexId;
    private final int type;

    public Pokemon(String name, int maxHp, int currentHp, int level, int species, int pokedexId, int type) {
        this.name = name;
        this.maxHp = maxHp;
        this.currentHp = currentHp;
        this.level = level;
        this.species = species;
        this.pokedexId = pokedexId;
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public int getMaxHp() {
        return maxHp;
    }

    public int getCurrentHp() {
        return currentHp;
    }

    public int getLevel() {
        return level;
    }

    public int getSpecies() {
        return species;
    }

    public int getPokedexId() {
        return pokedexId;
    }

    public int getType() {
        return type;
    }

    @Override
    public String toString() {
        return "\nPokemon{" +
                "name='" + name + '\'' +
                ", maxHp=" + maxHp +
                ", currentHp=" + currentHp +
                ", level=" + level +
                ", species=" + species +
                ", pokedexId=" + pokedexId +
                ", type=" + type +
                '}';
    }
}
