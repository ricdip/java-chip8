package com.ricdip.emulators.cli;

import picocli.CommandLine.Option;

import java.util.Optional;

public class LoggingArgGroup {
    @Option(names = {"-lo", "--log-off"}, description = "Disable logging")
    private boolean loggingOff;

    @Option(names = {"-li", "--log-info"}, description = "Enable logging level INFO [default]")
    private boolean loggingInfo;

    @Option(names = {"-ld", "--log-debug"}, description = "Enable logging level DEBUG")
    private boolean loggingDebug;

    @Option(names = {"-lt", "--log-trace"}, description = "Enable logging level TRACE")
    private boolean loggingTrace;

    public Optional<LoggingLevel> getLoggingLevel() {
        if (loggingOff) {
            return Optional.of(LoggingLevel.OFF);
        }
        if (loggingInfo) {
            return Optional.of(LoggingLevel.INFO);
        }
        if (loggingDebug) {
            return Optional.of(LoggingLevel.DEBUG);
        }
        if (loggingTrace) {
            return Optional.of(LoggingLevel.TRACE);
        } else {
            return Optional.empty();
        }
    }
}
