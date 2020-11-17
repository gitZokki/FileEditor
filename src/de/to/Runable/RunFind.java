package de.to.Runable;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

import de.to.Utils.ZString;

public class RunFind extends AbstractRun {

    String find;

    public RunFind(File file, String find) {
	super(file);
	this.find = ZString.checkCaseSens(find);
    }

    @Override
    public void run() {
	findInFile();

	openFile();
    }

    private void findInFile() {
	try {
	    BufferedReader br = new BufferedReader(new FileReader(file));

	    String string;
	    while ((string = br.readLine()) != null) {
		if (ZString.checkCaseSens(string).contains(find)) {
		    edited = file;
		    break;
		}
	    }

	    br.close();
	} catch (Exception e) {
	    e.printStackTrace();
	}
    }
}
