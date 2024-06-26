package com.ricdip.emulators;

import com.ricdip.emulators.engine.Chip8;
import com.ricdip.emulators.graphics.Screen;
import com.ricdip.emulators.graphics.SwingScreen;
import com.ricdip.emulators.model.Rom;

public class Main {
    public static void main(String[] args) {
        // init engine
        Chip8 chip8 = new Chip8();
        // init graphics
        Screen screen = new SwingScreen(chip8.getDisplay());
        // TODO: init key events
        // load ROM file
        Rom romFile = new Rom("roms/IBM_logo.ch8");
        chip8.loadRom(romFile);

        // emulation loop
        boolean running = true;
        while (running) {
            // emulate 1 cycle
            chip8.emulateCycle();

            // if draw flag is set, redraw screen
            if (chip8.isDrawFlag()) {
                chip8.setDrawFlag(false);
                screen.redraw(chip8.getDisplay());
            }

            // TODO: handle key press
        }
    }
}