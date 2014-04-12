package com.ouchadam.psr.read.reader;

public class TeamOffsets {

    static final int OFFSET_POKEMON_TEAM_STAT = 0x08;
    static final int POKEMON_NAME_SIZE = 404;
    static final int OFFSET_STAT_TYPE_1 = 0x05;
    static final int OFFSET_STAT_LEVEL = 0x21;
    static final int OFFSET_STAT_MAX_HP_1 = 0x22;
    static final int OFFSET_STAT_MAX_HP_2 = 0x23;
    static final int OFFSET_STAT_CURRENT_HP_1 = 0x01;
    static final int OFFSET_STAT_CURRENT_HP_2 = 0x02;
    static final int OFFSET_STAT_SPECIES = 0x00;
    static final int POKEMON_STAT_SIZE = 44;

    private static final int MAX_TEAM_SIZE = 6;

    private final int offsetTeamNames;
    private final int pokemonStatStartOffset;
    private final int offsetPokemonTeam;

    public TeamOffsets(int baseOffset) {
        this.offsetPokemonTeam = baseOffset;
        this.offsetTeamNames = offsetPokemonTeam + (MAX_TEAM_SIZE * (POKEMON_STAT_SIZE + 12) + 2);
        this.pokemonStatStartOffset = offsetPokemonTeam + OFFSET_POKEMON_TEAM_STAT;
    }

    public static TeamOffsets getInstance() {
        String os = System.getProperty("os.name");
        if (os.contains("win")) {
            return windows();
        } else {
            return linux();
        }
    }

    static TeamOffsets linux() {
        return new TeamOffsets(0xCE1D);
    }

    static TeamOffsets windows() {
        return new TeamOffsets(0x54db);
    }

    public int getOffsetTeamNames() {
        return offsetTeamNames;
    }

    public int getPokemonStatStartOffset() {
        return pokemonStatStartOffset;
    }

    public int getOffsetPokemonTeam() {
        return offsetPokemonTeam;
    }
}
