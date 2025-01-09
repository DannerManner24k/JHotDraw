package org.jhotdraw.app.action.file.scenario;

import org.jhotdraw.app.action.file.scenario.stage.*;
import org.junit.Test;

import javax.swing.*;

public class NewWindowScenarioTest {

    JFrame window = new JFrame();

    @Test
    public void testNewWindow() {
        GivenCanvas.existingCanvas(window);
        WhenAction.selectMenu(window, "New Window");
        ThenCanvas.newCanvasIsCreated(window);
    }
}
