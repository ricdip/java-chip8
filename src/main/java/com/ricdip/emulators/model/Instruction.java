package com.ricdip.emulators.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class Instruction {
    private int opcode;
    private OpcodeType opcodeType;
}
