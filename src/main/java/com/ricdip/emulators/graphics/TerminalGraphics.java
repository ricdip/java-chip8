package com.ricdip.emulators.graphics;

import com.ricdip.emulators.engine.Display;

public class TerminalGraphics implements Graphics {

    @Override
    public void draw(Display display) {
        for (int i = 0; i < display.getDisplay().length; i++) {
            if (i % Display.DISPLAY_WIDTH == 0) {
                System.out.print("\n");
            }
            System.out.print((display.getDisplay()[i]) ? "1" : "0");
        }
        System.out.print("\n");
    }
}
