package com.ouchadam.psr.read.reader;

import com.ouchadam.psr.helper.FileHelper;
import com.ouchadam.psr.read.PokemonFile;

public class ReaderTestHelper {

    protected static final PokemonFile LINUX_STATE = FileHelper.loadState("linux_save1.sgm");
    protected static final PokemonFile WIN_STATE = FileHelper.loadState("save1.sgm");
    protected static final PokemonFile SAV = FileHelper.loadState("save1.sav");

    protected static int windowsOffset(int offset) {
        return offset - Offsets.WINDOWS_STATE_OFFSET;
    }

}
