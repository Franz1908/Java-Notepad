import controller.NotepadController;
import model.DocumentModel;
import view.NotepadWindow;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        NotepadWindow notepad = new NotepadWindow();
        DocumentModel documentModel = new DocumentModel();
        NotepadController notepadController = new NotepadController(notepad, documentModel);

    }
}
