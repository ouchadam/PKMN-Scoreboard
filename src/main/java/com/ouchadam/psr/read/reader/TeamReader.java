package com.ouchadam.psr.read.reader;

import com.ouchadam.psr.read.Pokedex;
import com.ouchadam.psr.read.PokemonFile;
import com.ouchadam.psr.read.domain.Pokemon;
import com.ouchadam.psr.read.domain.Team;
import com.ouchadam.psr.read.text.TextReader;
import com.ouchadam.psr.read.text.TextTerminatedException;

import java.util.ArrayList;
import java.util.List;

public class TeamReader implements PokemonFileReader<Team> {

    private final TextReader textReader;
    private final Pokedex pokedex;
    private final TeamOffsets teamOffsets;

    public TeamReader(TextReader textReader, Pokedex pokedex, TeamOffsets teamOffsets) {
        this.textReader = textReader;
        this.pokedex = pokedex;
        this.teamOffsets = teamOffsets;
    }

    @Override
    public Team read(PokemonFile save) {
        int teamCount = getTeamCount(save);
        return new Team(teamCount, getPokemon(save, teamCount));
    }

    private int getTeamCount(PokemonFile file) {
        return file.getInt(teamOffsets.getOffsetPokemonTeam());
    }

    private List<Pokemon> getPokemon(PokemonFile file, int teamCount) {
        List<Pokemon> pokemons = new ArrayList<Pokemon>(teamCount);
        List<String> pokemonNames = getPokemonNames(file, teamCount);
        for (int index = 0; index < teamCount; index++) {
            int baseStatOffset = (TeamOffsets.POKEMON_STAT_SIZE * index) + teamOffsets.getPokemonStatStartOffset();
            int species = file.getInt(baseStatOffset + TeamOffsets.OFFSET_STAT_SPECIES);
            int type = file.getInt(baseStatOffset + TeamOffsets.OFFSET_STAT_TYPE_1);
            int level = file.getInt(baseStatOffset + TeamOffsets.OFFSET_STAT_LEVEL);
            int maxHp = file.getTwoInt(baseStatOffset + TeamOffsets.OFFSET_STAT_MAX_HP_1, baseStatOffset + TeamOffsets.OFFSET_STAT_MAX_HP_2);
            int currentHp = file.getTwoInt(baseStatOffset + TeamOffsets.OFFSET_STAT_CURRENT_HP_1, baseStatOffset + TeamOffsets.OFFSET_STAT_CURRENT_HP_2);
            String name = pokemonNames.get(index);
            int pokedexId = pokedex.getIdFor(name);
            pokemons.add(new Pokemon(name, maxHp, currentHp, level, species, pokedexId, type));
        }
        return pokemons;
    }

    private List<String> getPokemonNames(PokemonFile file, int teamCount) {
        List<String> names = new ArrayList<>(teamCount);
        String pokemonName = "";

        for (int index = teamOffsets.getOffsetTeamNames(); index < teamOffsets.getOffsetPokemonTeam() + TeamOffsets.POKEMON_NAME_SIZE; index++) {
            int saveByte = file.getInt(index);
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
        throw new PokemonFileReadException("Bad pokemon name count : found  :" + names.size() + " but expected :" + teamCount);
    }

}
