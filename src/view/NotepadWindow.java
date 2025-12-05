package view;

import javax.swing.*;
import java.awt.*;

/**
 * Main application window for the Notepad.
 * Creates and arranges the editor panel and the top menu bar.
 * This is the primary view component in the MVC architecture.
 */
public class NotepadWindow extends JFrame {

    private static final int HEIGHT = 400;
    private static final int WIDTH = 600;

    private TextEditorPanel textEditorPanel;
    private AppMenuBar appMenuBar;

    /**
     * Constructor that initializes and displays the main window.
     * Sets up the layout, creates UI components, and configures window properties.
     */
    public NotepadWindow() {
        super("Notepad - Untitled");

        // Set BorderLayout for the main window
        setLayout(new BorderLayout());

        // Create UI components
        this.textEditorPanel = new TextEditorPanel();
        this.appMenuBar = new AppMenuBar();

        // Add components to the window
        add(textEditorPanel, BorderLayout.CENTER);  // Text editor in the center
        add(appMenuBar, BorderLayout.NORTH);        // Menu bar at the top

        // Configure window settings
        setSize(WIDTH, HEIGHT);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);  // Center the window on screen
        setVisible(true);
    }

    /**
     * Gets the text editor panel component.
     *
     * @return the TextEditorPanel containing the text area
     */
    public TextEditorPanel getTextEditorPanel() {
        return this.textEditorPanel;
    }

    /**
     * Gets the application menu bar component.
     *
     * @return the AppMenuBar containing File, Edit, and Format menus
     */
    public AppMenuBar getAppMenuBar() {
        return this.appMenuBar;
    }
}

