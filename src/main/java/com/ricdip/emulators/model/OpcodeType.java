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
     * SYS addr: Jump to a machine code routine at NNN.
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
     * JP addr: Jump to location NNN.
     */
    OP_1NNN("1NNN"),

    /**
     * CALL addr: Call subroutine at NNN.
     */
    OP_2NNN("2NNN"),

    /**
     * SE VX, byte: Skip next instruction if VX = KK.
     */
    OP_3XKK("3XKK"),

    /**
     * SNE VX, byte: Skip next instruction if VX != KK.
     */
    OP_4XKK("4XKK"),

    /**
     * SE VX, VY: Skip next instruction if VX = VY.
     */
    OP_5XY0("5XY0"),

    /**
     * LD VX, byte: Set VX = KK.
     */
    OP_6XKK("6XKK"),

    /**
     * ADD VX, byte: Set VX = VX + KK.
     */
    OP_7XKK("7XKK"),

    /**
     * LD VX, VY: Set VX = VY.
     */
    OP_8XY0("8XY0"),

    /**
     * OR Vx, Vy: Set VX = VX OR VY.
     */
    OP_8XY1("8XY1"),

    /**
     * AND VX, VY: Set VX = VX AND VY.
     */
    OP_8XY2("8XY2"),

    /**
     * XOR VX, VY: Set VX = VX XOR VY.
     */
    OP_8XY3("8XY3"),

    /**
     * ADD VX, VY: Set VX = VX + VY, set VF = carry.
     */
    OP_8XY4("8XY4"),

    /**
     * SUB VX, VY: Set VX = VX - VY, set VF = NOT borrow.
     */
    OP_8XY5("8XY5"),

    /**
     * SHR(1) VX: Set VX = VX SHR 1.
     */
    OP_8XY6("8XY6"),

    /**
     * SUBN VX, VY: Set VX = VY - VX, set VF = NOT borrow.
     */
    OP_8XY7("8XY7"),

    /**
     * SHL(1) VX: Set VX = VX SHL 1.
     */
    OP_8XYE("8XYE"),

    /**
     * SNE VX, VY: Skip next instruction if VX != VY.
     */
    OP_9XY0("9XY0"),

    /**
     * LD I, addr: Set I = nnn.
     */
    OP_ANNN("ANNN"),

    /**
     * JP V0, addr: Jump to location NNN + V0.
     */
    OP_BNNN("BNNN"),

    /**
     * RND VX, byte: Set VX = random byte AND KK.
     */
    OP_CXKK("CXKK"),

    /**
     * DRW VX, VY, nibble: Display n-byte sprite starting at memory location I at (VX, VY), set VF = collision.
     */
    OP_DXYN("DXYN"),

    /**
     * SKP VX: Skip next instruction if key with the value of VX is pressed.
     */
    OP_EX9E("EX9E"),

    /**
     * SKNP VX: Skip next instruction if key with the value of VX is not pressed.
     */
    OP_EXA1("EXA1"),

    /**
     * LD VX, DT: Set VX = delay timer value.
     */
    OP_FX07("FX07"),

    /**
     * LD VX, K: Wait for a key press, store the value of the key in VX.
     */
    OP_FX0A("FX0A"),

    /**
     * LD DT, VX: Set delay timer = VX.
     */
    OP_FX15("FX15"),

    /**
     * LD ST, VX: Set sound timer = VX.
     */
    OP_FX18("FX18"),

    /**
     * ADD I, VX: Set I = I + VX.
     */
    OP_FX1E("FX1E"),

    /**
     * LD F, VX: Set I = location of sprite for digit VX.
     */
    OP_FX29("FX29"),

    /**
     * LD B, VX: Store BCD representation of VX in memory locations I, I+1, and I+2.
     */
    OP_FX33("FX33"),

    /**
     * LD [I], VX: Store registers V0 through VX in memory starting at location I.
     */
    OP_FX55("FX55"),

    /**
     * LD VX, [I]: Read registers V0 through VX from memory starting at location I.
     */
    OP_FX65("FX65");

    @Getter
    private final String type;

    OpcodeType(String type) {
        this.type = type;
    }
}
