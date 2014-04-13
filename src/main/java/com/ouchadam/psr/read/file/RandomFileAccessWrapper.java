package com.ouchadam.psr.read.file;

import com.ouchadam.psr.read.PokemonFile;

import java.io.IOException;
import java.io.RandomAccessFile;

public abstract class RandomFileAccessWrapper implements PokemonFile {

    private final RandomAccessFile file;

    protected RandomFileAccessWrapper(RandomAccessFile file) {
        this.file = file;
    }

    @Override
    public int getInt(long offset) {
        try {
            file.seek(offset);
            return file.readUnsignedByte();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
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

    @Override
    public long length() {
        try {
            return file.length();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
