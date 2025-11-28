package service;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;

public class FileService {
    private static JFileChooser fileChooser;

    public static void saveFile(){
        fileChooser = new JFileChooser();
        FileNameExtensionFilter filter = new FileNameExtensionFilter("Text Files", "txt");
        fileChooser.addChoosableFileFilter(filter);
        fileChooser.setAcceptAllFileFilterUsed(false);
        fileChooser.showSaveDialog(null);
    }
}
