package com.ricdip.emulators.graphics;

import com.ricdip.emulators.engine.Chip8;

public class TerminalGraphics implements Graphics {

    @Override
    public void draw(boolean[] display) {
        for (int i = 0; i < (Chip8.DISPLAY_WIDTH * Chip8.DISPLAY_HEIGHT); i++) {
            if (i % Chip8.DISPLAY_WIDTH == 0) {
                System.out.print("\n");
            }
            System.out.print((display[i]) ? "1" : "0");
        }
        System.out.print("\n");
    }
}
