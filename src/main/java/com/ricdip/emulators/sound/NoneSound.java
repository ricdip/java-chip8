package com.ricdip.emulators.sound;

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
