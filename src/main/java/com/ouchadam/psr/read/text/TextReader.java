package com.ouchadam.psr.read.text;

public class TextReader {

    private static final int COLUMN_START = 0x00;
    private static final int COLUMN_END = 0x10;
    private static final int ROW_START = 0x08;
    private static final int ROW_END = 0x10;

    private static final int WHITE_SPACE = 0x7F;
    private static final int TERMINATION = 0x50;

    private static final char[][] TEXT = {
            {'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P'},
            {'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', '(', ')', ':', ';', '[', ']'},
            {'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p'},
            {'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z', ' ', ' ', ' ', ' ', ' ', ' '},
            {' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' '},
            {' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' '},
            {' ', 'p', 'm', '-', ' ', ' ', '?', '!', '.', ' ', ' ', ' ', ' ', ' ', ' ', '♂'},
            {' ', '*', ' ', '/', ',', '♀', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9'}
    };

    public char toLetter(int savByte) throws TextTerminatedException {
        if (isWhiteSpace(savByte)) {
            return ' ';
        }
        if (isTermination(savByte)) {
            throw new TextTerminatedException();
        }
        for (int column = COLUMN_START; column < COLUMN_END; column++) {
            for (int row = ROW_START; row < ROW_END; row++) {
                if (savByte == toInt(Integer.toHexString(row) + Integer.toHexString(column))) {
                    return TEXT[row - ROW_START][column];
                }
            }
        }
        throw new TextUnhandledException();
    }

    private boolean isWhiteSpace(int savByte) {
        return savByte == WHITE_SPACE;
    }

    private boolean isTermination(int savByte) {
        return savByte == TERMINATION;
    }

    private static int toInt(String value) {
        return Integer.parseInt(value, 16);
    }

}
