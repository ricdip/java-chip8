package com.ricdip.emulators.graphics;

import com.ricdip.emulators.engine.Display;

public class TerminalScreen implements Screen {

    @Override
    public void redraw(Display display) {
        for (int i = 0; i < display.getDisplay().length; i++) {
            if (i % Display.DISPLAY_WIDTH == 0) {
                System.out.print("\n");
            }
            System.out.print((display.getDisplay()[i]) ? "1" : "0");
        }
        System.out.print("\n");
    }
}
