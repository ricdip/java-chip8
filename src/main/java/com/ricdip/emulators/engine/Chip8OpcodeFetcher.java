package com.ricdip.emulators.engine;

public final class Chip8OpcodeFetcher {

    private Chip8OpcodeFetcher() {
    }

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
