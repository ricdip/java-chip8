package com.ricdip.emulators.javachip8.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@AllArgsConstructor
@Builder
public class Instruction {
    /**
     * OP section from opcode: 0xF000
     */
    private int OP;

    /**
     * X section from opcode: 0x0F00
     */
    private int X;

    /**
     * Y section from opcode: 0x00F0
     */
    private int Y;

    /**
     * N section from opcode: 0x000N
     */
    private int N;

    /**
     * NNN section from opcode: 0x0NNN
     */
    private int NNN;

    /**
     * KK section from opcode: 0x00KK
     */
    private int KK;

    @Setter
    private OpcodeType opcodeType;

    @Override
    public String toString() {
        return (opcodeType != null) ? opcodeType.getType() : "<null opcode type>";
    }
}
