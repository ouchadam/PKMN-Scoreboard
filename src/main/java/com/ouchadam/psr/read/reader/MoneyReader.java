package com.ouchadam.psr.read.reader;

import com.ouchadam.psr.read.PokemonSave;
import com.ouchadam.psr.read.SaveReader;
import com.ouchadam.psr.read.domain.Money;

import java.util.ArrayList;
import java.util.List;

class MoneyReader implements SaveReader<Money> {

    private static final int OFFSET_MONEY = 0x25F3;
    private static final int PLAYER_MONEY_SIZE = 3;

    MoneyReader() {}

    @Override
    public Money read(PokemonSave save) {
        String money = "";
        for (int index = OFFSET_MONEY; index < OFFSET_MONEY + PLAYER_MONEY_SIZE; index++) {
            String binary = Integer.toBinaryString(save.getInt(index));
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
