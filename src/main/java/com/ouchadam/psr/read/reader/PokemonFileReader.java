package com.ouchadam.psr.read.reader;

import com.ouchadam.psr.read.PokemonFile;

public interface PokemonFileReader<T> {
    T read(PokemonFile save);
}
