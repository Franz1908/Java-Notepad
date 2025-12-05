import controller.NotepadController;
import model.DocumentModel;
import view.NotepadWindow;

/**
 * Main entry point for the Notepad application.
 * Initializes the MVC components and starts the application.
 */
public class Main {

    /**
     * Application entry point.
     * Creates the view, model, and controller, establishing connections between them.
     *
     * @param args command line arguments (not used)
     */
    public static void main(String[] args) {
        // Create the main window (View)
        NotepadWindow notepad = new NotepadWindow();

        // Create the document model (Model)
        DocumentModel documentModel = new DocumentModel();

        // Create the controller and wire everything together (Controller)
        NotepadController notepadController = new NotepadController(notepad, documentModel);
    }
}
