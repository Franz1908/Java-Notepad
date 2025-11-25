package view;

import javax.swing.*;
import java.awt.*;

/**
 * Represents the application's top menu bar (File, Edit, Format).
 * This class uses a JPanel wrapper to keep layout flexible,
 * but it could also extend JMenuBar directly.
 */
public class AppMenuBar extends JPanel {

    private JMenuBar menuBar;

    public AppMenuBar() {
        // Create the menu bar
        menuBar = new JMenuBar();
        setLayout(new BorderLayout());

        // --- Top-level menus ---
        JMenu fileMenu = new JMenu("File");
        JMenu editMenu = new JMenu("Edit");
        JMenu formatMenu = new JMenu("Format");

        // Add menus to the menu bar
        menuBar.add(fileMenu);
        menuBar.add(editMenu);
        menuBar.add(formatMenu);

        // --- File menu items ---
        JMenuItem menuItemSave = new JMenuItem("Save");
        JMenuItem menuItemSaveAs = new JMenuItem("Save as");
        JMenuItem menuItemOpenFile = new JMenuItem("Open file");

        fileMenu.add(menuItemSave);
        fileMenu.add(menuItemSaveAs);
        fileMenu.add(menuItemOpenFile);

        // --- Edit menu items ---
        JMenuItem menuItemUndo = new JMenuItem("Undo");
        JMenuItem menuItemRedo = new JMenuItem("Redo");

        editMenu.add(menuItemUndo);
        editMenu.add(menuItemRedo);

        // --- Format menu submenus ---
        JMenu fontMenu = new JMenu("Font");
        JMenu fontSizeMenu = new JMenu("Font size");

        formatMenu.add(fontSizeMenu);
        formatMenu.add(fontMenu);

        // Add the menu bar into this panel
        add(menuBar, BorderLayout.NORTH);
    }
}

