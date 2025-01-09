package org.jhotdraw.app.action.file.scenario.stage;
import javax.swing.*;

public class GivenCanvas {

    // Simulate an existing open canvas in the window
    public static void existingCanvas(JFrame window) {
        JPanel panel = new JPanel();
        window.add(panel);
        window.setVisible(true);
    }

    // Simulate no open canvas in the window
    public static void noOpenCanvas(JFrame window) {
        window.getContentPane().removeAll();
        window.revalidate();
        window.repaint();
    }
}

