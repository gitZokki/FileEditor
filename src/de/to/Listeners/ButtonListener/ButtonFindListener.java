package de.to.Listeners.ButtonListener;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JTextField;

import de.to.Enum.ComponentNames;
import de.to.Listeners.Listeners;
import de.to.Runable.RunFind;

public class ButtonFindListener extends Listeners {
	
	public ActionListener getButtonFindListener() {
		return new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String find = ((JTextField) getComponentFromGuiWithName(ComponentNames.TEXT_FIND)).getText();
				for(File file : getAllFiles()) {
					new Thread(new RunFind(file, find)).run();
				}
			}
		};
	}
}
