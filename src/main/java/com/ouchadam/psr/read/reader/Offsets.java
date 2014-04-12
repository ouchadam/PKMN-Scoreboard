package com.ouchadam.psr.read.reader;

public class Offsets {

    static final int WINDOWS_STATE_OFFSET = 0x7942;

    static final int SAV_MONEY = 0x25F3;
    static final int STATE_MONEY = 0xd001;

    static final int SAV_TEAM = 0x2F2C;
    static final int STATE_TEAM = 0x12bea;

    static final int SAV_PLAYTIME = 0x2CEE;
    static final int STATE_PLAYTIME = 0x7b9d;

    static final int SAV_PLAYER_NAME = 0x2598;
    static final int STATE_PLAYER_NAME = 0xce12;

    private Offsets() {
        throw new IllegalAccessError("This class is not instantiable");
    }

}
