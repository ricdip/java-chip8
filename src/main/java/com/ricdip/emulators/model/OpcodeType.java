package com.ricdip.emulators.model;

import lombok.Getter;

/**
 * CHIP-8 instructions (opcode types).
 * Variables:
 * <ul>
 *     <li>NNN or addr: 12-bit value, the lowest 12 bits of the instruction.</li>
 *     <li>N or nibble: 4-bit value, the lowest 4 bits of the instruction.</li>
 *     <li>X: 4-bit value, the lower 4 bits of the high byte of the instruction.</li>
 *     <li>Y: 4-bit value, the upper 4 bits of the low byte of the instruction.</li>
 *     <li>KK or byte: 8-bit value, the lowest 8 bits of the instruction.</li>
 * </ul>
 */
public enum OpcodeType {
    /**
     * SYS addr: Jump to a machine code routine at nnn.
     */
    OP_0NNN("0NNN"),

    /**
     * CLS: Clear the display.
     */
    OP_00E0("00E0"),

    /**
     * RET: Return from a subroutine.
     */
    OP_00EE("00EE"),

    /**
     * JP addr: Jump to location nnn.
     */
    OP_1NNN("1NNN"),

    /**
     * CALL addr: Call subroutine at nnn.
     */
    OP_2NNN("2NNN"),

    /**
     * SE Vx, byte: Skip next instruction if Vx = kk.
     */
    OP_3XKK("3XKK"),

    /**
     * SNE Vx, byte: Skip next instruction if Vx != kk.
     */
    OP_4XKK("4XKK"),

    /**
     * SE Vx, Vy: Skip next instruction if Vx = Vy.
     */
    OP_5XY0("5XY0"),

    /**
     * LD Vx, byte: Set Vx = kk.
     */
    OP_6XKK("6XKK"),

    /**
     * ADD Vx, byte: Set Vx = Vx + kk.
     */
    OP_7XKK("7XKK"),

    /**
     * LD Vx, Vy: Set Vx = Vy.
     */
    OP_8XY0("8XY0"),

    /**
     * OR Vx, Vy: Set Vx = Vx OR Vy.
     */
    OP_8XY1("8XY1"),

    /**
     * AND Vx, Vy: Set Vx = Vx AND Vy.
     */
    OP_8XY2("8XY2"),

    /**
     * XOR Vx, Vy: Set Vx = Vx XOR Vy.
     */
    OP_8XY3("8XY3"),

    /**
     * ADD Vx, Vy: Set Vx = Vx + Vy, set VF = carry.
     */
    OP_8XY4("8XY4"),

    /**
     * SUB Vx, Vy: Set Vx = Vx - Vy, set VF = NOT borrow.
     */
    OP_8XY5("8XY5"),

    /**
     * SHR Vx {, Vy}: Set Vx = Vy, then SHR 1.
     */
    OP_8XY6("8XY6"),

    /**
     * SUBN Vx, Vy: Set Vx = Vy - Vx, set VF = NOT borrow.
     */
    OP_8XY7("8XY7"),

    /**
     * SHL Vx {, Vy}: Set Vx = Vy, then SHL 1.
     */
    OP_8XYE("8XYE"),

    /**
     * SNE Vx, Vy: Skip next instruction if Vx != Vy.
     */
    OP_9XY0("9XY0"),

    /**
     * LD I, addr: Set I = nnn.
     */
    OP_ANNN("ANNN"),

    /**
     * JP V0, addr: Jump to location nnn + V0.
     */
    OP_BNNN("BNNN"),

    /**
     * RND Vx, byte: Set Vx = random byte AND kk.
     */
    OP_CXKK("CXKK"),

    /**
     * DRW Vx, Vy, nibble: Display n-byte sprite starting at memory location I at (Vx, Vy), set VF = collision.
     */
    OP_DXYN("DXYN"),

    /**
     * SKP Vx: Skip next instruction if key with the value of Vx is pressed.
     */
    OP_EX9E("EX9E"),

    /**
     * SKNP Vx: Skip next instruction if key with the value of Vx is not pressed.
     */
    OP_EXA1("EXA1"),

    /**
     * LD Vx, DT: Set Vx = delay timer value.
     */
    OP_FX07("FX07"),

    /**
     * LD Vx, K: Wait for a key press, store the value of the key in Vx.
     */
    OP_FX0A("FX0A"),

    /**
     * LD DT, Vx: Set delay timer = Vx.
     */
    OP_FX15("FX15"),

    /**
     * LD ST, Vx: Set sound timer = Vx.
     */
    OP_FX18("FX18"),

    /**
     * ADD I, Vx: Set I = I + Vx.
     */
    OP_FX1E("FX1E"),

    /**
     * LD F, Vx: Set I = location of sprite for digit Vx.
     */
    OP_FX29("FX29"),

    /**
     * LD B, Vx: Store BCD representation of Vx in memory locations I, I+1, and I+2.
     */
    OP_FX33("FX33"),

    /**
     * LD [I], Vx: Store registers V0 through Vx in memory starting at location I.
     */
    OP_FX55("FX55"),

    /**
     * LD Vx, [I]: Read registers V0 through Vx from memory starting at location I.
     */
    OP_FX65("FX65");

    @Getter
    private final String type;

    OpcodeType(String type) {
        this.type = type;
    }
}
