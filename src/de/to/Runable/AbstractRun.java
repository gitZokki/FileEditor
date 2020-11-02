package de.to.Runable;

import java.awt.Desktop;
import java.io.File;

import de.to.Utils.Optionals;

public class AbstractRun implements Runnable {
	
	File file;
	File edited;
	
	public AbstractRun(File file) {
		this.file = file;
	}
	
	@Override
	public void run() {}
	
	protected void openFile() {
		if(!Optionals.getInstance().isOpenFile()) { return; }
		
		if(Desktop.isDesktopSupported()) {
			try {
				Desktop.getDesktop().open(edited);
			} catch(Exception e) {
				//e.printStackTrace();
			}
		} else {
			System.err.println("Desktop isn't supported");
		} ;
	}
}
