package com.ricdip.emulators.javachip8.engine;

import com.ricdip.emulators.javachip8.exception.Chip8Exception;
import com.ricdip.emulators.javachip8.model.FontSet;
import com.ricdip.emulators.javachip8.model.Rom;
import com.ricdip.emulators.javachip8.sound.NoneSound;
import com.ricdip.emulators.javachip8.sound.Sound;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Getter
@Slf4j
public abstract class BaseChip8 {
    /**
     * Chip-8 frame rate in Hz
     */
    public static final int FRAME_RATE = 60;
    public static final int MEMORY_LENGTH = 4096;
    public static final int V_LENGTH = 16;
    public static final int I_INIT_VALUE = 0x000;
    public static final int DELAY_TIMER_INIT_VALUE = 0x00;
    public static final int SOUND_TIMER_INIT_VALUE = 0x00;
    public static final int PC_INIT_VALUE = 0x0200;
    public static final int STACK_LENGTH = 16;
    public static final int SP_INIT_VALUE = 0x00;
    public static final int OPCODE_INIT_VALUE = 0x0000;
    /**
     * 4,096 bytes of RAM: 0x000 (0) - 0xFFF (4095).
     * The first 512 bytes (0x000 - 0x1FF) are where the original interpreter was located, and should not be used by
     * programs. Most Chip-8 programs start at location 0x200 (512).
     */
    protected int[] memory;
    /**
     * 16 general purpose 8-bit registers: V0 - VF.
     * VF register should not be used by any program, as it is used as a flag.
     */
    protected int[] V;
    /**
     * 16-bit register: index register, used to store memory addresses, so only the lowest (rightmost) 12 bits are
     * usually used (2^12 = 4096).
     */
    protected int I;
    /**
     * special purpose 8-bit register. The delay timer is active whenever the delay timer register (DT) is non-zero.
     * This timer does nothing more than subtract 1 from the value of DT at a rate of 60Hz.
     * When DT reaches 0, it deactivates.
     */
    protected int delayTimer;
    /**
     * special purpose 8-bit register. The sound timer is active whenever the sound timer register (ST) is non-zero.
     * This timer also decrements at a rate of 60Hz, however, as long as ST's value is greater than zero,
     * the Chip-8 buzzer will sound.
     * When ST reaches zero, the sound timer deactivates.
     */
    protected int soundTimer;
    protected Sound sound;
    /**
     * 16-bit pseudo-register: program counter, used to store the currently executing address.
     * Starts at 0x200.
     */
    protected int PC;
    /**
     * array of 16 16-bit values: used to store the address that the interpreter shoud return to when finished with a
     * subroutine. Chip-8 allows for up to 16 levels of nested subroutines.
     */
    protected int[] stack;
    /**
     * 8-bit pseudo-register: stack pointer, used to point to the topmost level of the stack.
     */
    protected int SP;
    /**
     * object that contains a 16-key hexadecimal keypad with the following layout:
     * <pre>
     * |1|2|3|C|
     * |4|5|6|D|
     * |7|8|9|E|
     * |A|0|B|F|
     * </pre>
     */
    protected Keyboard keyboard;
    /**
     * the original implementation of the Chip-8 language used a 64x32-pixel monochrome display with this format:
     * <pre>
     * |(0,0)   (0,63)|
     * |(31,0) (31,63)|
     * </pre>
     * Chip-8 draws graphics on screen through the use of sprites. A sprite is a group of bytes which are a binary
     * representation of the desired picture.
     * Chip-8 sprites may be up to 15 bytes, for a possible sprite size of 8x15.
     * Programs may also refer to a group of sprites representing the hexadecimal digits 0 through F. These sprites are
     * 5 bytes long, or 8x5 pixels.
     * The data should be stored in the interpreter area of Chip-8 memory (0x000 to 0x1FF). This data is called
     * "fontset".
     */
    protected Display display;
    /**
     * 16-bit opcode. CHIP-8 has 35 opcodes which are all two bytes long.
     */
    protected int opcode;
    /**
     * if this flag is set, update the screen.
     */
    @Setter
    protected boolean drawFlag;

    public BaseChip8() {
        keyboard = new Keyboard();
        display = new Display();
        sound = new NoneSound();
        init();
    }

    /**
     * reset CHIP-8 data structures.
     */
    public void reset() {
        init();
        log.info("reset performed");
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
        log.info("ROM file '{}' loaded", rom.getRomName());
    }

    private void init() {
        log.info("initialization");
        memory = new int[MEMORY_LENGTH]; // clear memory
        V = new int[V_LENGTH]; // clear registers V0-VF
        I = I_INIT_VALUE; // clear index register
        delayTimer = DELAY_TIMER_INIT_VALUE; // reset delay timer
        soundTimer = SOUND_TIMER_INIT_VALUE; // reset sound timer
        PC = PC_INIT_VALUE; // program counter starts at 0x200
        SP = SP_INIT_VALUE; // reset stack pointer
        stack = new int[STACK_LENGTH]; // clear stack
        keyboard.reset(); // reset keyboard
        display.clear(); // clear display
        opcode = OPCODE_INIT_VALUE; // reset opcode
        drawFlag = false; // reset draw flag
        loadFontSet();
    }

    private void loadFontSet() {
        // load fontset in memory
        for (int i = 0; i < FontSet.SPRITES.length; i++) {
            memory[i] = FontSet.SPRITES[i];
        }
        log.info("fontset loaded");
    }

    /**
     * emulate 1 CHIP-8 cycle: fetch opcode, decode opcode, execute opcode.
     * At the end, update sound timer and delay timer.
     *
     * @throws Chip8Exception if an error occurred during cycle emulation
     */
    public abstract void emulateCycle();

    /**
     * set CHIP-8 sound implementation
     *
     * @param sound the sound implementation to set
     */
    public void setSound(@NonNull Sound sound) {
        this.sound = sound;
        log.info("sound implementation '{}' set", sound.getClass().getName());
    }
}
