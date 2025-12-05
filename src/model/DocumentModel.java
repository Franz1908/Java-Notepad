package model;

import java.io.File;

/**
 * Model class representing the current document state.
 * Tracks the document's text content, file location, and modification status.
 */
public class DocumentModel {

    private String text;
    private boolean modified;
    private File file;
    private String fileName;

    /**
     * Constructor that initializes a new empty document.
     */
    public DocumentModel() {
        this.text = "";
        this.modified = false;
        this.file = null;
        this.fileName = "";
    }

    /**
     * Sets the document text content.
     *
     * @param text the new text content
     */
    public void setText(String text) {
        this.text = text;
    }

    /**
     * Gets the current document text content.
     *
     * @return the document text
     */
    public String getText() {
        return this.text;
    }

    /**
     * Sets the file associated with this document.
     *
     * @param file the file object
     */
    public void setFile(File file) {
        this.file = file;
    }

    /**
     * Gets the file associated with this document.
     *
     * @return the file object, or null if no file is associated
     */
    public File getFile() {
        return this.file;
    }

    /**
     * Sets the file name.
     *
     * @param fileName the file name
     */
    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    /**
     * Gets the file name.
     *
     * @return the file name
     */
    public String getFileName() {
        return this.fileName;
    }

    /**
     * Sets whether the document has been modified since last save.
     *
     * @param isModified true if modified, false otherwise
     */
    public void setModified(boolean isModified) {
        this.modified = isModified;
    }

    /**
     * Checks if the document has been modified since last save.
     *
     * @return true if modified, false otherwise
     */
    public boolean isModified() {
        return this.modified;
    }
}
