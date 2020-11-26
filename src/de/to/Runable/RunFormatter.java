package de.to.Runable;

import java.nio.file.Path;
import java.util.Arrays;

public class RunFormatter extends AbstractRun {

    private final String lineSeperator = System.getProperty("line.separator");
    private String className = null;
    private String line = "";

    private String constructorLine = "";

    private int inConstructor = 0;

    public RunFormatter(Path path) {
	super(path);
	initReaderAndWriter();
    }

    @Override
    public void run() {
	br.lines().forEach(line -> {
	    if (line.matches("[\s\t]*console.log\\(.*[);]")) {
		return;
	    }

	    if (line.matches("(export [a-zA-Z ]*class .*\\{)")) {
		String[] lineArray = line.split("[ <]");
		className = lineArray[Arrays.asList(lineArray).indexOf("class") + 1];
	    }

	    final String old = line;
	    this.line = line;

	    anyFormatter();

	    if (line.startsWith("import")) {
		importFormatter();
	    } else if (!line.startsWith("//")) {
		if (!otherFormatter()) {
		    return;
		}
	    }

	    checkAndSetEdited(old, line);
	    append();
	});

	closeReaderAndWriter();
	openFile();
    }

    private void append() {
	try {
	    bw.append(line);
	    bw.newLine();
	} catch (Exception e) {
	    e.printStackTrace();
	}
    }

    private void anyFormatter() {
	if (!line.contains("return val.replace(/[")) {
	    line = line.replaceAll("\"", "'");
	}
	if (inConstructor == 0) {
	    line = line.replaceAll("  ", "\t");
	    line = line.replaceAll("\t\tpublic", "\tpublic");
	    line = line.replaceAll("\t\tprivat", "\tprivat");
	    line = line.replaceAll("\t\tprotected", "\tprotected");
	    line = line.replaceAll("\t\tconstructor", "\tconstructor");
	}
    }

    private void importFormatter() {
	formatterImport();
    }

    private boolean otherFormatter() {
	formatterSymbols();
	formatterVariables();
	formatterLogging();

	if (line.contains("constructor(") || inConstructor != 0) {
	    if (formatterConstructor()) {
		return false;
	    }
	}

	return true;
    }

    private void formatterImport() {
	line = line.replaceAll("(\s*(\\{)\s*)", " {");
	line = line.replaceAll("(\s*(\\})\s*)", "} ");
	line = line.replaceFirst("(\s*(from)\s*)", " from ");
	line = line.replaceAll("(\s*(import)\s*)", "import ");
	line = line.replaceAll("(\s*(;))", ";");
	line = line.replaceAll("(\s*(,)\s*)", ", ");

	if (line.endsWith("'")) {
	    line += ";";
	}
    }

    private void formatterSymbols() {
	if (!line.contains(">") && !line.contains("<") && !line.contains("!")) {
	    if (line.contains("===")) {
		line = line.replaceAll("(\s*(===)\s*)", " === ");
	    } else if (line.contains("==")) {
		line = line.replaceAll("(\s*(==)\s*)", " === ");
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
	    line = line.replace("(\s*[:]\s*)", ": ");
	}

	line = line.replaceAll("([\\(]\s*)", "(");
	line = line.replaceAll("(\s*[;]\s*)", ";");
	line = line.replaceAll("([\\[]\s*[\\]])", "[]");
    }

    private void formatterVariables() {
	final String GlobalAppDataProvider = "globalAppData";
	final String LocalAppDataProvider = "localAppData";
	final String UserDataProvider = "userDataService";
	final String NetworkStatusServiceProvider = "networkStatus";
	final String AppEventsProvider = "appEvents";
	final String MessageTranslationProvider = "translator";

	line = line.replaceAll("__globalAppDataProvider", GlobalAppDataProvider);
	line = line.replaceAll("_globalAppDataProvider", GlobalAppDataProvider);
	line = line.replaceAll("globalAppDataProvider", GlobalAppDataProvider);
	line = line.replaceAll("globalData", GlobalAppDataProvider);

	line = line.replaceAll("__localAppDataProvider", LocalAppDataProvider);
	line = line.replaceAll("_localAppDataProvider", LocalAppDataProvider);
	line = line.replaceAll("localAppDataProvider", LocalAppDataProvider);
	line = line.replaceAll("localData", LocalAppDataProvider);

	line = line.replaceAll("userData", UserDataProvider);

	line = line.replaceAll("networkStatusService", NetworkStatusServiceProvider);
	line = line.replaceAll("_networkStatusServiceProvider", NetworkStatusServiceProvider);
	line = line.replaceAll("_networkStatusService", NetworkStatusServiceProvider);
	line = line.replaceAll("_networkStatusProvider", NetworkStatusServiceProvider);
	line = line.replaceAll("networkStatusServiceProvider", NetworkStatusServiceProvider);
	line = line.replaceAll("networkService", NetworkStatusServiceProvider);

	line = line.replaceAll("_appEventsProvider", AppEventsProvider);
	line = line.replaceAll("appEventsProvider", AppEventsProvider);
	line = line.replaceAll("eventService", AppEventsProvider);

	line = line.replaceAll("messageTranslationProvider", MessageTranslationProvider);
	line = line.replaceAll("translationProvider", MessageTranslationProvider);
	line = line.replaceAll("translationService", MessageTranslationProvider);
    }

    private void formatterLogging() {
	if (className != null && !line.contains("alert") && !line.contains("reject(") && !line.contains("resolved(")
		&& !line.contains("resolve(") && !line.contains("super(") && !line.contains("catch(")
		&& !line.contains("Timeout(") && !line.contains("switch(") && !line.contains("constructor(")
		&& !line.contains("Change(")) {

	    if (line.matches("([\s\ta-zA-Z]*\s*[a-zA-Z<T>]{4,}\\(\\).*\\{)")) {
		line = line + lineSeperator + "\t\tconsole.log('" + className + " -> "
			+ line.trim().substring(0, line.indexOf("(")) + ")');";
	    } else if (line.matches("([\s\t]*[a-zA-Z]*\s*[a-zA-Z<T>]{4,}\\(.*\\).*\\{)")) {
		line = line.replaceAll("\s*(:)\s*", ": ");
		if (!line.substring(line.indexOf("("), line.indexOf(")")).contains(":")) {
		    line = line.replace(")", ": any)");
		    line = line.replaceAll(",", ": any,");
		}
		String[] lines = line.substring(line.indexOf("("), line.indexOf(")")).split(",");
		line = line.replaceFirst("[\t\s]*", "\t") + System.getProperty("line.separator") //
			+ "\t\tconsole.log('" + className + " -> " //
			+ line.trim().substring(0, line.indexOf("(")) + ")";

		try {
		    String vars = " [";
		    for (int i = 0; i < lines.length; i++) {
			vars += lines[i].substring(lines[i].contains("(") ? lines[i].indexOf("(") + 1 : 0,
				lines[i].contains(":") ? lines[i].indexOf(":") : lines[i].length());
			if (i < lines.length - 1) {
			    vars += ", ";
			} else {
			    vars += "]";
			}
		    }
		    line += vars + "', " + vars + ");";
		} catch (Exception e) {
		    line += "');";
		}
	    }

	}
    }

    private boolean formatterConstructor() {
	inConstructor = inConstructor == 2 ? 2 : 1;
	constructorLine += line.replaceAll("(\\/\\/.*)", "") + lineSeperator;
	//FIXME
	if (line.endsWith("{")) {
	    inConstructor = 2;

	    constructorLine = "\t" + constructorLine.replaceAll("[\t\s\r\n]+", "");

	    if (!constructorLine.contains("protectedconstructor(")) {
		constructorLine = constructorLine.replaceAll("public", "\t\t\t\tpublic ");
		constructorLine = constructorLine.replaceAll("private", "\t\t\t\tprivate ");
		constructorLine = constructorLine.replaceAll("protected", "\t\t\t\tprotected ");
		constructorLine = constructorLine.replaceAll(",", "," + lineSeperator);
		constructorLine = constructorLine.replaceAll("\\{", "{");
		constructorLine = constructorLine.replaceAll("\\}", "\t}");
		constructorLine = constructorLine.replaceAll(":", ": ");
		constructorLine = constructorLine.replaceAll("\\)", ") ");
		constructorLine = constructorLine.replaceFirst("(\\([\s\t]+public)", "(public");
		constructorLine = constructorLine.replaceFirst("(\\([\s\t]+private)", "(private");
		constructorLine = constructorLine.replaceFirst("(\\([\s\t]+protected)", "(protected");
	    } else {
		constructorLine = constructorLine.replaceAll("private", "\t\t\t\t\t\t  private ");
		constructorLine = constructorLine.replaceAll("public", "\t\t\t\t\t\t  public ");
		constructorLine = constructorLine.replaceAll("protected", "\t\t\t\t\t\t  protected ");
		constructorLine = constructorLine.replaceAll(",", "," + lineSeperator);
		constructorLine = constructorLine.replaceAll("\\{", "{");
		constructorLine = constructorLine.replaceAll("\\}", "\t}");
		constructorLine = constructorLine.replaceAll(":", ": ");
		constructorLine = constructorLine.replaceAll("\\)", ") ");
		constructorLine = constructorLine.replaceFirst("[\s\t]+\\([\s\t]+", "(");
		constructorLine = constructorLine.replaceFirst("([\s\t]+protected)", "\tprotected");
		constructorLine = constructorLine.replaceFirst("(\\([\s\t]+protected)", "(protected");
	    }

	    if (line.endsWith("}")) {
		line = constructorLine;
		return false;
	    }
	}
	return true;
    }
}
