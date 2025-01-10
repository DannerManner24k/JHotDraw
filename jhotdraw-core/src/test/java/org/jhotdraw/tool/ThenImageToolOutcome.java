package org.jhotdraw.tool;

import com.tngtech.jgiven.Stage;
import org.jhotdraw.draw.Drawing;
import org.jhotdraw.draw.figure.ImageHolderFigure;
import static org.junit.jupiter.api.Assertions.*;

public class ThenImageToolOutcome extends Stage<ThenImageToolOutcome> {
    private Drawing drawing;

    public ThenImageToolOutcome the_canvas_contains_an_image_at(int x, int y) {
        boolean found = drawing.getChildren().stream().anyMatch(figure -> {
            if (figure instanceof ImageHolderFigure) {
                ImageHolderFigure imgFig = (ImageHolderFigure) figure;
                return imgFig.getBounds().getX() == x && imgFig.getBounds().getY() == y;
            }
            return false;
        });
        assertTrue(found, "The image is not at the expected position.");
        return this;
    }

    public ThenImageToolOutcome the_image_has_dimensions(int width, int height) {
        boolean validDimensions = drawing.getChildren().stream().anyMatch(figure -> {
            if (figure instanceof ImageHolderFigure) {
                ImageHolderFigure imgFig = (ImageHolderFigure) figure;
                return imgFig.getBounds().getWidth() == width && imgFig.getBounds().getHeight() == height;
            }
            return false;
        });
        assertTrue(validDimensions, "The image does not have the expected dimensions.");
        return this;
    }
}
