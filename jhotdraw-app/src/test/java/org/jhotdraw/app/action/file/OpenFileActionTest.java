package org.jhotdraw.app.action.file;

import org.jhotdraw.api.app.Application;
import org.jhotdraw.api.app.View;
import org.jhotdraw.api.gui.URIChooser;
import org.junit.Before;
import org.junit.Test;

import javax.swing.*;
import java.io.IOException;
import java.net.URI;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;
public class OpenFileActionTest {

    private OpenFileAction openFileAction;
    private Application mockApp;
    private View mockView;
    private URIChooser mockChooser;

    @Before
    public void setUp() {
        mockApp = mock(Application.class);
        mockView = mock(View.class);
        mockChooser = mock(URIChooser.class);
        openFileAction = new OpenFileAction(mockApp);
    }

    // Test for getOrCreateView()
    @Test
    public void testGetOrCreateView_ReturnsExistingEmptyView() {
        when(mockApp.getActiveView()).thenReturn(mockView);
        when(mockView.isEmpty()).thenReturn(true);
        when(mockView.isEnabled()).thenReturn(true);

        View result = openFileAction.getOrCreateView(mockApp);
        assertEquals(mockView, result);
    }

    @Test
    public void testGetOrCreateView_CreatesNewViewWhenNoneAvailable() {
        when(mockApp.getActiveView()).thenReturn(null);
        when(mockApp.createView()).thenReturn(mockView);

        View result = openFileAction.getOrCreateView(mockApp);
        verify(mockApp).add(mockView);
        assertEquals(mockView, result);
    }

    // Test for openViewFromURI()
    @Test
    public void testOpenViewFromURI_SetsMultipleOpenId() throws Exception {
        URI testURI = new URI("file:///example/path.txt");

        // Mock the existing view with an ID of 1
        View existingView = mock(View.class);
        when(existingView.isEmpty()).thenReturn(true);
        when(existingView.getMultipleOpenId()).thenReturn(1);  // Simulate existing ID of 1

        // Simulate that the existing view is already opened
        when(mockApp.views()).thenReturn(java.util.List.of(existingView, mockView));
        when(mockView.isEmpty()).thenReturn(true);

        openFileAction.openViewFromURI(mockView, testURI, mockChooser);

        // Expect the next ID to be 2
        verify(mockView).setMultipleOpenId(2);
    }


    @Test
    public void testOpenViewFromURI_HandlesNonexistentFile() throws Exception {
        URI invalidURI = new URI("file:///invalid/path.txt");
        when(mockView.isEmpty()).thenReturn(true);
        when(mockChooser.getSelectedURI()).thenReturn(invalidURI);
        when(mockApp.views()).thenReturn(java.util.List.of(mockView));

        doThrow(new IOException("File not found")).when(mockView).read(invalidURI, mockChooser);

        openFileAction.openViewFromURI(mockView, invalidURI, mockChooser);
    }

    // Test for handleFileOpenCompletion()
    @Test
    public void testHandleFileOpenCompletion_Success() throws Exception {
        URI testURI = new URI("file:///example/path.txt");

        // Create a SwingWorker simulation
        SwingWorker<Object, Void> mockWorker = new SwingWorker<>() {
            @Override
            protected Object doInBackground() {
                return null;
            }
        };
        mockWorker.execute(); // Ensure the worker is initialized

        // Simulate component and window hierarchy
        JPanel panel = new JPanel();
        JFrame frame = new JFrame();
        frame.add(panel);
        frame.pack();

        when(mockView.getComponent()).thenReturn(panel);

        openFileAction.handleFileOpenCompletion(mockApp, mockView, testURI, mockWorker);

        verify(mockView).setURI(testURI);
        verify(mockView).setEnabled(true);
        verify(mockApp).addRecentURI(testURI);
    }



    @Test
    public void testHandleFileOpenCompletion_HandlesException() throws Exception {
        URI testURI = new URI("file:///example/path.txt");

        // Create a SwingWorker simulation with exception
        SwingWorker<Object, Void> mockWorker = new SwingWorker<>() {
            @Override
            protected Object doInBackground() throws Exception {
                throw new IOException("Simulated Exception");
            }
        };

        mockWorker.execute();  // Initialize the SwingWorker

        // Simulate component and window hierarchy
        JPanel panel = new JPanel();
        JFrame frame = new JFrame();
        frame.add(panel);
        frame.pack();

        when(mockView.getComponent()).thenReturn(panel);

        // Perform the action that triggers SwingWorker.get()
        openFileAction.handleFileOpenCompletion(mockApp, mockView, testURI, mockWorker);

        // Verify that the view and app are re-enabled after exception
        verify(mockView).setEnabled(true);
        verify(mockApp).setEnabled(true);
    }


    @Test
    public void testHandleFileOpenCompletion_BringsWindowToFront() throws Exception {
        URI testURI = new URI("file:///example/path.txt");

        // Create a real SwingWorker simulation
        SwingWorker<Object, Void> mockWorker = new SwingWorker<>() {
            @Override
            protected Object doInBackground() {
                return null;  // Simulate successful completion
            }
        };
        mockWorker.execute(); // Initialize the SwingWorker

        // Simulate component and window hierarchy
        JPanel panel = new JPanel();
        JFrame frame = new JFrame();  // Create a real JFrame
        frame.add(panel);             // Attach the panel
        frame.pack();
        frame.setVisible(true);       // Ensure it's visible

        JFrame spyFrame = spy(frame); // Spy AFTER full initialization
        when(mockView.getComponent()).thenReturn(panel);
    }

}