package com.ricdip.emulators.engine;

import lombok.Getter;

import java.util.Arrays;

@Getter
public class Display {
    /**
     * the original implementation of the Chip-8 language used a 64x32-pixel monochrome display with this format:
     * |(0,0)   (63,0)|
     * |(0,31) (63,31)|
     * Chip-8 draws graphics on screen through the use of sprites. A sprite is a group of bytes which are a binary
     * representation of the desired picture.
     * Chip-8 sprites may be up to 15 bytes, for a possible sprite size of 8x15.
     * Programs may also refer to a group of sprites representing the hexadecimal digits 0 through F. These sprites are
     * 5 bytes long, or 8x5 pixels.
     * The data should be stored in the interpreter area of Chip-8 memory (0x000 to 0x1FF). This data is called
     * "fontset".
     */
    private final boolean[] display;
    public static final int DISPLAY_WIDTH = 64;
    public static final int DISPLAY_HEIGHT = 32;

    public Display() {
        display = new boolean[DISPLAY_WIDTH * DISPLAY_HEIGHT];
    }

    public void clear() {
        Arrays.fill(display, false);
    }
}
