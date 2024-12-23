package com.ricdip.emulators.javachip8.engine;

import com.ricdip.emulators.javachip8.exception.Chip8Exception;
import com.ricdip.emulators.javachip8.model.Instruction;
import com.ricdip.emulators.javachip8.model.OpcodeType;

public final class Chip8OpcodeDecoder {
    private Chip8OpcodeDecoder() {
        // NOOP
    }

    /**
     * decode CHIP-8 Instruction from opcode
     *
     * @param opcode the opcode to decode
     * @return decoded instruction
     */
    public static Instruction decode(int opcode) {
        int op = (opcode & 0xF000) >>> 12;
        int x = (opcode & 0x0F00) >>> 8;
        int y = (opcode & 0x00F0) >>> 4;
        int n = (opcode & 0x000F);
        int kk = (opcode & 0x00FF);
        int nnn = (opcode & 0x0FFF);

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
                decodedInstruction.setOpcodeType(decodeOp0x0(decodedInstruction));
                break;
            case 0x1:
                decodedInstruction.setOpcodeType(decodeOp0x1());
                break;
            case 0x2:
                decodedInstruction.setOpcodeType(decodeOp0x2());
                break;
            case 0x3:
                decodedInstruction.setOpcodeType(decodeOp0x3());
                break;
            case 0x4:
                decodedInstruction.setOpcodeType(decodeOp0x4());
                break;
            case 0x5:
                decodedInstruction.setOpcodeType(decodeOp0x5(decodedInstruction));
                break;
            case 0x6:
                decodedInstruction.setOpcodeType(decodeOp0x6());
                break;
            case 0x7:
                decodedInstruction.setOpcodeType(decodeOp0x7());
                break;
            case 0x8:
                decodedInstruction.setOpcodeType(decodeOp0x8(decodedInstruction));
                break;
            case 0x9:
                decodedInstruction.setOpcodeType(decodeOp0x9(decodedInstruction));
                break;
            case 0xA:
                decodedInstruction.setOpcodeType(decodeOp0xA());
                break;
            case 0xB:
                decodedInstruction.setOpcodeType(decodeOp0xB());
                break;
            case 0xC:
                decodedInstruction.setOpcodeType(decodeOp0xC());
                break;
            case 0xD:
                decodedInstruction.setOpcodeType(decodeOp0xD());
                break;
            case 0xE:
                decodedInstruction.setOpcodeType(decodeOp0xE(decodedInstruction));
                break;
            case 0xF:
                decodedInstruction.setOpcodeType(decodeOp0xF(decodedInstruction));
                break;
        }

        if (decodedInstruction.getOpcodeType() == null) {
            throw new Chip8Exception(String.format("Unknown opcode: 0x%04X", opcode));
        }

        return decodedInstruction;
    }

    private static OpcodeType decodeOp0x0(Instruction instruction) {
        if (instruction.getX() == 0x0) {
            if (instruction.getY() == 0xE) {
                switch (instruction.getN()) {
                    case 0x0:
                        return OpcodeType.OP_00E0;
                    case 0xE:
                        return OpcodeType.OP_00EE;
                }
            }
        }

        return OpcodeType.OP_0NNN;
    }

    private static OpcodeType decodeOp0x1() {
        return OpcodeType.OP_1NNN;
    }

    private static OpcodeType decodeOp0x2() {
        return OpcodeType.OP_2NNN;
    }

    private static OpcodeType decodeOp0x3() {
        return OpcodeType.OP_3XKK;
    }

    private static OpcodeType decodeOp0x4() {
        return OpcodeType.OP_4XKK;
    }

    private static OpcodeType decodeOp0x5(Instruction instruction) {
        if (instruction.getN() == 0x0) {
            return OpcodeType.OP_5XY0;
        } else {
            return null;
        }
    }

    private static OpcodeType decodeOp0x6() {
        return OpcodeType.OP_6XKK;
    }

    private static OpcodeType decodeOp0x7() {
        return OpcodeType.OP_7XKK;
    }

    private static OpcodeType decodeOp0x8(Instruction instruction) {
        return switch (instruction.getN()) {
            case 0x0 -> OpcodeType.OP_8XY0;
            case 0x1 -> OpcodeType.OP_8XY1;
            case 0x2 -> OpcodeType.OP_8XY2;
            case 0x3 -> OpcodeType.OP_8XY3;
            case 0x4 -> OpcodeType.OP_8XY4;
            case 0x5 -> OpcodeType.OP_8XY5;
            case 0x6 -> OpcodeType.OP_8XY6;
            case 0x7 -> OpcodeType.OP_8XY7;
            case 0xE -> OpcodeType.OP_8XYE;
            default -> null;
        };
    }

    private static OpcodeType decodeOp0x9(Instruction instruction) {
        if (instruction.getN() == 0x0) {
            return OpcodeType.OP_9XY0;
        } else {
            return null;
        }
    }

    private static OpcodeType decodeOp0xA() {
        return OpcodeType.OP_ANNN;
    }

    private static OpcodeType decodeOp0xB() {
        return OpcodeType.OP_BNNN;
    }

    private static OpcodeType decodeOp0xC() {
        return OpcodeType.OP_CXKK;
    }

    private static OpcodeType decodeOp0xD() {
        return OpcodeType.OP_DXYN;
    }

    private static OpcodeType decodeOp0xE(Instruction instruction) {
        return switch (instruction.getKK()) {
            case 0x9E -> OpcodeType.OP_EX9E;
            case 0xA1 -> OpcodeType.OP_EXA1;
            default -> null;
        };
    }

    private static OpcodeType decodeOp0xF(Instruction instruction) {
        return switch (instruction.getKK()) {
            case 0x07 -> OpcodeType.OP_FX07;
            case 0x0A -> OpcodeType.OP_FX0A;
            case 0x15 -> OpcodeType.OP_FX15;
            case 0x18 -> OpcodeType.OP_FX18;
            case 0x1E -> OpcodeType.OP_FX1E;
            case 0x29 -> OpcodeType.OP_FX29;
            case 0x33 -> OpcodeType.OP_FX33;
            case 0x55 -> OpcodeType.OP_FX55;
            case 0x65 -> OpcodeType.OP_FX65;
            default -> null;
        };
    }
}
