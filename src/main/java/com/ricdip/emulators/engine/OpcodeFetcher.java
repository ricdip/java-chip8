package com.ricdip.emulators.engine;

final class OpcodeFetcher {

    private OpcodeFetcher() {
    }

    public static int fetch(int[] memory, int PC) {
        // fetch opcode first byte
        int opcodeFirstByte = memory[PC];
        // fetch opcode second byte
        int opcodeSecondByte = memory[PC + 1];
        // merge both bytes to get full opcode:
        // 1. 0x0000XXXX << 8 = 0xXXXX0000
        // 2. 0xXXXX0000 | 0x0000YYYY = 0xXXXXYYYY
        return opcodeFirstByte << 8 | opcodeSecondByte;
    }
}
