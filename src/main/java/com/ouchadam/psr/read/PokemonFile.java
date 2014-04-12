package com.ouchadam.psr.read;

public interface PokemonFile {
    int getInt(int index);
    int getTwoInt(int offset1, int offset2);
    long length();
}
