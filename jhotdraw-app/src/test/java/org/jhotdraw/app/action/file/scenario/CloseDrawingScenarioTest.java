package org.jhotdraw.app.action.file.scenario;

import org.jhotdraw.app.action.file.scenario.stage.*;
import org.junit.Test;

import javax.swing.*;

public class CloseDrawingScenarioTest {

    JFrame window = new JFrame();

    @Test
    public void testCloseCanvas() {
        GivenCanvas.existingCanvas(window);
        WhenAction.clickButton(window, "Close Canvas");
        ThenCanvas.canvasIsClosed(window);
    }
}
