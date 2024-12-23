package com.ricdip.emulators.javachip8.sound;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class PrintSound implements Sound {
    @Override
    public void performSound() {
        log.info("BEEP");
    }

    @Override
    public void close() {
        // NOOP
    }
}
