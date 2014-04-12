package com.ouchadam.psr.read.reader;

import com.ouchadam.psr.read.PokemonFile;
import com.ouchadam.psr.read.SpeciesToPokedex;
import com.ouchadam.psr.read.domain.Pokemon;
import com.ouchadam.psr.read.domain.Team;
import com.ouchadam.psr.read.text.TextReader;

import org.junit.Test;

import static org.fest.assertions.api.Assertions.assertThat;

public class TeamReaderTest extends ReaderTestHelper {

    @Test
    public void read_sav_count() {
        assertCount(Offsets.SAV_TEAM, SAV);
    }

    @Test
    public void read_sav_pokemon() {
        assertTeam(Offsets.SAV_TEAM, SAV);
    }

    @Test
    public void read_linux_state_count() {
        assertCount(Offsets.STATE_TEAM, LINUX_STATE);
    }

    @Test
    public void read_linux_state_pokemon() {
        assertTeam(Offsets.STATE_TEAM, LINUX_STATE);
    }

    @Test
    public void read_windows_state_count() {
        assertCount(windowsOffset(Offsets.STATE_TEAM), WIN_STATE);
    }

    @Test
    public void read_windows_state_pokemon() {
        assertTeam(windowsOffset(Offsets.STATE_TEAM), WIN_STATE);
    }

    private static void assertCount(int countOffset, PokemonFile file) {
        PokemonFileReader<Team> teamReader = create(new TeamOffsets(countOffset));
        Team team = teamReader.read(file);
        assertThat(team.count).isEqualTo(6);
    }

    private static void assertTeam(int teamOffset, PokemonFile file) {
        PokemonFileReader<Team> teamReader = create(new TeamOffsets(teamOffset));
        Team team = teamReader.read(file);
        assertPokemon(team.pokemons.get(0), "DRAGONITE", 100, 66);
        assertPokemon(team.pokemons.get(5), "MEWTWO", 100, 131);
    }

    private static PokemonFileReader<Team> create(TeamOffsets teamOffsets) {
        return new TeamReader(new TextReader(), new SpeciesToPokedex(), teamOffsets);
    }

    private static void assertPokemon(Pokemon pokemon, String name, int level, int species) {
        assertThat(pokemon.getName()).isEqualTo(name);
        assertThat(pokemon.getLevel()).isEqualTo(level);
        assertThat(pokemon.getSpecies()).isEqualTo(species);
    }

}
