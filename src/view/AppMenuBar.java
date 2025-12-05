package view;

import javax.swing.*;
import java.awt.*;

/**
 * Represents the application's top menu bar (File, Edit, Format).
 * This class uses a JPanel wrapper to keep layout flexible and
 * organizes all menu items and submenus for the application.
 */
public class AppMenuBar extends JPanel {

    private JMenuBar menuBar;

    /**
     * Constructor that initializes and configures the menu bar.
     * Creates all menus (File, Edit, Format) and their respective items.
     */
    public AppMenuBar() {
        // Create the menu bar
        menuBar = new JMenuBar();
        setLayout(new BorderLayout());

        // --- Create top-level menus ---
        JMenu fileMenu = new JMenu("File");
        JMenu editMenu = new JMenu("Edit");
        JMenu formatMenu = new JMenu("Format");

        // Add menus to the menu bar
        menuBar.add(fileMenu);
        menuBar.add(editMenu);
        menuBar.add(formatMenu);

        // --- Populate File menu ---
        JMenuItem menuItemSave = new JMenuItem("Save");
        JMenuItem menuItemSaveAs = new JMenuItem("Save as");
        JMenuItem menuItemOpenFile = new JMenuItem("Open file");

        fileMenu.add(menuItemSave);
        fileMenu.add(menuItemSaveAs);
        fileMenu.add(menuItemOpenFile);

        // --- Populate Edit menu ---
        JMenuItem menuItemUndo = new JMenuItem("Undo");
        JMenuItem menuItemRedo = new JMenuItem("Redo");

        editMenu.add(menuItemUndo);
        editMenu.add(menuItemRedo);

        // --- Populate Format menu with submenus ---
        JMenu fontMenu = new JMenu("Font");
        JMenu fontSizeMenu = new JMenu("Font size");

        formatMenu.add(fontSizeMenu);
        formatMenu.add(fontMenu);

        // Add the menu bar to this panel
        add(menuBar, BorderLayout.NORTH);
    }

    /**
     * Gets the internal JMenuBar component.
     *
     * @return the JMenuBar instance
     */
    public JMenuBar getJMenuBar() {
        return this.menuBar;
    }

    /**
     * Gets the File menu.
     *
     * @return the File menu (index 0)
     */
    public JMenu getFileMenu() {
        return this.menuBar.getMenu(0);
    }

    /**
     * Gets the Edit menu.
     *
     * @return the Edit menu (index 1)
     */
    public JMenu getEditMenu() {
        return this.menuBar.getMenu(1);
    }

    /**
     * Gets the Format menu.
     *
     * @return the Format menu (index 2)
     */
    public JMenu getFormatMenu() {
        return this.menuBar.getMenu(2);
    }
}

