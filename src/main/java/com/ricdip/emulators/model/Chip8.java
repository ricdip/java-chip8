package com.ricdip.emulators.model;

public class Chip8 {
    /**
     * 4,096 bytes of RAM: 0x000 (0) - 0xFFF (4095).
     * The first 512 bytes (0x000 - 0x1FF) are where the original interpreter was located, and should not be used by
     * programs. Most Chip-8 programs start at location 0x200 (512).
     */
    private int[] memory;

    /**
     * 16 general purpose 8-bit registers: V0 - VF.
     * VF register should not be used by any program, as it is used as a flag.
     */
    private int[] V;

    /**
     * 16-bit register: index register, used to store memory addresses, so only the lowest (rightmost) 12 bits are
     * usually used (2^12 = 4096).
     */
    private int I;

    /**
     * special purpose 8-bit register.
     */
    private int delayTimer;

    /**
     * special purpose 8-bit register.
     */
    private int soundTimer;

    /**
     * 16-bit pseudo-register: program counter, used to store the currently executing address.
     * Starts at 0x200.
     */
    private int PC;

    /**
     * 8-bit pseudo-register: stack pointer, used to point to the topmost level of the stack.
     */
    private int SP;

    /**
     * array of 16 16-bit values: used to store the address that the interpreter shoud return to when finished with a
     * subroutine. Chip-8 allows for up to 16 levels of nested subroutines.
     */
    private int[] stack;

    /**
     * 16-key hexadecimal keypad with the following layout:
     * |1|2|3|C|
     * |4|5|6|D|
     * |7|8|9|E|
     * |A|0|B|F|
     */
    private boolean[] keyboard;

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
    private int[][] display;

    public Chip8() {
        init();
    }

    public void reset() {
        init();
    }

    private void init() {
        memory = new int[4096]; // clear memory
        V = new int[16]; // clear registers V0-VF
        I = 0x000000; // clear index register
        delayTimer = 0x0000; // reset delay timer
        soundTimer = 0x0000; // reset sound timer
        PC = 0x00000200; // program counter starts at 0x200
        SP = 0x0000; // reset stack pointer
        stack = new int[16]; // clear stack
        keyboard = new boolean[16]; // reset keyboard
        display = new int[32][64]; // clear display
        loadFontSet();
    }

    private void loadFontSet() {
        // load fontset in memory
        for (int i = 0; i < FontSet.SPRITES.length; i++) {
            memory[i] = FontSet.SPRITES[i];
        }
    }
}
