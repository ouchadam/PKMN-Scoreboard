package com.ouchadam.psr.read.reader;

import com.ouchadam.psr.read.PokemonFile;
import com.ouchadam.psr.read.domain.Money;

import java.util.ArrayList;
import java.util.List;

public class MoneyReader implements PokemonFileReader<Money> {

    private static final int PLAYER_MONEY_SIZE = 3;
    private final int baseOffset;

    public MoneyReader(int baseOffset) {
        this.baseOffset = baseOffset;
    }

    @Override
    public Money read(PokemonFile file) {
        String money = "";
        for (int index = baseOffset; index < baseOffset + PLAYER_MONEY_SIZE; index++) {
            String binary = Integer.toBinaryString(file.getInt(index));
            money += mergeIntegers(bcmDecoder(binary));
        }
        return new Money(money);
    }

    private int mergeIntegers(List<Integer> integers) {
        String merged = "";
        for (Integer integer : integers) {
            merged += integer;
        }
        return Integer.valueOf(merged);
    }

    private static List<Integer> bcmDecoder(String binaryString) {
        List<Integer> list = new ArrayList<Integer>();
        StringBuilder buffer = new StringBuilder();
        int count = 0;

        for (char c : binaryString.toCharArray()) {
            buffer.append(c);
            count++;

            if (count >= 4) {
                list.add(Integer.parseInt(buffer.toString(), 2));
                count = 0;
                buffer.delete(0, 4);
            }
        }

        return list;
    }

}
