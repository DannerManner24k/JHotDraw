package org.jhotdraw.app.action.file.scenario;

import org.jhotdraw.app.action.file.scenario.stage.*;
import org.junit.Test;

import javax.swing.*;

public class NewCanvasScenarioTest {

    JFrame window = new JFrame();

    @Test
    public void testNewCanvas() {
        GivenCanvas.noOpenCanvas(window);
        WhenAction.clickButton(window, "New Canvas");
        ThenCanvas.newCanvasIsCreated(window);
    }

}
