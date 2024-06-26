package com.ricdip.emulators.graphics;

import com.ricdip.emulators.engine.Display;
import lombok.AllArgsConstructor;

import javax.swing.*;
import java.awt.*;

@AllArgsConstructor
public class SwingDisplayComponent extends JComponent {
    private Display display;
    public static final int PIXEL_SIZE = 10;

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        for (int height = 0; height < Display.DISPLAY_HEIGHT; height++) {
            for (int width = 0; width < Display.DISPLAY_WIDTH; width++) {
                if (display.getDisplay()[(height * Display.DISPLAY_WIDTH) + width]) {
                    g.setColor(Color.WHITE);
                } else {
                    g.setColor(Color.BLACK);
                }

                g.fillRect(width * PIXEL_SIZE, height * PIXEL_SIZE, PIXEL_SIZE, PIXEL_SIZE);
            }
        }
    }

    public void redraw(Display display) {
        this.display = display;
        repaint();
    }
}
