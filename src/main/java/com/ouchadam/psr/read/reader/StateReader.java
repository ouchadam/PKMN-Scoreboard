package com.ouchadam.psr.read.reader;

import com.ouchadam.psr.read.file.PokemonState;

public interface StateReader<T> {
    T read(PokemonState state);
}
