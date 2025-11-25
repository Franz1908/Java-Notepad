package view;

import javax.swing.*;
import java.awt.*;

/**
 * Main application window for the Notepad.
 * Creates and arranges the editor panel and the top menu bar.
 */
public class NotepadWindow extends JFrame {

    private static final int HEIGHT = 400;
    private static final int WIDTH = 600;

    private TextEditorPanel textEditorPanel;
    private AppMenuBar menuBar;

    public NotepadWindow() {
        super("Notepad");

        // Window layout
        setLayout(new BorderLayout());

        // Create UI components
        this.textEditorPanel = new TextEditorPanel();
        this.menuBar = new AppMenuBar();

        // Add components to the window
        add(textEditorPanel, BorderLayout.CENTER);
        add(menuBar, BorderLayout.NORTH);

        // Window settings
        setSize(WIDTH, HEIGHT);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Center the window on screen
        setVisible(true);
    }
}

