package com.ricdip.emulators.engine;

import com.ricdip.emulators.exception.Chip8Exception;
import com.ricdip.emulators.model.Instruction;

final class OpcodeDecoder {
    private OpcodeDecoder() {
    }

    public static Instruction decode(int opcode) {
        int op = (opcode & 0xF000) >> 12;
        int x = (opcode & 0x0F00) >> 8;
        int y = (opcode & 0x00F0) >> 4;
        int n = (opcode & 0x000F);
        int nnn = (opcode & 0x0FFF);
        int kk = (opcode & 0x00FF);

        Instruction decodedInstruction = Instruction
                .builder()
                .OP(op)
                .X(x)
                .Y(y)
                .N(n)
                .NNN(nnn)
                .KK(kk)
                .build();

        switch (op) {
            case 0x0:
                break;
            case 0x1:
                break;
            case 0x2:
                break;
            case 0x3:
                break;
            case 0x4:
                break;
            case 0x5:
                break;
            case 0x6:
                break;
            case 0x7:
                break;
            case 0x8:
                break;
            case 0x9:
                break;
            case 0xA:
                break;
            case 0xB:
                break;
            case 0xC:
                break;
            case 0xD:
                break;
            case 0xE:
                break;
            case 0xF:
                break;
        }

        if (decodedInstruction.getOpcodeType() == null) {
            throw new Chip8Exception(String.format("Unknown opcode: 0x%04X", nnn));
        }

        return decodedInstruction;
    }
}
