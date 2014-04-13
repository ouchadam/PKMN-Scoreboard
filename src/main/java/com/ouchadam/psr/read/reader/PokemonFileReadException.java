package com.ouchadam.psr.read.reader;

public class PokemonFileReadException extends RuntimeException {
    public PokemonFileReadException(String message) {
        super(message);
    }

    public PokemonFileReadException(Throwable cause) {
        super(cause);
    }
}
