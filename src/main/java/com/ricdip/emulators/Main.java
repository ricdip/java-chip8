package com.ricdip.emulators;

import com.ricdip.emulators.cli.CLIApplication;
import com.ricdip.emulators.engine.Chip8Executor;
import picocli.CommandLine;

public class Main {
    public static void main(String[] args) {
        int exitCode = new CommandLine(new CLIApplication(Chip8Executor::startEmulation)).execute(args);
        System.exit(exitCode);
    }
}
