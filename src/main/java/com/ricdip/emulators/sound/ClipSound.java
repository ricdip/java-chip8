package com.ricdip.emulators.sound;

import com.ricdip.emulators.exception.SoundException;
import lombok.extern.slf4j.Slf4j;

import javax.sound.sampled.*;
import java.io.BufferedInputStream;
import java.io.InputStream;

@Slf4j
public class ClipSound implements Sound {
    private static final String BEEP_CLIP_RESOURCE_PATH = "sound/beep.wav";
    private final Clip beepClip;

    public ClipSound() {
        beepClip = loadAudioClip();
        log.info("sound initialized");
    }

    @Override
    public void performSound() {
        beepClip.start();
    }

    @Override
    public void close() {
        beepClip.close();
        log.info("sound closed");
    }

    private Clip loadAudioClip() {
        try (InputStream beepClipInputStream = getClass().getClassLoader().getResourceAsStream(BEEP_CLIP_RESOURCE_PATH)) {
            if (beepClipInputStream == null) {
                throw new SoundException("Beep sound InputStream is null");
            }
            try (InputStream bufferedBeepClipInputStream = new BufferedInputStream(beepClipInputStream);
                 AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(bufferedBeepClipInputStream)
            ) {
                AudioFormat audioFormat = audioInputStream.getFormat();
                DataLine.Info info = new DataLine.Info(Clip.class, audioFormat);
                Clip audioClip = (Clip) AudioSystem.getLine(info);
                audioClip.open(audioInputStream);
                return audioClip;
            }
        } catch (Exception e) {
            throw new SoundException("Exception occurred during sound load", e);
        }
    }
}
