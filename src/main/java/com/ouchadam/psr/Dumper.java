package com.ouchadam.psr;

import com.ouchadam.psr.read.PokemonSave;
import com.ouchadam.psr.read.text.TextReader;
import com.ouchadam.psr.read.text.TextTerminatedException;

import java.io.IOException;
import java.io.RandomAccessFile;

public class Dumper {

    public static void dumpAll(long start, long end, RandomAccessFile file) throws IOException {
        for (long index = start; index < end; index++) {
            file.seek(index);
            int aByte = file.readUnsignedByte();
            System.out.print(aByte + " ");
        }
    }

    public static void printAllText(long start, long end, PokemonSave file) throws IOException {
        String text = "";
        TextReader textReader = new TextReader();
        for (long index = start; index < end; index++) {
            int saveByte = file.getInt(index);
            try {
                char c = textReader.toLetter(saveByte);
                text += c;
            } catch (TextTerminatedException e) {
                if (!text.isEmpty()) {
                    System.out.println(text + " ::: " + Long.toHexString(index));
                    text = "";
                }
            } catch (RuntimeException e) {
                if (!text.isEmpty()) {
                    System.out.println(text + " ::: " + Long.toHexString(index));
                    text = "";
                }
            }
        }

    }

}
