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

    public Rom(@NonNull String romPath) {
        try (InputStream romInputStream = this.getClass().getClassLoader().getResourceAsStream(romPath)) {
            Objects.requireNonNull(romInputStream);
            romName = Path.of(romPath).getFileName().toString();
            romContent = romInputStream.readAllBytes();
        } catch (Exception e) {
            throw new RomException(e);
        }
    }
}
