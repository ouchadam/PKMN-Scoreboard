package com.ouchadam.psr.read.reader;

import com.ouchadam.psr.read.PokemonSave;
import com.ouchadam.psr.read.SaveReader;
import com.ouchadam.psr.read.SpeciesToPokedex;
import com.ouchadam.psr.read.text.TextReader;
import com.ouchadam.psr.read.domain.Pokemon;
import com.ouchadam.psr.read.domain.Team;
import com.ouchadam.psr.read.text.TextTerminatedException;

import java.util.ArrayList;
import java.util.List;

class TeamPokemonReader implements SaveReader<Team> {

    private static final int OFFSET_POKEMON_TEAM = 0x2F2C;
    private static final int OFFSET_POKEMON_TEAM_STAT = 0x08;
    private static final int POKEMON_STAT_START_OFFSET = OFFSET_POKEMON_TEAM + OFFSET_POKEMON_TEAM_STAT;
    private static final int PLAYER_NAME_SIZE = 404;
    private static final int OFFSET_STAT_TYPE_1 = 0x05;
    private static final int OFFSET_STAT_LEVEL = 0x21;
    private static final int OFFSET_STAT_MAX_HP_1 = 0x22;
    private static final int OFFSET_STAT_MAX_HP_2 = 0x23;
    private static final int OFFSET_STAT_CURRENT_HP_1 = 0x01;
    private static final int OFFSET_STAT_CURRENT_HP_2 = 0x02;
    private static final int OFFSET_STAT_SPECIES = 0x00;

    private static final int POKEMON_STAT_SIZE = 44;
    private static final int MAX_TEAM_SIZE = 6;
    private static final int OFFSET_TEAM_NAMES = OFFSET_POKEMON_TEAM + (MAX_TEAM_SIZE * (POKEMON_STAT_SIZE + 12) + 2);

    private final TextReader textReader;
    private final SpeciesToPokedex speciesToPokedex;

    TeamPokemonReader(TextReader textReader, SpeciesToPokedex speciesToPokedex) {
        this.textReader = textReader;
        this.speciesToPokedex = speciesToPokedex;
    }

    @Override
    public Team read(PokemonSave save) {
        int teamCount = getTeamCount(save);
        return new Team(teamCount, getPokemon(save, teamCount));
    }

    private int getTeamCount(PokemonSave save) {
        return save.getInt(OFFSET_POKEMON_TEAM);
    }

    private List<Pokemon> getPokemon(PokemonSave save, int teamCount) {
        List<Pokemon> pokemons = new ArrayList<Pokemon>(teamCount);
        List<String> pokemonNames = getPokemonNames(save, teamCount);
        for (int index = 0; index < teamCount; index++) {
            int baseStatOffset = (POKEMON_STAT_SIZE * index) + POKEMON_STAT_START_OFFSET;
            int species = save.getInt(baseStatOffset + OFFSET_STAT_SPECIES);
            int type = save.getInt(baseStatOffset + OFFSET_STAT_TYPE_1);
            int level = save.getInt(baseStatOffset + OFFSET_STAT_LEVEL);
            int maxHp = save.getTwoInt(baseStatOffset + OFFSET_STAT_MAX_HP_1, baseStatOffset + OFFSET_STAT_MAX_HP_2);
            int currentHp = save.getTwoInt(baseStatOffset + OFFSET_STAT_CURRENT_HP_1, baseStatOffset + OFFSET_STAT_CURRENT_HP_2);
            String name = pokemonNames.get(index);
            int pokedexId = speciesToPokedex.getIdFor(name);
            pokemons.add(new Pokemon(name, maxHp, currentHp, level, species, pokedexId, type));
        }
        return pokemons;
    }

    private List<String> getPokemonNames(PokemonSave save, int teamCount) {
        List<String> names = new ArrayList<>(teamCount);
        String pokemonName = "";

        for (int index = OFFSET_TEAM_NAMES; index < OFFSET_POKEMON_TEAM + PLAYER_NAME_SIZE; index++) {
            int saveByte = save.getInt(index);
            try {
                char c = textReader.toLetter(saveByte);
                pokemonName += c;
            } catch (TextTerminatedException e) {
                if (!pokemonName.isEmpty()) {
                    names.add(pokemonName);
                    pokemonName = "";
                    if (names.size() == teamCount) {
                        return names;
                    }
                }
            }
        }
        throw new RuntimeException("Bad pokemon name count : found  :"  + names.size() + " but expected :" + teamCount);
    }

}
