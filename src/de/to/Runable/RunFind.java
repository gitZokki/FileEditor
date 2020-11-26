package de.to.Runable;

import java.nio.file.Path;

public class RunFind extends AbstractRun {

    String find;

    public RunFind(Path path, String find) {
	super(path);
	initReader();
	this.find = find;
    }

    @Override
    public void run() {
	findInFile();
	
	closeReder();
	openFile();
    }

    private void findInFile() {
	try {
	    String string;
	    while ((string = br.readLine()) != null) {
		if (string.contains(find)) {
		    edited = path;
		    break;
		}
	    }

	    br.close();
	} catch (Exception e) {
	    e.printStackTrace();
	}
    }
}
