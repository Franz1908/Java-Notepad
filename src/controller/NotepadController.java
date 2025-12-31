package controller;

import model.DocumentModel;
import service.FileService;
import view.NotepadWindow;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;

/**
 * Controller class that manages user interactions and coordinates
 * between the view (NotepadWindow) and the model (DocumentModel).
 */
public class NotepadController {

    private NotepadWindow notepadWindow;
    private DocumentModel documentModel;

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
        textArea.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent documentEvent) {
                onTextChanged();
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

        // Get menu references
        JMenu fileMenu = notepadWindow.getAppMenuBar().getFileMenu();
        JMenu editMenu = notepadWindow.getAppMenuBar().getEditMenu();
        JMenu formatMenu = notepadWindow.getAppMenuBar().getFormatMenu();

        // Attach action listeners to File menu items
        fileMenu.getItem(0).addActionListener(e -> saveFile());
        fileMenu.getItem(1).addActionListener(e -> saveAsFile());
        fileMenu.getItem(2).addActionListener(e -> openFile());

        fileMenu.getItem(0).setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, KeyEvent.CTRL_DOWN_MASK));
        fileMenu.getItem(1).setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_A, KeyEvent.CTRL_DOWN_MASK));
        fileMenu.getItem(2).setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, KeyEvent.CTRL_DOWN_MASK));
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
            // Get current text from the text area
            String text = documentModel.getText();
            System.out.println(text);

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

        System.out.println("Saving file...");
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

            // Get current text and update model
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

        System.out.println("Saving file as...");
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

        System.out.println("Opening file...");
    }

    private void onTextChanged(){
        JTextArea textArea = notepadWindow.getTextEditorPanel().getTextArea();
        documentModel.setText(textArea.getText());
        System.out.println(textArea.getText());
        documentModel.setModified(true);
        updateWindowTitle();
    }

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
