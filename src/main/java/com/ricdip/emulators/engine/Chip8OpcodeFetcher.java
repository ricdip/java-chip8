package com.ricdip.emulators.engine;

public final class Chip8OpcodeFetcher {

    private Chip8OpcodeFetcher() {
        // NOOP
    }

    /**
     * fetch CHIP-8 instruction from memory at the position indicated by PC (Program Counter)
     *
     * @param memory the CHIP-8 memory data structure
     * @param PC     the CHIP-8 Program Counter
     * @return CHIP-8 fetched opcode
     */
    public static int fetch(int[] memory, int PC) {
        // fetch opcode first byte
        int opcodeFirstByte = memory[PC];
        // fetch opcode second byte
        int opcodeSecondByte = memory[PC + 1];
        // merge both bytes to get full opcode:
        // 1. 0x00XX << 8 = 0xXX00
        // 2. 0xXX00 | 0x00YY = 0xXXYY
        return opcodeFirstByte << 8 | opcodeSecondByte;
    }
}
