package com.ricdip.emulators;

import com.ricdip.emulators.engine.Chip8;
import com.ricdip.emulators.graphics.Graphics;
import com.ricdip.emulators.graphics.TerminalGraphics;

public class Main {
    public static void main(String[] args) {
        Chip8 chip8 = new Chip8();
        Graphics graphics = new TerminalGraphics();

        graphics.draw(chip8.getDisplay());
    }
}