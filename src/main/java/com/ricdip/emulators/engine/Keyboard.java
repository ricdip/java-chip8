package com.ricdip.emulators.engine;

import com.ricdip.emulators.exception.KeyboardException;

import java.util.Arrays;
import java.util.Optional;

public class Keyboard {
    /**
     * 16-key hexadecimal keypad with the following layout:
     * |1|2|3|C|
     * |4|5|6|D|
     * |7|8|9|E|
     * |A|0|B|F|
     */
    private final boolean[] keys;
    public static final int KEYBOARD_LENGTH = 16;

    public Keyboard() {
        keys = new boolean[KEYBOARD_LENGTH];
    }

    public void reset() {
        Arrays.fill(keys, false);
    }

    public boolean isPressed(int key) {
        checkIllegalKeyError(key);
        return keys[key];
    }

    public Optional<Integer> getPressedKey() {
        for (int i = 0; i < KEYBOARD_LENGTH; i++) {
            if (keys[i]) {
                return Optional.of(i);
            }
        }
        return Optional.empty();
    }

    public void setKeyPressed(int key) {
        checkIllegalKeyError(key);
        keys[key] = true;
    }

    public void setKeyReleased(int key) {
        checkIllegalKeyError(key);
        keys[key] = false;
    }

    private void checkIllegalKeyError(int value) {
        if (value >= Keyboard.KEYBOARD_LENGTH) {
            throw new KeyboardException(String.format("Illegal key error: 0x%02X", value));
        }
    }
}
