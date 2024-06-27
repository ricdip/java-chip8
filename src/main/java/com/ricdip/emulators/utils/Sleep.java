package com.ricdip.emulators.utils;

import com.ricdip.emulators.exception.ExecutionException;

public final class Sleep {
    private Sleep() {
    }

    public static void fromFrequency(int hertz) {
        // Hz = 1 / s => s = 1 / Hz
        double millis = (1.0 / hertz) * 1000;

        try {
            Thread.sleep(Math.round(millis));
        } catch (InterruptedException e) {
            throw new ExecutionException(String.format(
                    "Error during sleep for %f milliseconds (from frequency %d Hz)",
                    millis,
                    hertz),
                    e
            );
        }
    }
}