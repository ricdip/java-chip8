package com.ricdip.emulators.javachip8.sound;

public class NoneSound implements Sound {
    @Override
    public void performSound() {
        // NOOP
    }

    @Override
    public void close() {
        // NOOP
    }
}
