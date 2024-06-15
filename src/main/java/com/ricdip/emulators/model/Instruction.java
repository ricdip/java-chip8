package com.ricdip.emulators.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@AllArgsConstructor
@Builder
public class Instruction {
    private int OP;
    private int X;
    private int Y;
    private int N;
    private int NNN;
    private int KK;
    @Setter
    private OpcodeType opcodeType;
}
