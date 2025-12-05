package controller;

import model.DocumentModel;
import service.FileService;
import view.NotepadWindow;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.File;
import java.io.IOException;

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
        File file = documentModel.getFile();
        if(file == null){
            saveAsFile();
        }
        else{
            String text = notepadWindow.getTextEditorPanel().getTextArea().getText();
            System.out.println(text);
            documentModel.setText(text);
            documentModel.setModified(false);
            try {
                FileService.saveFile(file, text);
            } catch (IOException e) {
                JOptionPane.showMessageDialog(notepadWindow,
                        "The file cannot be saved",
                        "Save error",
                        JOptionPane.ERROR_MESSAGE);
            }
        }
        System.out.println("Saving file...");
    }

    public void saveAsFile(){
        System.out.println("Saving file as...");
    }

    public void openFile(){
        JFileChooser chooser = new JFileChooser();
        FileNameExtensionFilter filter = new FileNameExtensionFilter("File di testo (*.txt)", "txt");
        chooser.setFileFilter(filter);
        if(chooser.showOpenDialog(notepadWindow) == JFileChooser.APPROVE_OPTION){
            File file = chooser.getSelectedFile();
            documentModel.setFile(file);
            documentModel.setModified(false);
            try{
                String text = FileService.readFile(file);
                documentModel.setText(text);
                notepadWindow.getTextEditorPanel().getTextArea().setText(text);
            } catch (IOException e) {
                JOptionPane.showMessageDialog(notepadWindow,
                        "The file cannot be opened",
                        "Open error",
                        JOptionPane.ERROR_MESSAGE);
            }
        }

        System.out.println("Opening file...");
    }

}
