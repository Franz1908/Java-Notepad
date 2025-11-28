package model;

import java.io.File;

public class DocumentModel {
    private String text;
    private boolean modified;
    private File file;
    private String fileName;

    public DocumentModel(){
        this.text = "";
        this.modified = false;
        this.file = null;
        this.fileName = "";
    }

    public void setText(String text){
        this.text = text;
    }

    public String getText(){
        return this.text;
    }

    public void setFile(File file){
        this.file = file;
    }

    public File getFile(){
        return this.file;
    }

    public void setFileName(String fileName){
        this.fileName = fileName;
    }

    public String getFileName(){
        return this.fileName;
    }

    public void setModified(boolean isModified){
        this.modified = isModified;
    }

    public boolean isModified(){
        return this.modified;
    }

}
