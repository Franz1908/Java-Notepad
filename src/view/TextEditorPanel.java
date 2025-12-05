package view;

import javax.swing.*;
import java.awt.*;

/**
 * Panel containing the main text editor area.
 * Uses a JTextArea wrapped inside a JScrollPane to provide
 * scrolling functionality and text editing capabilities.
 */
public class TextEditorPanel extends JPanel {

    private JTextArea textArea;

    /**
     * Constructor that initializes the text editor panel.
     * Configures the text area with word wrapping and adds scroll functionality.
     */
    public TextEditorPanel() {
        setLayout(new BorderLayout());

        // Create the text area
        this.textArea = new JTextArea();
        textArea.setLineWrap(true);       // Enable automatic line wrapping
        textArea.setWrapStyleWord(true);  // Wrap by word boundaries, not mid-word

        // Create scroll pane and attach the text area
        JScrollPane scrollPane = new JScrollPane(textArea);

        // Configure scrollbar policies
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);

        // Add the scroll pane to the panel
        // Note: The scroll pane contains the textArea, so both are effectively added
        add(scrollPane, BorderLayout.CENTER);
    }

    /**
     * Gets the text area component.
     *
     * @return the JTextArea used for text editing
     */
    public JTextArea getTextArea() {
        return this.textArea;
    }
}

