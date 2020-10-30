package de.to.Runable;

import java.io.File;
import java.util.Stack;

public class RunFormatter implements Runnable{

	Stack<File> files;
	
	boolean constructor = false;
	
	public RunFormatter(Stack<File> files) {
		this.files = files;
	}
	
	@Override
	public void run() {
		formatterVariabelOfFile();
	}

	private void formatterVariabelOfFile() {
	}
	
}
