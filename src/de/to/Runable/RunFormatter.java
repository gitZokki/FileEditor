package de.to.Runable;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;

import de.to.Utils.CaseString;

public class RunFormatter extends AbstractRun {
	
	boolean isInConstructor = false;
	
	public RunFormatter(File file) {
		super(file);
	}
	
	@Override
	public void run() {
		formatterImport();
		formatterSymbols();
			
		openFile();
	}
	
	private void formatterImport() {
		try {
			BufferedReader br = new BufferedReader(new FileReader(file));
			File f = new File(file.getAbsolutePath() + "-copy");
			BufferedWriter bw = new BufferedWriter(new FileWriter(f));
			
			br.lines().forEach(line -> {
				try {
					if(!line.startsWith("import")) {
						bw.append(line);
						bw.newLine();
						return;
					}
					String old = line;
					
					line = CaseString.replaceAll(line, "(\s*(\\{)\s*)", " {");
					line = CaseString.replaceAll(line, "(\s*(\\})\s*)", "} ");
					line = CaseString.replaceAll(line, "(\s*(from)\s*)", " from ");
					line = CaseString.replaceAll(line, "(\s*(import)\s*)", "import ");
					line = CaseString.replaceAll(line, "(\s*(;)\s*)", ";");
					line = CaseString.replaceAll(line, "(\s*(,)\s*)", ", ");
					
					if(old.contentEquals(line)) { edited = file; }
					
					bw.append(line);
					bw.newLine();
				} catch(Exception e) {
					e.printStackTrace();
				}
			});
			bw.close();
			br.close();
			file.delete();
			f.renameTo(file);
		} catch(Exception e) {
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
					if(line.startsWith("import")) {
						bw.append(line);
						bw.newLine();
						return;
					}
					String old = line;
					
					line = CaseString.replaceAll(line, "(\s*(\\()\s*)", "(");
					
					if(old.contentEquals(line)) { edited = file; }
					
					bw.append(line);
					bw.newLine();
				} catch(Exception e) {
					e.printStackTrace();
				}
			});
			bw.close();
			br.close();
			file.delete();
			f.renameTo(file);
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
}
