package de.to.Runable;

import java.nio.file.Path;

public class RunReplace extends AbstractRun {

    String toReplace, replacement;

    public RunReplace(Path path, String toReplace, String replacement) {
	super(path);
	initReaderAndWriter();
	this.toReplace = toReplace;
	this.replacement = replacement;
    }

    @Override
    public void run() {
	replace();

	openFile();
    }

    private void replace() {
	try {
	    br.lines().forEach(line -> {
		try {
		    String old = line;
		    line = line.replaceAll(toReplace, replacement);

		    if (old.contentEquals(line)) {
			edited = path;
		    }

		    bw.append(line);
		    bw.newLine();
		} catch (Exception e) {
		    e.printStackTrace();
		}
	    });
	} catch (Exception e) {
	    e.printStackTrace();
	}
    }
}
