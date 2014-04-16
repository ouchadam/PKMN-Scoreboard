package com.ouchadam.psr.read.validate;

import com.ouchadam.psr.read.domain.Money;

public class MoneyValidator implements Validator<Money> {

    private static final int MIN_MONEY = 0;
    private static final int MAX_MONEY = 999999;

    @Override
    public boolean isValid(Money what) {
        return what.value() >= MIN_MONEY && what.value() <= MAX_MONEY;
    }
}
