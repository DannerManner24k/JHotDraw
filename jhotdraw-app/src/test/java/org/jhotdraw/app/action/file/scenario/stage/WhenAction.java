package org.jhotdraw.app.action.file.scenario.stage;

import javax.swing.*;

public class WhenAction {

    // Simulate a button click action by the user
    public static void clickButton(JFrame window, String actionCommand) {
        JButton button = new JButton(actionCommand);

        button.addActionListener(e -> {
            // Action for creating a new canvas
            if (actionCommand.equals("New Canvas")) {
                JPanel panel = new JPanel();
                window.getContentPane().removeAll();  // Clear any existing canvases
                window.add(panel);  // Add a new canvas (panel)
                window.revalidate();
                window.repaint();
                window.setVisible(true);  // Ensure the window is visible
            }

            // Action for closing the canvas
            if (actionCommand.equals("Close Canvas")) {
                window.getContentPane().removeAll();  // Remove all components (canvas)
                window.revalidate();
                window.repaint();
                window.dispose();  // Dispose the frame to close it completely
            }
        });

        // Simulate the button click
        button.doClick();
    }

    // Simulate selecting a menu item
    public static void selectMenu(JFrame window, String menuItem) {
        JMenuItem item = new JMenuItem(menuItem);
        item.addActionListener(e -> {
            if (menuItem.equals("Exit")) {
                window.dispose();  // Close the window when "Exit" is selected
            }
        });
        item.doClick();
    }
}
