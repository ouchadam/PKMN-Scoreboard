package com.ouchadam.psr.read;

public interface SaveReader<T> {
    T read(PokemonSave save);
}
