/*
 * @(#)ImageTool.java
 *
 * Copyright (c) 1996-2010 The authors and contributors of JHotDraw.
 * You may not use, copy or modify this file, except in compliance with the
 * accompanying license terms.
 */
package org.jhotdraw.draw.tool;

import java.awt.*;
import java.io.*;
import java.util.*;
import javax.swing.*;
import org.jhotdraw.draw.AttributeKey;
import org.jhotdraw.draw.DrawingEditor;
import org.jhotdraw.draw.DrawingView;
import org.jhotdraw.draw.figure.ImageHolderFigure;

/**
 * A tool to create new figures that implement the ImageHolderFigure
 * interface, such as ImageFigure. The figure to be created is specified by a
 * prototype.
 * <p>
 * Immediately, after the ImageTool has been activated, it opens a JFileChooser,
 * letting the user specify an image file. The the user then performs
 * the following mouse gesture:
 * <ol>
 * <li>Press the mouse button and drag the mouse over the DrawingView.
 * This defines the bounds of the created figure.</li>
 * </ol>
 *
 * <hr>
 * <b>Design Patterns</b>
 *
 * <p>
 * <em>Prototype</em><br>
 * The {@code ImageTool} creates new figures by cloning a prototype
 * {@code ImageHolderFigure} object.<br>
 * Prototype: {@link ImageHolderFigure}; Client: {@link ImageTool}.
 * <hr>
 *
 * @author Werner Randelshofer
 * @version $Id$
 */
public class ImageTool extends CreationTool {

    private static final long serialVersionUID = 1L;
    protected FileDialog fileDialog;
    protected JFileChooser fileChooser;
    protected boolean useFileDialog;

    /**
     * Creates a new instance.
     */
    public ImageTool(ImageHolderFigure prototype) {
        super(prototype);
    }

    /**
     * Creates a new instance.
     */
    public ImageTool(ImageHolderFigure prototype, Map<AttributeKey<?>, Object> attributes) {
        super(prototype, attributes);
    }

    public void setUseFileDialog(boolean newValue) {
        useFileDialog = newValue;
        if (useFileDialog) {
            fileChooser = null;
        } else {
            fileDialog = null;
        }
    }

    public boolean isUseFileDialog() {
        return useFileDialog;
    }

    @Override
    public void activate(DrawingEditor editor) {
        super.activate(editor);
        final DrawingView view = getView();
        if (view == null) {
            return;
        }
        File file = selectFile(view);
        if (file != null) {
            loadImage(file, view);
        } else {
            handleNoFileSelected();
        }
    }

    private File selectFile(DrawingView view) {
        if (useFileDialog) {
            return selectFileUsingFileDialog();
        } else {
            return selectFileUsingFileChooser(view);
        }
    }

    private File selectFileUsingFileDialog() {
        getFileDialog().setVisible(true);
        if (getFileDialog().getFile() != null) {
            return new File(getFileDialog().getDirectory(), getFileDialog().getFile());
        }
        return null;
    }

    private File selectFileUsingFileChooser(DrawingView view) {
        if (getFileChooser().showOpenDialog(view.getComponent()) == JFileChooser.APPROVE_OPTION) {
            return getFileChooser().getSelectedFile();
        }
        return null;
    }

    private void loadImage(File file, DrawingView view) {
        final ImageHolderFigure loaderFigure = ((ImageHolderFigure) prototype.clone());
        new SwingWorker<Void, Void>() {
            @Override
            protected Void doInBackground() throws Exception {
                loaderFigure.loadImage(file);
                return null;
            }

            @Override
            protected void done() {
                handleImageLoaded(loaderFigure, view);
            }
        }.execute();
    }

    private void handleImageLoaded(ImageHolderFigure loaderFigure, DrawingView view) {
        try {
            if (createdFigure == null) {
                ((ImageHolderFigure) prototype).setImage(loaderFigure.getImageData(), loaderFigure.getBufferedImage());
            } else {
                ((ImageHolderFigure) createdFigure).setImage(loaderFigure.getImageData(), loaderFigure.getBufferedImage());
            }
        } catch (IOException ex) {
            showErrorDialog(view, ex.getMessage());
        }
    }

    private void handleNoFileSelected() {
        if (isToolDoneAfterCreation()) {
            fireToolDone();
        }
    }

    private void showErrorDialog(DrawingView view, String message) {
        JOptionPane.showMessageDialog(view.getComponent(),
                message,
                null,
                JOptionPane.ERROR_MESSAGE);
    }

    private JFileChooser getFileChooser() {
        if (fileChooser == null) {
            fileChooser = new JFileChooser();
        }
        return fileChooser;
    }

    private FileDialog getFileDialog() {
        if (fileDialog == null) {
            fileDialog = new FileDialog(new Frame());
        }
        return fileDialog;
    }
}
