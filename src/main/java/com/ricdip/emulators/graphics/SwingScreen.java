package com.ricdip.emulators.graphics;

import com.ricdip.emulators.engine.Display;
import lombok.Getter;

import javax.swing.*;

@Getter
public class SwingScreen implements Screen {
    private final SwingDisplayComponent swingDisplayComponent;
    public static final String WINDOW_TITLE = "Chip-8";

    public SwingScreen(Display display) {
        JFrame frame = new JFrame(WINDOW_TITLE);
        frame.setSize(Display.DISPLAY_WIDTH * SwingDisplayComponent.PIXEL_SIZE, Display.DISPLAY_HEIGHT * SwingDisplayComponent.PIXEL_SIZE);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        swingDisplayComponent = new SwingDisplayComponent(display);

        frame.add(swingDisplayComponent);
        frame.setVisible(true);
    }

    @Override
    public void redraw(Display display) {
        swingDisplayComponent.redraw(display);
    }
}
