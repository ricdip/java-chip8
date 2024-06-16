package com.ricdip.emulators.graphics;

import com.ricdip.emulators.engine.BaseChip8;

public class TerminalGraphics implements Graphics {

    @Override
    public void draw(boolean[] display) {
        for (int i = 0; i < (BaseChip8.DISPLAY_WIDTH * BaseChip8.DISPLAY_HEIGHT); i++) {
            if (i % BaseChip8.DISPLAY_WIDTH == 0) {
                System.out.print("\n");
            }
            System.out.print((display[i]) ? "1" : "0");
        }
        System.out.print("\n");
    }
}
