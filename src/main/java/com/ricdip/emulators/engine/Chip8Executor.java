package com.ricdip.emulators.engine;

import com.ricdip.emulators.exception.Chip8Exception;
import com.ricdip.emulators.exception.RomException;
import com.ricdip.emulators.model.Rom;
import com.ricdip.emulators.screen.Screen;
import com.ricdip.emulators.screen.SwingScreen;
import com.ricdip.emulators.sound.PrintSound;
import com.ricdip.emulators.utils.Sleep;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public final class Chip8Executor {
    private Chip8Executor() {
    }

    /**
     * start CHIP-8 emulation
     *
     * @param romPath    the path to the ROM file
     * @param randomSeed nullable random seed to set
     * @throws RomException   if an error occurs during ROM reading
     * @throws Chip8Exception if an error occurs during emulation
     */
    public static void startEmulation(String romPath, Long randomSeed) {
        // init engine
        Chip8 chip8 = new Chip8();
        // init graphics
        Screen screen = new SwingScreen(chip8.getDisplay());
        // init keyboard input
        screen.configureKeyListener(chip8.getKeyboard());
        // TODO: init real sound effect
        chip8.setSound(new PrintSound());
        // init random seed
        if (randomSeed != null) {
            chip8.setRandomSeed(randomSeed);
        }
        // load ROM file
        Rom romFile = new Rom(romPath);
        chip8.loadRom(romFile);

        // emulation loop
        log.info("emulation loop start");
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
            Sleep.fromFrameRate(Chip8.FRAME_RATE);
        }
        log.info("emulation loop end");
    }
}
