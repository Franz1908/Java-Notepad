# Notepad

A simple desktop text editor built with Java Swing, organized using the
Model-View-Controller (MVC) architectural pattern. It supports basic file
operations, undo/redo, and customization of the editor font and size.

## Features

- **File operations**: create, open, save, and save-as for `.txt` files, with
  automatic `.txt` extension handling and overwrite confirmation.
- **Undo / Redo**: word-level undo and redo history with keyboard shortcuts.
- **Formatting**: change the editor font family and font size from the
  *Format* menu.
- **Modification tracking**: the window title reflects the current file name
  and marks unsaved changes with a `*`.
- **Word wrapping**: text wraps automatically at word boundaries.

## Keyboard Shortcuts

| Action    | Shortcut   |
|-----------|------------|
| Save      | `Ctrl + S` |
| Save As   | `Ctrl + A` |
| Open      | `Ctrl + O` |
| Undo      | `Ctrl + Z` |
| Redo      | `Ctrl + Y` |

## Project Structure

The application follows the MVC pattern, with each responsibility separated
into its own package:

```
src/
├── Main.java                  # Application entry point; wires MVC together
├── model/
│   └── DocumentModel.java      # Document state: text, file, modified flag
├── view/
│   ├── NotepadWindow.java      # Main JFrame window
│   ├── TextEditorPanel.java    # JTextArea inside a scroll pane
│   └── AppMenuBar.java         # File, Edit, and Format menus
├── service/
│   └── FileService.java        # File read/write I/O
└── controller/
    └── NotepadController.java  # User interaction and view/model coordination
```

- **Model** (`DocumentModel`) holds the document's text content, associated
  file, and modification status.
- **View** (`NotepadWindow`, `TextEditorPanel`, `AppMenuBar`) builds and
  displays the user interface.
- **Controller** (`NotepadController`) listens to user actions, updates the
  model, and keeps the view in sync.
- **Service** (`FileService`) handles reading from and writing to disk.

## Requirements

- Java Development Kit (JDK) 21 or later.

## Build and Run

From the project root, compile the sources and run the application:

```bash
# Compile all sources into the out/ directory
javac -d out src/Main.java src/**/*.java

# Run the application
java -cp out Main
```

If your shell does not expand `src/**/*.java`, compile by listing the
packages explicitly:

```bash
javac -d out src/Main.java src/model/*.java src/view/*.java \
    src/service/*.java src/controller/*.java
java -cp out Main
```

Alternatively, open the project in IntelliJ IDEA (an `.iml` module file is
included) and run the `Main` class directly.

## Usage

1. Launch the application — an empty, untitled document opens.
2. Type your text in the editor area.
3. Use the **File** menu (or shortcuts) to open and save documents.
4. Use the **Edit** menu to undo or redo changes.
5. Use the **Format** menu to pick a font family and size for the editor.

## Notes

- Some font families (e.g. *Arial*, *Courier New*) depend on the fonts
  installed on your system. The logical fonts *Monospaced*, *SansSerif*,
  *Serif*, and *Dialog* are always available across platforms.
- The font and size selections combine: changing one preserves the other.
