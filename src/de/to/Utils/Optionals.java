package de.to.Utils;

public class Optionals {

    private static Optionals instance;

    private String fileEnding;
    private String fileNameNotContain;

    private boolean openFile;

    public Optionals() {
    }

    public static Optionals getInstance() {
	if (instance == null) {
	    instance = new Optionals();
	    instance.setOpenFile(false);
	    instance.setFileEnding("");
	    instance.setFileNameNotContain("");
	}
	return instance;
    }

    public boolean isOpenFile() {
	return openFile;
    }

    public void setOpenFile(boolean openFile) {
	this.openFile = openFile;
    }

    public String getFileEnding() {
	return fileEnding;
    }

    public void setFileEnding(String fileEnding) {
	this.fileEnding = fileEnding;
    }

    public String getFileNameNotContain() {
	return fileNameNotContain;
    }

    public void setFileNameNotContain(String fileNameNotContain) {
	this.fileNameNotContain = fileNameNotContain;
    }
}
