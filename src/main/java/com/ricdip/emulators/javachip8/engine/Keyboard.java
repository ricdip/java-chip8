package com.ricdip.emulators.javachip8.engine;

import com.ricdip.emulators.javachip8.exception.KeyboardException;

import java.util.Arrays;
import java.util.Optional;

public class Keyboard {
    public static final int KEYBOARD_LENGTH = 16;
    /**
     * 16-key keypad implementation using an array of booleans.
     * The CHIP-8 keyboard has the following layout:
     * <pre>
     * |1|2|3|C|
     * |4|5|6|D|
     * |7|8|9|E|
     * |A|0|B|F|
     * </pre>
     * Where each position denotes a specific key (example: position 15 -> 0xF is key F)
     */
    private final boolean[] keys;

    public Keyboard() {
        keys = new boolean[KEYBOARD_LENGTH];
    }

    /**
     * reset the keyboard by setting every element to false
     */
    public void reset() {
        Arrays.fill(keys, false);
    }

    /**
     * check if specific key is pressed
     *
     * @param key key to check
     * @return true if key is pressed, false otherwise
     */
    public boolean isPressed(int key) {
        checkIllegalKeyError(key);
        return keys[key];
    }

    /**
     * get pressed key from keyboard
     *
     * @return Optional describing the pressed key, <code>Optional.empty()</code> otherwise
     */
    public Optional<Integer> getPressedKey() {
        for (int i = 0; i < KEYBOARD_LENGTH; i++) {
            if (keys[i]) {
                return Optional.of(i);
            }
        }
        return Optional.empty();
    }

    /**
     * set key pressed on the keyboard
     *
     * @param key pressed key to set
     * @throws KeyboardException if supplied key is illegal
     */
    public void setKeyPressed(int key) {
        checkIllegalKeyError(key);
        keys[key] = true;
    }

    /**
     * set key released on the keyboard
     *
     * @param key released key to set
     * @throws KeyboardException if supplied key is illegal
     */
    public void setKeyReleased(int key) {
        checkIllegalKeyError(key);
        keys[key] = false;
    }

    private void checkIllegalKeyError(int value) {
        if (value < 0 || value >= Keyboard.KEYBOARD_LENGTH) {
            throw new KeyboardException(String.format("Illegal key error: 0x%02X", value));
        }
    }
}
