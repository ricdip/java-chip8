package com.ricdip.emulators.javachip8.engine;

import com.ricdip.emulators.javachip8.exception.Chip8Exception;
import com.ricdip.emulators.javachip8.model.FontSet;
import com.ricdip.emulators.javachip8.model.Instruction;
import lombok.extern.slf4j.Slf4j;

import java.util.Optional;
import java.util.Random;

@Slf4j
public class Chip8 extends BaseChip8 {

    private final Random random;

    public Chip8() {
        random = new Random();
    }

    /**
     * set random seed
     *
     * @param seed the random seed to set
     */
    public void setRandomSeed(long seed) {
        random.setSeed(seed);
        log.info("random seed '{}' set", seed);
    }

    @Override
    public void emulateCycle() {
        // fetch opcode from memory
        opcode = Chip8OpcodeFetcher.fetch(memory, PC);
        log.trace(String.format("fetched opcode: 0x%04X", opcode));

        // decode opcode
        Instruction decodedInstruction = Chip8OpcodeDecoder.decode(opcode);
        log.trace("decoded instruction: {}", decodedInstruction);

        // execute opcode
        executeInstruction(decodedInstruction);
        log.trace("executed instruction");

        // update timers
        updateTimers();
        log.trace("timers updated");
    }

    private void executeInstruction(Instruction instruction) {
        int X = instruction.getX();
        int Y = instruction.getY();
        int N = instruction.getN();
        int NNN = instruction.getNNN();
        int KK = instruction.getKK();

        switch (instruction.getOpcodeType()) {
            case OP_0NNN: {
                log.debug("{} - ignore legacy instruction", instruction);
                PC += 2;
                break;
            }

            case OP_00E0: {
                log.debug("{} - clear display", instruction);
                display.clear();
                drawFlag = true;
                PC += 2;
                break;
            }

            case OP_00EE: {
                log.debug("{} - return from subroutine", instruction);
                checkStackUnderflowError();
                SP -= 1;
                PC = stack[SP];
                PC += 2;
                break;
            }

            case OP_1NNN: {
                log.debug("{} - jump to address NNN", instruction);
                PC = NNN;
                break;
            }

            case OP_2NNN: {
                log.debug("{} - call subroutine at NNN", instruction);
                checkStackOverflowError();
                stack[SP] = PC;
                SP += 1;
                PC = NNN;
                break;
            }

            case OP_3XKK: {
                log.debug("{} - skip next instruction if VX == KK", instruction);
                if (V[X] == KK) {
                    PC += 2;
                }
                PC += 2;
                break;
            }

            case OP_4XKK: {
                log.debug("{} - skip next instruction if VX != KK", instruction);
                if (V[X] != KK) {
                    PC += 2;
                }
                PC += 2;
                break;
            }

            case OP_5XY0: {
                log.debug("{} - skip next instruction if VX == VY", instruction);
                if (V[X] == V[Y]) {
                    PC += 2;
                }
                PC += 2;
                break;
            }

            case OP_6XKK: {
                log.debug("{} - set VX = KK", instruction);
                V[X] = KK;
                PC += 2;
                break;
            }

            case OP_7XKK: {
                log.debug("{} - add KK to VX", instruction);
                int sum = V[X] + KK;
                int overflow = 0xFF + 1;
                if (sum >= overflow) {
                    V[X] = sum - overflow;
                } else {
                    V[X] = sum;
                }
                PC += 2;
                break;
            }

            case OP_8XY0: {
                log.debug("{} - set VX = VY", instruction);
                V[X] = V[Y];
                PC += 2;
                break;
            }

            case OP_8XY1: {
                log.debug("{} - set VX = VX OR VY", instruction);
                V[X] |= V[Y];
                PC += 2;
                break;
            }

            case OP_8XY2: {
                log.debug("{} - set VX = VX AND VY", instruction);
                V[X] &= V[Y];
                PC += 2;
                break;
            }

            case OP_8XY3: {
                log.debug("{} - set VX = VX XOR VY", instruction);
                V[X] ^= V[Y];
                PC += 2;
                break;
            }

            case OP_8XY4: {
                log.debug("{} - set VX = VX + VY, set VF = carry", instruction);
                int sum = V[X] + V[Y];
                int overflow = 0xFF + 1;
                if (sum >= overflow) {
                    V[0xF] = 1;
                    V[X] = sum - overflow;
                } else {
                    V[0xF] = 0;
                    V[X] = sum;
                }
                PC += 2;
                break;
            }

            case OP_8XY5: {
                log.debug("{} - set VX = VX - VY, set VF = NOT borrow", instruction);
                if (V[X] > V[Y]) {
                    V[0xF] = 1; // not borrow is true (borrow = false)
                    V[X] -= V[Y];
                } else {
                    V[0xF] = 0; // not borrow is false (borrow = true)
                    int diff = V[Y] - V[X];
                    V[X] = diff;
                }
                PC += 2;
                break;
            }

            case OP_8XY6: {
                log.debug("{} - set VX = VX SHR 1, set VF to least significant bit of VX before shift", instruction);
                V[0xF] = V[X] & 0x01;
                V[X] >>>= 1;
                PC += 2;
                break;
            }

            case OP_8XY7: {
                log.debug("{} - set VX = VY - VX, set VF = NOT borrow", instruction);
                if (V[Y] > V[X]) {
                    V[0xF] = 1; // not borrow is true (borrow = false)
                    int diff = V[Y] - V[X];
                    V[X] = diff;
                } else {
                    V[0xF] = 0; // not borrow is false (borrow = true)
                    V[X] -= V[Y];
                }
                PC += 2;
                break;
            }

            case OP_8XYE: {
                log.debug("{} - set VX = VX SHL 1, set VF to most significant bit of VX before shift", instruction);
                V[0xF] = (V[X] >>> 7) & 0x01;
                V[X] = V[X] << 1; // multiply VX by 2
                PC += 2;
                break;
            }

            case OP_9XY0: {
                log.debug("{} - skip next instruction if VX != VY", instruction);
                if (V[X] != V[Y]) {
                    PC += 2;
                }
                PC += 2;
                break;
            }

            case OP_ANNN: {
                log.debug("{} - set I = NNN", instruction);
                I = NNN;
                PC += 2;
                break;
            }

            case OP_BNNN: {
                log.debug("{} - jump to location NNN + V0", instruction);
                PC = NNN + V[0x0];
                break;
            }

            case OP_CXKK: {
                log.debug("{} - set VX = random([0, 256)) AND KK", instruction);
                // generate random number: [0, 256)
                int maxRndInt = 0xFF + 1;
                int rnd = random.nextInt(maxRndInt);
                V[X] = rnd & KK;
                PC += 2;
                break;
            }

            case OP_DXYN: {
                log.debug(
                        "{} - display n-byte sprite starting at memory location I at (VX, VY), set VF = collision",
                        instruction
                );
                V[0xF] = 0;
                for (int height = 0; height < N; height++) {
                    int sprite = memory[I + height];
                    for (int currentBit = 0; currentBit < 8; currentBit++) {
                        int spriteBit = sprite & (0b10000000 >>> currentBit);
                        int row_coord = V[Y] + height;
                        int col_coord = V[X] + currentBit;
                        if (spriteBit != 0) {
                            if (row_coord < Display.DISPLAY_HEIGHT && col_coord < Display.DISPLAY_WIDTH) {
                                if (display.isPixelSet(row_coord, col_coord)) {
                                    V[0xF] = 1;
                                }
                                display.togglePixel(row_coord, col_coord);
                            }
                        }
                    }
                }
                drawFlag = true;
                PC += 2;
                break;
            }

            case OP_EX9E: {
                log.debug("{} - skip next instruction if key with value of VX is pressed", instruction);
                if (keyboard.isPressed(V[X])) {
                    PC += 2;
                }
                PC += 2;
                break;
            }

            case OP_EXA1: {
                log.debug("{} - skip next instruction if key with value of VX is not pressed", instruction);
                if (!keyboard.isPressed(V[X])) {
                    PC += 2;
                }
                PC += 2;
                break;
            }

            case OP_FX07: {
                log.debug("{} - set VX = delay timer value", instruction);
                V[X] = delayTimer;
                PC += 2;
                break;
            }

            case OP_FX0A: {
                log.debug("{} - wait for a key press, store the value of the key in VX", instruction);
                Optional<Integer> pressedKey = keyboard.getPressedKey();
                if (pressedKey.isEmpty()) {
                    break;
                }
                int key = pressedKey.get();
                log.debug(String.format("pressed key: 0x%01X", key));
                V[X] = key;
                PC += 2;
                break;
            }

            case OP_FX15: {
                log.debug("{} - set delay timer = VX", instruction);
                delayTimer = V[X];
                PC += 2;
                break;
            }

            case OP_FX18: {
                log.debug("{} - set sound timer = VX", instruction);
                soundTimer = V[X];
                PC += 2;
                break;
            }

            case OP_FX1E: {
                log.debug("{} - set I = I + VX", instruction);
                int sum = I + V[X];
                int overflow = 0xFFF + 1;
                if (sum >= overflow) {
                    V[0xF] = 1;
                } else {
                    V[0xF] = 0;
                }
                I = sum;
                PC += 2;
                break;
            }

            case OP_FX29: {
                log.debug("{} - set I = location of sprite for digit VX", instruction);
                for (int i = 0; i < FontSet.SPRITES.length; i++) {
                    if (V[X] == memory[i]) {
                        I = i;
                        break;
                    }
                }
                PC += 2;
                break;
            }

            case OP_FX33: {
                log.debug(
                        "{} - store binary coded decimal representation of VX in memory locations I, I+1, I+2",
                        instruction
                );
                memory[I] = V[X] / 100;
                memory[I + 1] = (V[X] / 10) % 10;
                memory[I + 2] = (V[X] % 100);
                PC += 2;
                break;
            }

            case OP_FX55: {
                log.debug("{} - store registers V0-VX in memory starting at location I", instruction);
                for (int i = 0; i <= X; i++) {
                    memory[I + i] = V[i];
                }
                PC += 2;
                break;
            }

            case OP_FX65: {
                log.debug("{} - read registers V0-VX in memory starting at location I", instruction);
                for (int i = 0; i <= X; i++) {
                    V[i] = memory[I + i];
                }
                PC += 2;
                break;
            }

            default:
                throw new Chip8Exception(
                        String.format(
                                "Fail to execute opcode %s: not recognized",
                                instruction.getOpcodeType().getType()
                        )
                );
        }
    }

    private void checkStackUnderflowError() {
        if (SP <= 0) {
            throw new Chip8Exception("Stack underflow error: attempted to get value from empty stack");
        }
    }

    private void checkStackOverflowError() {
        if (SP >= stack.length) {
            throw new Chip8Exception("Stack overflow error: attempted to add value to full stack");
        }
    }

    private void updateTimers() {
        log.trace("delay timer: {}", delayTimer);
        if (delayTimer > 0) {
            delayTimer -= 1;
        }
        log.trace("sound timer: {}", soundTimer);
        if (soundTimer > 0) {
            log.trace("performing sound");
            sound.performSound();
            soundTimer -= 1;
        }
    }
}
