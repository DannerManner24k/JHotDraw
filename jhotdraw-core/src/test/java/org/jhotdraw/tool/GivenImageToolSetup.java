package org.jhotdraw.tool;

import com.tngtech.jgiven.Stage;
import org.jhotdraw.draw.DefaultDrawingEditor;
import org.jhotdraw.draw.DefaultDrawingView;
import org.jhotdraw.draw.tool.ImageTool;
import org.jhotdraw.draw.figure.ImageFigure;

public class GivenImageToolSetup extends Stage<GivenImageToolSetup> {
    private DefaultDrawingEditor drawingEditor;
    private DefaultDrawingView drawingView;
    private ImageTool imageTool;

    public GivenImageToolSetup a_drawing_editor() {
        drawingEditor = new DefaultDrawingEditor();
        return this;
    }

    public GivenImageToolSetup a_drawing_view() {
        drawingView = new DefaultDrawingView();
        drawingEditor.add(drawingView);
        return this;
    }

    public GivenImageToolSetup an_image_tool_with_prototype() {
        ImageFigure prototype = new ImageFigure();
        imageTool = new ImageTool(prototype);
        drawingEditor.setTool(imageTool);
        return this;
    }

    public DefaultDrawingEditor getDrawingEditor() {
        return drawingEditor;
    }

    public DefaultDrawingView getDrawingView() {
        return drawingView;
    }

    public ImageTool getImageTool() {
        return imageTool;
    }
}
