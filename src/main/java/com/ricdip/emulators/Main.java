package com.ricdip.emulators;

import com.ricdip.emulators.engine.Chip8;
import com.ricdip.emulators.model.Rom;
import com.ricdip.emulators.screen.Screen;
import com.ricdip.emulators.screen.SwingScreen;
import com.ricdip.emulators.utils.Sleep;

public class Main {
    public static void main(String[] args) {
        // init engine
        Chip8 chip8 = new Chip8();
        // init graphics
        Screen screen = new SwingScreen(chip8.getDisplay());
        // init keyboard input
        screen.configureKeyListener(chip8.getKeyboard());
        // TODO: init sound effect
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

            // limit frame rate at 60 Hz
            Sleep.fromFrequency(Chip8.FRAME_RATE);
        }
    }
}