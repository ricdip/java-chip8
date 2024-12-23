package com.ricdip.emulators.javachip8.engine;

import java.util.Arrays;

public class Display {
    public static final int DISPLAY_WIDTH = 64;
    public static final int DISPLAY_HEIGHT = 32;
    /**
     * CHIP-8 display implementation using booleans. Each element of the array is a pixel on the display:
     * <ul>
     *     <li>if the pixel is true: the pixel is on</li>
     *     <li>if the pixel is false: the pixel is off</li>
     * </ul>
     */
    private final boolean[] display;

    public Display() {
        display = new boolean[DISPLAY_WIDTH * DISPLAY_HEIGHT];
    }

    /**
     * clear the display setting every pixel to false
     */
    public void clear() {
        Arrays.fill(display, false);
    }

    /**
     * check if pixel in (row, col) coordinates is on
     *
     * @param row the row of the display
     * @param col the col of the display
     * @return true if pixel is set, false otherwise
     */
    public boolean isPixelSet(int row, int col) {
        int coords = (row * Display.DISPLAY_WIDTH) + col;
        return display[coords];
    }

    /**
     * toggle pixel in (row, col) coordinates
     *
     * @param row the row of the display
     * @param col the col of the display
     */
    public void togglePixel(int row, int col) {
        int coords = (row * Display.DISPLAY_WIDTH) + col;
        display[coords] = !display[coords];
    }
}
