package de.to.Runable;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

import de.to.Utils.CaseString;

public class RunFind extends AbstractRun {
	
	String find;
	
	public RunFind(File file, String find) {
		super(file);
		this.find = find;
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
			while((string = br.readLine()) != null) {
				if(CaseString.checkCaseSens(string).contains(CaseString.checkCaseSens(find))) {
					edited = file;
					break;
				}
				if(edited != null) {
					System.out.println("ERROR");
				}
			}
			br.close();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
}
