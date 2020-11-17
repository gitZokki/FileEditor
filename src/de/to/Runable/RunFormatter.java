package de.to.Runable;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;

public class RunFormatter extends AbstractRun {

    boolean isInConstructor = false;

    public RunFormatter(File file) {
	super(file);
	System.out.println(file);
    }

    @Override
    public void run() {
	formatterImport();
	formatterSymbols();
	formatterVariables();

	openFile();
    }

    private void formatterImport() {
	try {
	    BufferedReader br = new BufferedReader(new FileReader(file));
	    File f = new File(file.getAbsolutePath() + "-copy");
	    BufferedWriter bw = new BufferedWriter(new FileWriter(f));

	    br.lines().forEach(line -> {
		try {
		    if (!line.startsWith("import")) {
			bw.append(line);
			bw.newLine();
			return;
		    }
		    String old = line;

		    line = line.replaceAll("(\s*(\\{)\s*)", " {");
		    line = line.replaceAll("(\s*(\\})\s*)", "} ");
		    line = line.replaceFirst("(\s*(from)\s*)", " from ");
		    line = line.replaceAll("(\s*(import)\s*)", "import ");
		    line = line.replaceAll("(\s*(;)\s*)", ";");
		    line = line.replaceAll("(\s*(,)\s*)", ", ");

		    if (line.endsWith("\"") || line.endsWith("'")) {
			line += ";";
		    }

		    line = line.replaceAll("\"", "'");

		    if (old.contentEquals(line)) {
			edited = file;
		    }

		    bw.append(line);
		    bw.newLine();
		} catch (Exception e) {
		    e.printStackTrace();
		}
	    });
	    bw.close();
	    br.close();
	    file.delete();
	    f.renameTo(file);
	} catch (Exception e) {
	    e.printStackTrace();
	}
    }

    private void formatterSymbols() {
	try {
	    BufferedReader br = new BufferedReader(new FileReader(file));
	    File f = new File(file.getAbsolutePath() + "-copy");
	    BufferedWriter bw = new BufferedWriter(new FileWriter(f));

	    br.lines().forEach(line -> {
		try {
		    if (line.startsWith("import") || line.startsWith("//")) {
			bw.append(line);
			bw.newLine();
			return;
		    }
		    String old = line;

		    if (!line.contains(">") && !line.contains("<") && !line.contains("!")) {
			if (line.contains("===")) {
			    line = line.replaceAll("(\s*(===)\s*)", " === ");
			} else if (line.contains("==")) {
			    line = line.replaceAll("(\s*(===)\s*)", " == ");
			} else if (line.contains("=")) {
			    line = line.replaceAll("(\s*[=]\s*)", " = ");
			}
		    }

		    if (line.contains(">") || line.contains("<") || line.contains("!")) {
			if (line.contains(">=")) {
			    line = line.replaceAll("(\s*(>=)\s*)", " >= ");
			} else if (line.contains("<=")) {
			    line = line.replaceAll("(\s*(<=)\s*)", " <= ");
			} else if (line.contains("=>")) {
			    line = line.replaceAll("(\s*(=>)\s*)", " => ");
			} else if (line.contains("!==")) {
			    line = line.replaceAll("(\s*(!==)\s*)", " !== ");
			} else if (line.contains("!=")) {
			    line = line.replaceAll("(\s*(!=)\s*)", " !== ");
			}
		    }

		    if (line.contains("public ") || line.contains("protected ") || line.contains("private ")
			    || line.contains("@Input() ") || line.contains("@Output() ")) {
			line = line.replaceAll("(\s*[:]\s*)", ": ");
		    }

		    line = line.replaceAll("([\\(]\s*)", "(");
		    line = line.replaceAll("(\s*[;]\s*)", ";");
		    line = line.replaceAll("([\\[]\s*[\\]])", "[]");

		    line = line.replaceAll("\"", "'");

		    if (old.contentEquals(line)) {
			edited = file;
		    }

		    bw.append(line);
		    bw.newLine();
		} catch (Exception e) {
		    e.printStackTrace();
		}
	    });
	    bw.close();
	    br.close();
	    file.delete();
	    f.renameTo(file);
	} catch (Exception e) {
	    e.printStackTrace();
	}
    }

    private void formatterVariables() {
	try {
	    BufferedReader br = new BufferedReader(new FileReader(file));
	    File f = new File(file.getAbsolutePath() + "-copy");
	    BufferedWriter bw = new BufferedWriter(new FileWriter(f));

	    br.lines().forEach(line -> {
		try {
		    if (line.startsWith("import") || line.startsWith("//")) {
			bw.append(line);
			bw.newLine();
			return;
		    }
		    String old = line;

		    String GlobalAppDataProvider = "globalAppData";
		    String LocalAppDataProvider = "localAppData";
		    String UserDataProvider = "userData";
		    String NetworkStatusServiceProvider = "networkStatus";
		    String AppEventsProvider = "appEvents";
		    String MessageTranslationProvider = "translator";

		    line = line.replaceAll("__globalAppDataProvider", GlobalAppDataProvider);
		    line = line.replaceAll("_globalAppDataProvider", GlobalAppDataProvider);
		    line = line.replaceAll("globalAppDataProvider", GlobalAppDataProvider);
		    line = line.replaceAll("globalData", GlobalAppDataProvider);

		    line = line.replaceAll("__localAppDataProvider", LocalAppDataProvider);
		    line = line.replaceAll("_localAppDataProvider", LocalAppDataProvider);
		    line = line.replaceAll("localAppDataProvider", LocalAppDataProvider);
		    line = line.replaceAll("localData", LocalAppDataProvider);

		    line = line.replaceAll("userDataService", UserDataProvider);

		    line = line.replaceAll("networkStatusService", NetworkStatusServiceProvider);
		    line = line.replaceAll("_networkStatusServiceProvider", NetworkStatusServiceProvider);
		    line = line.replaceAll("_networkStatusProvider", NetworkStatusServiceProvider);
		    line = line.replaceAll("networkStatusServiceProvider", NetworkStatusServiceProvider);
		    line = line.replaceAll("networkService", NetworkStatusServiceProvider);

		    line = line.replaceAll("_appEventsProvider", AppEventsProvider);
		    line = line.replaceAll("appEventsProvider", AppEventsProvider);
		    line = line.replaceAll("eventService", AppEventsProvider);

		    line = line.replaceAll("messageTranslationProvider", MessageTranslationProvider);
		    line = line.replaceAll("translationProvider", MessageTranslationProvider);
		    line = line.replaceAll("translationService", MessageTranslationProvider);

		    if (old.contentEquals(line)) {
			edited = file;
		    }

		    bw.append(line);
		    bw.newLine();
		} catch (Exception e) {
		    e.printStackTrace();
		}
	    });
	    bw.close();
	    br.close();
	    file.delete();
	    f.renameTo(file);
	} catch (Exception e) {
	    e.printStackTrace();
	}
    }
}
