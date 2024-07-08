package com.ricdip.emulators.screen;

import com.ricdip.emulators.engine.Display;
import com.ricdip.emulators.engine.Keyboard;

public interface Screen {
    void redraw(Display display);

    void attachKeyboard(Keyboard keyboard);

    boolean isClosed();
}
