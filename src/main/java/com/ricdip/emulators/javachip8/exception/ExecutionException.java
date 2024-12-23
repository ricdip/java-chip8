package com.ricdip.emulators.javachip8.exception;

public class ExecutionException extends RuntimeException {
    public ExecutionException(String message) {
        super(message);
    }

    public ExecutionException(String message, Throwable cause) {
        super(message, cause);
    }
}
