package com.ouchadam.psr.read;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;

public class PokemonSave {

    private final RandomAccessFile file;

    public PokemonSave(RandomAccessFile file) {
        this.file = file;
    }

    public static PokemonSave from(String filename) throws FileNotFoundException {
        return new PokemonSave(new RandomAccessFile(new File(filename), "r"));
    }

    public int getInt(long offset) {
        try {
            file.seek(offset);
            return file.readUnsignedByte();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public int getTwoInt(int offset, int offset2) {
        String hex1 = getHex(offset);
        String hex2 = getHex(offset2);

        if (hex2.length() == 1) {
            hex2 = "0" + hex2;
        }

        if ((!hex1.isEmpty() || hex1.equals("0")) && (hex2.equals("0") || hex2.equals("00"))) {
            hex2 = "";
        }
//        System.out.println("Hex 1 : "  + hex1 + "  Hex 2 : " + hex2 + "  : " + (hex1 + hex2));

        return Integer.parseInt(hex1 + hex2, 16);
    }

    public String getHex(int offset) {
        return Integer.toHexString(getInt(offset)).trim();
    }

    public long length() {
        try {
            return file.length();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
