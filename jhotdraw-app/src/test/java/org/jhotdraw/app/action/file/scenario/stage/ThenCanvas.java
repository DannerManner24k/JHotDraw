package org.jhotdraw.app.action.file.scenario.stage;

import javax.swing.*;

import static org.junit.Assert.*;

public class ThenCanvas {

    // Check if the canvas is closed
    public static void canvasIsClosed(JFrame window) {
        window.dispose();
        assertEquals(0, window.getContentPane().getComponentCount());
    }


    // Check if a new canvas is created
    public static void newCanvasIsCreated(JFrame window) {
        assertTrue(window.isVisible());
        assertEquals(1, window.getContentPane().getComponentCount());
    }

    // Check if the application exited
    public static void applicationExited(JFrame window) {
        assertFalse(window.isVisible());
    }
}
