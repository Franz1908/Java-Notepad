package controller;

import model.DocumentModel;
import service.FileService;
import view.NotepadWindow;

import javax.swing.*;

public class NotepadController {

    private NotepadWindow notepadWindow;
    private DocumentModel documentModel;

    public NotepadController(NotepadWindow notepadWindow, DocumentModel documentModel) {
        this.notepadWindow = notepadWindow;
        this.documentModel = documentModel;

        JMenu fileMenu = notepadWindow.getAppMenuBar().getFileMenu();
        JMenu editMenu = notepadWindow.getAppMenuBar().getEditMenu();
        JMenu formatMenu = notepadWindow.getAppMenuBar().getFormatMenu();

        fileMenu.getItem(0).addActionListener(e -> saveFile());
        fileMenu.getItem(1).addActionListener(e -> saveAsFile());
        fileMenu.getItem(2).addActionListener(e -> openFile());

    }

    public void saveFile(){
        String text = notepadWindow.getTextEditorPanel().getTextArea().getText();
        documentModel.setText(text);
        FileService.saveFile();
        System.out.println("Saving file...");
    }

    public void saveAsFile(){
        System.out.println("Saving as file...");
    }

    public void openFile(){
        System.out.println("Opening file...");
    }

}
