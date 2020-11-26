package de.to.Runable;

import java.awt.Desktop;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.nio.file.Path;

import de.to.Utils.Optionals;

public class AbstractRun implements Runnable {

    protected BufferedReader br;
    protected BufferedWriter bw;

    private File newFile;
    private File file;

    protected Path path;
    protected Path edited;

    public AbstractRun(Path path) {
	this.path = path;
	file = path.toFile();
    }

    @Override
    public void run() {
    }

    protected void openFile() {
	if (!Optionals.getInstance().isOpenFile() || edited == null) {
	    return;
	}

	if (Desktop.isDesktopSupported()) {
	    try {
		Desktop.getDesktop().open(new File(edited.toString()));
	    } catch (Exception e) {
		e.printStackTrace();
	    }
	} else {
	    System.err.println("Desktop isn't supported");
	}
    }

    protected void initReaderAndWriter() {
	initReader();
	initWriter();
    }

    protected void initReader() {
	try {
	    br = new BufferedReader(new FileReader(file));
	} catch (Exception e) {
	    e.printStackTrace();
	}
    }

    protected void initWriter() {
	try {
	    newFile = new File(file.getAbsoluteFile() + "-copy");
	    bw = new BufferedWriter(new FileWriter(newFile));
	} catch (Exception e) {
	    e.printStackTrace();
	}
    }

    protected void closeReaderAndWriter() {
	closeReder();
	closeWriter();
    }

    protected void closeReder() {
	try {
	    br.close();
	} catch (Exception e) {
	    e.printStackTrace();
	}
    }

    protected void closeWriter() {
	try {
	    bw.close();
	    file.delete();
	    newFile.renameTo(file);
	} catch (Exception e) {
	    e.printStackTrace();
	}
    }

    protected void checkAndSetEdited(String oldLine, String newLine) {
	if (!oldLine.contentEquals(newLine)) {
	    setEdited();
	}
    }

    protected void setEdited() {
	edited = path;
    }
}
