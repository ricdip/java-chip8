package com.ricdip.emulators.javachip8;

import com.ricdip.emulators.javachip8.cli.CLIApplication;
import com.ricdip.emulators.javachip8.engine.Chip8Executor;
import picocli.CommandLine;

public class Main {
    public static void main(String[] args) {
        int exitCode = new CommandLine(new CLIApplication(Chip8Executor::startEmulation)).execute(args);
        System.exit(exitCode);
    }
}
