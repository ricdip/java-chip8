package com.ricdip.emulators.screen;

import com.ricdip.emulators.engine.Display;
import com.ricdip.emulators.engine.Keyboard;

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

    @Override
    public void configureKeyListener(Keyboard keyboard) {
        throw new UnsupportedOperationException("Keyboard not configured for terminal screen");
    }
}
