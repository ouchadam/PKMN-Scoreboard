package com.ouchadam.psr.read.reader;

public class DeltaOffsets {

    static final int STATE_DELTA_PLAYTIME = 0x0;
    public static final int STATE_DELTA_PLAYER_NAME = 0x5275;
    public static final int STATE_DELTA_MONEY = 0x5464;
    public static final int STATE_DELTA_TEAM = 0xb04d;

    private DeltaOffsets() {
        throw new IllegalAccessError("This class is not instantiable");
    }

}
