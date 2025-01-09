package org.jhotdraw.app.action.file.scenario;

import org.jhotdraw.app.action.file.scenario.stage.*;
import org.junit.Test;

import javax.swing.*;

public class ExitApplicationScenarioTest {

    JFrame window = new JFrame();

    @Test
    public void testExitApplication() {
        GivenCanvas.existingCanvas(window);
        WhenAction.selectMenu(window, "Exit");
        ThenCanvas.applicationExited(window);
    }
}
