package com.ricdip.emulators.model;

import com.ricdip.emulators.exception.RomException;
import lombok.Getter;
import lombok.NonNull;

import java.io.InputStream;
import java.nio.file.Path;
import java.util.Objects;

@Getter
public class Rom {
    private final String romName;
    private final byte[] romContent;
    private final int romSize;

    /**
     * create ROM object from data read from ROM file.
     *
     * @param romPath path to ROM file
     * @throws RomException if ROM file is not found or an exception occurs when reading file
     */
    public Rom(@NonNull String romPath) {
        try (InputStream romInputStream = this.getClass().getClassLoader().getResourceAsStream(romPath)) {
            Objects.requireNonNull(romInputStream);
            romName = Path.of(romPath).getFileName().toString();
            romContent = romInputStream.readAllBytes();
            romSize = romContent.length;
        } catch (Exception e) {
            throw new RomException(e);
        }
    }
}
