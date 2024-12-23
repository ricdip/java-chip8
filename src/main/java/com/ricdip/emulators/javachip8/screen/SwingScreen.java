package com.ricdip.emulators.javachip8.screen;

import com.ricdip.emulators.javachip8.engine.Display;
import com.ricdip.emulators.javachip8.engine.Keyboard;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import javax.swing.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowEvent;
import java.util.Optional;

@Getter
@Slf4j
public class SwingScreen implements Screen {
    public static final String WINDOW_TITLE = "CHIP-8";
    private final SwingDisplayComponent swingDisplayComponent;
    private final JFrame frame;

    public SwingScreen(Display display) {
        frame = new JFrame(WINDOW_TITLE);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setResizable(false);
        frame.setVisible(true);
        swingDisplayComponent = new SwingDisplayComponent(display);
        frame.add(swingDisplayComponent);
        frame.pack();
        frame.setLocationRelativeTo(null);
        configureOnWindowClosedListener();
        log.info("screen initialized");
    }

    @Override
    public void redraw(Display display) {
        swingDisplayComponent.redraw(display);
    }

    @Override
    public boolean isClosed() {
        return !frame.isDisplayable();
    }

    @Override
    public void attachKeyboard(Keyboard keyboard) {
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
        log.info("keyboard attached");
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

    private void configureOnWindowClosedListener() {
        frame.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e) {
                for (KeyListener keyListener : frame.getKeyListeners()) {
                    frame.removeKeyListener(keyListener);
                }
            }
        });
    }
}
