package com.ricdip.emulators.javachip8.cli;

import org.slf4j.simple.SimpleLogger;
import picocli.CommandLine.ArgGroup;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;

import java.util.Optional;

@Command(
        name = "<jar file name>",
        versionProvider = BuildInfoVersionProvider.class,
        mixinStandardHelpOptions = true,
        description = "A simple CHIP-8 implementation written in Java."
)
public class CLIApplication implements Runnable {
    @ArgGroup
    private final LoggingArgGroup loggingArgGroup = new LoggingArgGroup();
    private final ICLIRunner runner;
    @Option(names = {"-r", "--rom"}, description = "Path to CHIP-8 ROM file.", required = true)
    private String romPath;
    @Option(names = {"-s", "--seed"}, description = "Set random seed.")
    private Long randomSeed;

    public CLIApplication(ICLIRunner runner) {
        this.runner = runner;
    }

    @Override
    public void run() {
        Optional<LoggingLevel> loggingLevel = loggingArgGroup.getLoggingLevel();
        loggingLevel.ifPresent(level -> System.setProperty(SimpleLogger.DEFAULT_LOG_LEVEL_KEY, level.toString()));
        runner.run(romPath, randomSeed);
    }
}