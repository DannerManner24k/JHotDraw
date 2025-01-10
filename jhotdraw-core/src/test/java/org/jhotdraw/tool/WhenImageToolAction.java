package org.jhotdraw.tool;

import com.tngtech.jgiven.Stage;
import org.jhotdraw.draw.tool.ImageTool;

import java.io.File;

public class WhenImageToolAction extends Stage<WhenImageToolAction> {
    private ImageTool imageTool;

    public WhenImageToolAction the_user_selects_an_image(String filePath) {
        File file = new File(filePath);
        imageTool.loadImage(file, null); // Pass the correct DrawingView if available
        return this;
    }

    public WhenImageToolAction the_user_places_the_image_on_canvas(int x1, int y1, int x2, int y2) {
        // Simulate placing the image by defining bounds using mouse actions
        imageTool.mousePressed(new java.awt.event.MouseEvent(
                null, 0, 0, 0, x1, y1, 1, false
        ));
        imageTool.mouseDragged(new java.awt.event.MouseEvent(
                null, 0, 0, 0, x2, y2, 1, false
        ));
        imageTool.mouseReleased(new java.awt.event.MouseEvent(
                null, 0, 0, 0, x2, y2, 1, false
        ));
        return this;
    }
}
