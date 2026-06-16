package view;

import java.awt.*;
import javax.swing.*;

/**
 * Represents the application's top menu bar (File, Edit, Format).
 * This class uses a JPanel wrapper to keep layout flexible and
 * organizes all menu items and submenus for the application.
 */
public class AppMenuBar extends JPanel {

    private JMenuBar menuBar;

    /** Available font families offered in the Format > Font submenu. */
    private static final String[] FONT_NAMES = {
        "Monospaced",
        "SansSerif",
        "Serif",
        "Dialog",
        "Arial",
        "Courier New",
    };

    /** Available font sizes offered in the Format > Font size submenu. */
    private static final int[] FONT_SIZES = {
        10,
        12,
        14,
        16,
        18,
        20,
        24,
        28,
        32,
    };

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

        // Populate Font submenu with selectable font families
        for (String fontName : FONT_NAMES) {
            JMenuItem fontItem = new JMenuItem(fontName);
            fontMenu.add(fontItem);
        }

        // Populate Font size submenu with selectable sizes
        for (int size : FONT_SIZES) {
            JMenuItem sizeItem = new JMenuItem(String.valueOf(size));
            fontSizeMenu.add(sizeItem);
        }

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

    /**
     * Gets the Font size submenu (Format menu, index 0).
     *
     * @return the Font size submenu
     */
    public JMenu getFontSizeMenu() {
        return (JMenu) getFormatMenu().getItem(0);
    }

    /**
     * Gets the Font submenu (Format menu, index 1).
     *
     * @return the Font submenu
     */
    public JMenu getFontMenu() {
        return (JMenu) getFormatMenu().getItem(1);
    }
}
