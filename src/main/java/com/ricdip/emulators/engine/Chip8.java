package com.ricdip.emulators.engine;

import com.ricdip.emulators.exception.Chip8Exception;
import com.ricdip.emulators.model.FontSet;
import com.ricdip.emulators.model.Instruction;
import com.ricdip.emulators.model.Rom;
import lombok.Getter;

@Getter
public class Chip8 {
    /**
     * 4,096 bytes of RAM: 0x000 (0) - 0xFFF (4095).
     * The first 512 bytes (0x000 - 0x1FF) are where the original interpreter was located, and should not be used by
     * programs. Most Chip-8 programs start at location 0x200 (512).
     */
    private int[] memory;
    public static final int MEMORY_LENGTH = 4096;

    /**
     * 16 general purpose 8-bit registers: V0 - VF.
     * VF register should not be used by any program, as it is used as a flag.
     */
    private int[] V;
    public static final int V_LENGTH = 16;

    /**
     * 16-bit register: index register, used to store memory addresses, so only the lowest (rightmost) 12 bits are
     * usually used (2^12 = 4096).
     */
    private int I;
    public static final int I_INIT_VALUE = 0x000;

    /**
     * special purpose 8-bit register. The delay timer is active whenever the delay timer register (DT) is non-zero.
     * This timer does nothing more than subtract 1 from the value of DT at a rate of 60Hz.
     * When DT reaches 0, it deactivates.
     */
    private int delayTimer;
    public static final int DELAY_TIMER_INIT_VALUE = 0x00;

    /**
     * special purpose 8-bit register. The sound timer is active whenever the sound timer register (ST) is non-zero.
     * This timer also decrements at a rate of 60Hz, however, as long as ST's value is greater than zero,
     * the Chip-8 buzzer will sound.
     * When ST reaches zero, the sound timer deactivates.
     */
    private int soundTimer;
    public static final int SOUND_TIMER_INIT_VALUE = 0x00;

    /**
     * 16-bit pseudo-register: program counter, used to store the currently executing address.
     * Starts at 0x200.
     */
    private int PC;
    public static final int PC_INIT_VALUE = 0x0200;

    /**
     * array of 16 16-bit values: used to store the address that the interpreter shoud return to when finished with a
     * subroutine. Chip-8 allows for up to 16 levels of nested subroutines.
     */
    private int[] stack;
    public static final int STACK_LENGTH = 16;

    /**
     * 8-bit pseudo-register: stack pointer, used to point to the topmost level of the stack.
     */
    private int SP;
    public static final int SP_INIT_VALUE = 0x00;

    /**
     * 16-key hexadecimal keypad with the following layout:
     * |1|2|3|C|
     * |4|5|6|D|
     * |7|8|9|E|
     * |A|0|B|F|
     */
    private boolean[] keyboard;
    public static final int KEYBOARD_LENGTH = 16;

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
    private boolean[] display;
    public static final int DISPLAY_WIDTH = 64;
    public static final int DISPLAY_HEIGHT = 32;

    /**
     * 16-bit opcode. CHIP-8 has 35 opcodes which are all two bytes long.
     */
    private int opcode;
    public static final int OPCODE_INIT_VALUE = 0x0000;

    /**
     * if this flag is set, update the screen.
     */
    private boolean drawFlag;

    public Chip8() {
        init();
    }

    /**
     * reset CHIP-8 data structures.
     */
    public void reset() {
        init();
    }

    /**
     * load ROM into CHIP-8 memory.
     *
     * @param rom the ROM file to load
     * @throws Chip8Exception if ROM file length exceeds Memory length - 512
     */
    public void loadRom(Rom rom) {
        if (rom.getRomSize() > MEMORY_LENGTH - PC_INIT_VALUE) {
            throw new Chip8Exception("ROM file size exceeds available memory");
        }

        byte[] romContent = rom.getRomContent();
        for (int i = 0; i < rom.getRomSize(); i++) {
            memory[i + PC_INIT_VALUE] = Byte.toUnsignedInt(romContent[i]);
        }
    }

    private void init() {
        memory = new int[MEMORY_LENGTH]; // clear memory
        V = new int[V_LENGTH]; // clear registers V0-VF
        I = I_INIT_VALUE; // clear index register
        delayTimer = DELAY_TIMER_INIT_VALUE; // reset delay timer
        soundTimer = SOUND_TIMER_INIT_VALUE; // reset sound timer
        PC = PC_INIT_VALUE; // program counter starts at 0x200
        SP = SP_INIT_VALUE; // reset stack pointer
        stack = new int[STACK_LENGTH]; // clear stack
        keyboard = new boolean[KEYBOARD_LENGTH]; // reset keyboard
        display = new boolean[DISPLAY_WIDTH * DISPLAY_HEIGHT]; // clear display
        opcode = OPCODE_INIT_VALUE; // reset opcode
        drawFlag = false; // reset draw flag
        loadFontSet();
    }

    private void loadFontSet() {
        // load fontset in memory
        for (int i = 0; i < FontSet.SPRITES.length; i++) {
            memory[i] = FontSet.SPRITES[i];
        }
    }

    /**
     * emulate 1 CHIP-8 cycle: fetch opcode, decode opcode, execute opcode.
     * At the end, update sound timer and delay timer.
     */
    public void emulateCycle() {
        // fetch opcode from memory
        opcode = OpcodeFetcher.fetch(memory, PC);

        Instruction decodedInstruction = OpcodeDecoder.decode(opcode);

        // TODO: execute opcode
        throw new UnsupportedOperationException("execute opcode not implemented");
    }
}
