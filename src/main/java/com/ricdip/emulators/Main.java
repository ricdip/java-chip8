package com.ricdip.emulators;

import com.ricdip.emulators.engine.Chip8;
import com.ricdip.emulators.graphics.Graphics;
import com.ricdip.emulators.graphics.TerminalGraphics;
import com.ricdip.emulators.model.Rom;

public class Main {
    public static void main(String[] args) {
        // init graphics
        Graphics graphics = new TerminalGraphics();
        // TODO: init input
        // init engine
        Chip8 chip8 = new Chip8();
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
                graphics.draw(chip8.getDisplay());
            }

            // TODO: handle key press
        }
    }
}