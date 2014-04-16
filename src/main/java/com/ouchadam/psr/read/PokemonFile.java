package com.ouchadam.psr.read;

import com.ouchadam.psr.read.domain.PokemonFileType;

public interface PokemonFile {
    int getInt(long index);
    int getTwoInt(int offset1, int offset2);
    long length();
    PokemonFileType getType();
    String getFileName();
}
