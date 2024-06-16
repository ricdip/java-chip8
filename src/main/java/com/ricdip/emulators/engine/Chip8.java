package com.ricdip.emulators.engine;

import com.ricdip.emulators.exception.Chip8Exception;
import com.ricdip.emulators.model.Instruction;

import java.util.Arrays;

public class Chip8 extends BaseChip8 {

    @Override
    public void emulateCycle() {
        // fetch opcode from memory
        opcode = Chip8OpcodeFetcher.fetch(memory, PC);

        // decode opcode
        Instruction decodedInstruction = Chip8OpcodeDecoder.decode(opcode);

        // execute opcode
        executeInstruction(decodedInstruction);
    }

    private void executeInstruction(Instruction instruction) {
        int X = instruction.getX();
        int Y = instruction.getY();
        int N = instruction.getN();
        int NNN = instruction.getNNN();
        int KK = instruction.getKK();

        switch (instruction.getOpcodeType()) {
            case OP_0NNN:
                PC += 2;
                break;

            case OP_00E0:
                Arrays.fill(display, false);
                drawFlag = true;
                PC += 2;
                break;

            case OP_00EE:
                checkStackUnderflowError();
                SP -= 1;
                PC = stack[SP];
                break;

            case OP_1NNN:
                PC = NNN;
                break;

            case OP_2NNN:
                checkStackOverflowError();
                stack[SP] = PC;
                SP += 1;
                PC = NNN;
                break;

            case OP_3XKK:
                if (V[X] == KK) {
                    PC += 2;
                }
                PC += 2;
                break;

            case OP_4XKK:
                if (V[X] != KK) {
                    PC += 2;
                }
                PC += 2;
                break;

            case OP_5XY0:
                if (V[X] == V[Y]) {
                    PC += 2;
                }
                PC += 2;
                break;

            case OP_6XKK:
                V[X] = KK;
                PC += 2;
                break;

            case OP_7XKK:
                V[X] += KK;
                PC += 2;
                break;

            case OP_8XY0:
                V[X] = V[Y];
                PC += 2;
                break;

            case OP_8XY1:
                V[X] |= V[Y];
                PC += 2;
                break;

            case OP_8XY2:
                V[X] &= V[Y];
                PC += 2;
                break;

            case OP_8XY3:
                V[X] ^= V[Y];
                PC += 2;
                break;

            case OP_8XY4:
                int sum = V[X] + V[Y];
                if (sum > 0xFF) {
                    V[0xF] = 1;
                } else {
                    V[0xF] = 0;
                }
                V[X] = sum & 0xFF;
                PC += 2;
                break;

            case OP_8XY5:
                if (V[X] > V[Y]) {
                    V[0xF] = 1;
                    V[X] -= V[Y];
                } else {
                    V[0xF] = 0;
                    int diff = V[Y] - V[X];
                    V[X] = diff;
                }
                PC += 2;
                break;

            case OP_8XY6:
                V[X] = V[Y];
                V[0xF] = V[X] & 0x0F;
                V[X] >>= 1;
                PC += 2;
                break;

            case OP_8XY7:
                if (V[Y] > V[X]) {
                    V[0xF] = 1;
                    int diff = V[Y] - V[X];
                    V[X] = diff;
                } else {
                    V[0xF] = 0;
                    V[X] -= V[Y];
                }
                PC += 2;
                break;

            case OP_8XYE:
                V[X] = V[Y];
                V[0xF] = V[X] & 0xF0;
                V[X] <<= 1;
                PC += 2;
                break;

            case OP_9XY0:
                if (V[X] != V[Y]) {
                    PC += 2;
                }
                PC += 2;
                break;

            case OP_ANNN:
                I = NNN;
                PC += 2;
                break;

            case OP_BNNN:
                // TODO: continue execute opcode implementation
                break;

            default:
                throw new Chip8Exception(String.format("Fail to execute opcode %s: not implemented", instruction.getOpcodeType().getType()));
        }
    }

    private void checkStackUnderflowError() {
        if (SP == 0) {
            throw new Chip8Exception("Stack underflow error: attempted to get value from empty stack");
        }
    }

    private void checkStackOverflowError() {
        if (SP == stack.length) {
            throw new Chip8Exception("Stack overflow error: attempted to add value to full stack");
        }
    }
}
