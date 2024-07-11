package com.ricdip.emulators.exception;

public class SoundException extends RuntimeException {
    public SoundException(String message) {
        super(message);
    }

    public SoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
