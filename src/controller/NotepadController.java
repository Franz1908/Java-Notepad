package controller;

import model.DocumentModel;
import service.FileService;
import view.NotepadWindow;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;
import java.util.Stack;

/**
 * Controller class that manages user interactions and coordinates
 * between the view (NotepadWindow) and the model (DocumentModel).
 */
public class NotepadController {

    private NotepadWindow notepadWindow;
    private DocumentModel documentModel;

    /** Stack containing previous document states for undo functionality */
    private Stack<String> undoStack = new Stack<>();
    /** Stack containing reverted states for redo functionality */
    private Stack<String> redoStack = new Stack<>();

    /** Last saved state to avoid duplicate entries in the undo stack */
    private String lastSavedState = "";

    /** Flag to indicate if the current update is triggered by an undo operation */
    private boolean isUndo = false;

    /**
     * Constructor that initializes the controller and sets up menu action listeners.
     *
     * @param notepadWindow  the main window view
     * @param documentModel  the document data model
     */
    public NotepadController(NotepadWindow notepadWindow, DocumentModel documentModel) {
        this.notepadWindow = notepadWindow;
        this.documentModel = documentModel;
        JTextArea textArea = notepadWindow.getTextEditorPanel().getTextArea();

        // Add document listener to sync model with view on every text change
        textArea.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent documentEvent) {
                onTextChanged();
                // If this is a new user action (not an undo), clear the redo history
                if(!isUndo){
                    redoStack.clear();
                }
                else{
                    // Reset the flag after the undo operation is processed
                    isUndo = false;
                }
            }

            @Override
            public void removeUpdate(DocumentEvent documentEvent) {
                onTextChanged();
            }

            @Override
            public void changedUpdate(DocumentEvent documentEvent) {
                onTextChanged();
            }
        });

        // Add key listener to save state at appropriate moments for undo functionality
        textArea.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                int keyCode = e.getKeyCode();

                // Save state before destructive actions (backspace, delete)
                if (keyCode == KeyEvent.VK_BACK_SPACE || keyCode == KeyEvent.VK_DELETE) {
                    saveState();
                    redoStack.clear();
                }
            }

            @Override
            public void keyTyped(KeyEvent e) {
                char c = e.getKeyChar();

                // Save state after word completion (space, enter, punctuation)
                if (c == ' ' || c == '\n' || c == '.' || c == ',' || c == '!' || c == '?') {
                    saveState();
                    redoStack.clear();
                }
            }
        });

        // Get menu references
        JMenu fileMenu = notepadWindow.getAppMenuBar().getFileMenu();
        JMenu editMenu = notepadWindow.getAppMenuBar().getEditMenu();
        // JMenu formatMenu = notepadWindow.getAppMenuBar().getFormatMenu(); // Unused currently

        // Attach action listeners to File menu items
        fileMenu.getItem(0).addActionListener(e -> saveFile());
        fileMenu.getItem(1).addActionListener(e -> saveAsFile());
        fileMenu.getItem(2).addActionListener(e -> openFile());

        // Set keyboard shortcuts for File menu
        fileMenu.getItem(0).setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, KeyEvent.CTRL_DOWN_MASK));
        fileMenu.getItem(1).setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_A, KeyEvent.CTRL_DOWN_MASK));
        fileMenu.getItem(2).setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, KeyEvent.CTRL_DOWN_MASK));

        // Attach action listeners and shortcuts to Edit menu items
        editMenu.getItem(0).addActionListener(e -> undoState());
        editMenu.getItem(0).setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Z, KeyEvent.CTRL_DOWN_MASK));
        editMenu.getItem(1).addActionListener(e -> redoState());
        editMenu.getItem(1).setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Y, KeyEvent.CTRL_DOWN_MASK));
    }

    /**
     * Saves the current document to its existing file.
     * If no file is associated, delegates to saveAsFile().
     */
    public void saveFile() {
        File file = documentModel.getFile();

        // If no file exists, prompt user to choose one
        if (file == null) {
            saveAsFile();
        } else {
            // Get current text from the model
            String text = documentModel.getText();

            // Update model
            documentModel.setModified(false);
            updateWindowTitle();

            // Save to file
            try {
                FileService.saveFile(file, text);
            } catch (IOException e) {
                JOptionPane.showMessageDialog(
                        notepadWindow,
                        "The file cannot be saved",
                        "Save error",
                        JOptionPane.ERROR_MESSAGE
                );
            }
        }
    }

    /**
     * Opens a file dialog to save the current document with a new name.
     * Automatically appends .txt extension if not provided.
     * Asks for confirmation before overwriting existing files.
     */
    public void saveAsFile() {
        // Create and configure file chooser
        JFileChooser chooser = new JFileChooser();
        FileNameExtensionFilter filter = new FileNameExtensionFilter("Text File(*.txt)", "txt");
        chooser.setFileFilter(filter);
        chooser.setDialogTitle("Save File");
        chooser.setApproveButtonText("Save");

        // Show save dialog
        if (chooser.showSaveDialog(notepadWindow) == JFileChooser.APPROVE_OPTION) {
            File file = chooser.getSelectedFile();

            // Auto-append .txt extension if missing
            if (!file.getName().toLowerCase().endsWith(".txt")) {
                file = new File(file.getAbsolutePath() + ".txt");
            }

            // Confirm overwrite if file already exists
            if (file.exists()) {
                int result = JOptionPane.showConfirmDialog(
                        notepadWindow,
                        "The file '" + file.getName() + "' already exists. Do you want to overwrite it?",
                        "Confirm Overwrite",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.WARNING_MESSAGE
                );

                // User chose not to overwrite, exit without saving
                if (result != JOptionPane.YES_OPTION) {
                    return;
                }
            }

            // Get current text from model and update file reference
            String text = documentModel.getText();
            documentModel.setFile(file);
            documentModel.setModified(false);
            updateWindowTitle();

            // Save to file
            try {
                FileService.saveFile(file, text);
            } catch (IOException e) {
                JOptionPane.showMessageDialog(
                        notepadWindow,
                        "The file cannot be saved",
                        "Save as error",
                        JOptionPane.ERROR_MESSAGE
                );
            }
        }
    }

    /**
     * Opens a file dialog to load an existing text file.
     * Replaces the current text area content with the file content.
     */
    public void openFile() {
        // Create and configure file chooser
        JFileChooser chooser = new JFileChooser();
        FileNameExtensionFilter filter = new FileNameExtensionFilter("Text File(*.txt)", "txt");
        chooser.setFileFilter(filter);
        chooser.setDialogTitle("Open File");
        chooser.setApproveButtonText("Open");

        // Show open dialog
        if (chooser.showOpenDialog(notepadWindow) == JFileChooser.APPROVE_OPTION) {
            File file = chooser.getSelectedFile();

            // Update model with new file reference
            documentModel.setFile(file);
            documentModel.setModified(false);

            updateWindowTitle();

            // Read file and display content
            try {
                String text = FileService.readFile(file);
                documentModel.setText(text);
                notepadWindow.getTextEditorPanel().getTextArea().setText(text);
            } catch (IOException e) {
                JOptionPane.showMessageDialog(
                        notepadWindow,
                        "The file cannot be opened",
                        "Open error",
                        JOptionPane.ERROR_MESSAGE
                );
            }
        }
    }

    /**
     * Handles text changes in the text area.
     * Syncs the model with the view and updates the window title.
     */
    private void onTextChanged() {
        JTextArea textArea = notepadWindow.getTextEditorPanel().getTextArea();
        documentModel.setText(textArea.getText());
        documentModel.setModified(true);
        updateWindowTitle();
    }

    /**
     * Saves the current document state to the undo stack.
     * Only saves if the current text differs from the last saved state.
     */
    private void saveState() {
        String currentText = documentModel.getText();
        // Only save if the content has actually changed since the last save
        if (!currentText.equals(lastSavedState)) {
            undoStack.push(lastSavedState);
            lastSavedState = currentText;
        }
    }

    /**
     * Restores the previous document state from the undo stack.
     * Updates the view which triggers model synchronization via DocumentListener.
     */
    private void undoState() {
        if (!undoStack.isEmpty()) {
            // Save current state to redo stack before undoing
            redoStack.push(documentModel.getText());
            // Retrieve and restore previous state
            String previousState = undoStack.pop();
            lastSavedState = previousState;
            // Set flag to prevent DocumentListener from clearing redo stack during this update
            isUndo = true;
            notepadWindow.getTextEditorPanel().getTextArea().setText(previousState);
        }
    }

    /**
     * Performs the redo operation.
     * Moves the current state to the undo stack and restores the next state from the redo stack.
     */
    private void redoState() {
        if (!redoStack.isEmpty()) {
            // Retrieve the state to redo
            String previousState = redoStack.pop();
            // Push current state back to undo stack
            undoStack.push(documentModel.getText());
            lastSavedState = previousState;
            // Update view
            notepadWindow.getTextEditorPanel().getTextArea().setText(previousState);
        }
    }

    /**
     * Updates the window title to reflect the current file name and modification status.
     * Shows "Untitled" if no file is associated, and adds "*" prefix if modified.
     */
    private void updateWindowTitle() {
        File file = documentModel.getFile();
        String title;

        if (file == null) {
            title = "Notepad - Untitled";
        } else {
            String fileName = file.getName();
            String modifiedMarker = documentModel.isModified() ? "*" : "";
            title = "Notepad - " + modifiedMarker + fileName;
        }

        notepadWindow.setTitle(title);
    }
}
