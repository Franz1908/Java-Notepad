package view;

import javax.swing.*;
import java.awt.*;

/**
 * Panel containing the main text editor area.
 * Uses a JTextArea wrapped inside a JScrollPane.
 */
public class TextEditorPanel extends JPanel {

    private JTextArea textArea;

    public TextEditorPanel() {
        setLayout(new BorderLayout());

        // Create the text area
        this.textArea = new JTextArea();
        textArea.setLineWrap(true);       // Enable automatic line wrapping
        textArea.setWrapStyleWord(true);  // Wrap by word, not mid-word

        // Create scroll pane and attach the text area
        JScrollPane scrollPane = new JScrollPane(textArea);

        // Only vertical scroll allowed
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);

        // Add the scroll pane to the panel (the scroll panel contains the textArea inside so both are added to the panel)
        add(scrollPane, BorderLayout.CENTER);
    }
}

