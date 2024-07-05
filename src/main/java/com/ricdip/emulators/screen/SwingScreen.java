package com.ricdip.emulators.screen;

import com.ricdip.emulators.engine.Display;
import com.ricdip.emulators.engine.Keyboard;
import lombok.Getter;

import javax.swing.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Optional;

@Getter
public class SwingScreen implements Screen {
    private final SwingDisplayComponent swingDisplayComponent;
    private final JFrame frame;
    public static final String WINDOW_TITLE = "Chip-8";

    public SwingScreen(Display display) {
        frame = new JFrame(WINDOW_TITLE);
        frame.setSize(
                Display.DISPLAY_WIDTH * SwingDisplayComponent.PIXEL_SIZE,
                Display.DISPLAY_HEIGHT * SwingDisplayComponent.PIXEL_SIZE
        );
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        swingDisplayComponent = new SwingDisplayComponent(display);

        frame.add(swingDisplayComponent);
        frame.setVisible(true);
    }

    @Override
    public void redraw(Display display) {
        swingDisplayComponent.redraw(display);
    }

    @Override
    public void configureKeyListener(Keyboard keyboard) {
        frame.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                Optional<Integer> keyPressed = getChip8Key(e.getKeyChar());

                keyPressed.ifPresent(keyboard::setKeyPressed);
            }

            @Override
            public void keyReleased(KeyEvent e) {
                Optional<Integer> keyReleased = getChip8Key(e.getKeyChar());

                keyReleased.ifPresent(keyboard::setKeyReleased);
            }
        });

        frame.setFocusable(true);
        frame.requestFocusInWindow();
    }

    private Optional<Integer> getChip8Key(char key) {
        return switch (Character.toLowerCase(key)) {
            case '1' -> Optional.of(0x1);
            case '2' -> Optional.of(0x2);
            case '3' -> Optional.of(0x3);
            case '4' -> Optional.of(0xC);
            case 'q' -> Optional.of(0x4);
            case 'w' -> Optional.of(0x5);
            case 'e' -> Optional.of(0x6);
            case 'r' -> Optional.of(0xD);
            case 'a' -> Optional.of(0x7);
            case 's' -> Optional.of(0x8);
            case 'd' -> Optional.of(0x9);
            case 'f' -> Optional.of(0xE);
            case 'z' -> Optional.of(0xA);
            case 'x' -> Optional.of(0x0);
            case 'c' -> Optional.of(0xB);
            case 'v' -> Optional.of(0xF);
            default -> Optional.empty();
        };
    }
}
