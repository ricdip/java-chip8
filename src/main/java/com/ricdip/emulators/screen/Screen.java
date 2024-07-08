package com.ricdip.emulators.screen;

import com.ricdip.emulators.engine.Display;
import com.ricdip.emulators.engine.Keyboard;

public interface Screen {
    /**
     * redraw screen
     *
     * @param display CHIP-8 display to redraw
     */
    void redraw(Display display);

    /**
     * start listening CHIP-8 keyboard key presses and releases
     *
     * @param keyboard CHIP-8 keyboard
     */
    void attachKeyboard(Keyboard keyboard);

    /**
     * check if screen is closed
     *
     * @return true if screen is closed, false otherwise
     */
    boolean isClosed();
}
