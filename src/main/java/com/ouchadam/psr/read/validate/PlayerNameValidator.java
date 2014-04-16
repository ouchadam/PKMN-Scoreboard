package com.ouchadam.psr.read.validate;

import com.ouchadam.psr.read.domain.PlayerName;

public class PlayerNameValidator implements Validator<PlayerName> {

    private static final int MAX_NAME_LENGTH = 10;
    private static final int MIN_NAME_LENGTH = 1;

    @Override
    public boolean isValid(PlayerName what) {
        return what.length() >= MIN_NAME_LENGTH && what.length() <= MAX_NAME_LENGTH;
    }
}
