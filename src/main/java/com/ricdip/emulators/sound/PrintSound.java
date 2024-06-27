package com.ricdip.emulators.sound;

public class PrintSound implements Sound {
    @Override
    public void performSound() {
        System.out.println("BEEP");
    }
}
