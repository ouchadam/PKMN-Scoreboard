package com.ouchadam.psr.read.reader;

import com.ouchadam.psr.helper.FileHelper;
import com.ouchadam.psr.read.OffsetFinder;
import com.ouchadam.psr.read.PokemonFile;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import static org.fest.assertions.api.Assertions.assertThat;

public class OffsetFinderTest {

    protected static final PokemonFile LINUX_STATE = FileHelper.loadState("red_state_linux.sgm");
    protected static final PokemonFile WIN_STATE = FileHelper.loadState("red_state_windows.sgm");

    private OffsetFinder offsetFinder;

    @Before
    public void setUp() throws Exception {
        offsetFinder = OffsetFinder.newInstance();
    }

    @Test
    @Ignore
    public void offset_deltas() {

        List<Integer> offsets = new ArrayList<>();
        offsets.add(Offsets.STATE_MONEY);
        offsets.add(Offsets.STATE_PLAYER_NAME);
        offsets.add(Offsets.STATE_PLAYTIME);
        offsets.add(Offsets.STATE_TEAM);

        Collections.sort(offsets);

        int delta = 0;
        for (int index = 0; index < offsets.size(); index++) {
            int offset = offsets.get(index);
            if (index > 0) {
                delta = offset - offsets.get(0);
            }
            System.out.println("Offset : " + Integer.toHexString(offset) + " : " + offset + " delta : " + Integer.toHexString(delta));
        }

    }

    @Test
    public void find_windows_state_offset() {
        assertOffset(WIN_STATE, 0x25b);
    }

    @Test
    public void find_linux_state_offset() {
        assertOffset(LINUX_STATE, 0x7b9d);
    }

    private void assertOffset(PokemonFile file, int expectedOffset) {
        int foundOffset = -1;
        try {
            foundOffset = offsetFinder.findBaseOffset(file);
        } catch (RuntimeException e) {
            // do nothing
        }
        assertThat(foundOffset).isEqualTo(expectedOffset);
    }
}
