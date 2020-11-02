package de.to.Runable;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;

import de.to.Utils.CaseString;

public class RunReplace extends AbstractRun {
	
	String toReplace, replacement;
	
	public RunReplace(File file, String toReplace, String replacement) {
		super(file);
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
			BufferedReader br = new BufferedReader(new FileReader(file));
			File f = new File(file.getAbsolutePath() + "-copy");
			BufferedWriter bw = new BufferedWriter(new FileWriter(f));
			
			br.lines().forEach(line -> {
				try {
					String old = line;
					
					line = CaseString.replaceAll(line, toReplace, replacement);
					
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
