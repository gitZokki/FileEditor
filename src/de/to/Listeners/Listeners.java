package de.to.Listeners;

import java.awt.Component;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileFilter;
import java.util.List;
import java.util.Stack;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;

import de.to.FileEditor;
import de.to.Enum.ComponentNames;
import de.to.Enum.EditType;
import de.to.Gui.Gui;
import de.to.Utils.Optionals;

public class Listeners {
	
	public ActionListener getOptionsBoxListener(JComboBox<String> optionsBox) {
		return new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Gui gui = FileEditor.getGuiInstance();
				JCheckBox openFile = (JCheckBox) getComponentFromGuiWithName(ComponentNames.CHECK_OPENFILE);
				openFile.setText("<html>Open edited file</html>");
				
				switch(EditType.getByDisplay((String) optionsBox.getSelectedItem())) {
					case REPLACE:
						gui.addReplaceComponents();
						break;
					
					case FIND:
						openFile.setText("<html>Open founded files</html>");
						gui.addFindComponents();
						break;
					
					case FORMATTER:
						gui.addFormatterComponents();
						break;
					
					default:
						break;
				}
			}
		};
	}
	
	protected List<File> getAllFiles() {
		return getAllFiles(new File(((JButton) getComponentFromGuiWithName(ComponentNames.BTN_FILESELECT)).getText()));
	}
	
	protected Component getComponentFromGuiWithName(ComponentNames name) {
		return getComponentFromGuiWithName(name.getName());
	}
	
	private Component getComponentFromGuiWithName(String name) {
		Gui gui = FileEditor.getGuiInstance();
		return getComponentWithName(gui.getContentPane(), name);
	}
	
	private Component getComponentWithName(Container container, String name) {
		Component com = null;
		for(Component comp : container.getComponents()) {
			if(com != null) { break; }
			if(comp.getName() != null) { if(comp.getName().contentEquals(name)) { return comp; } }
			if(comp instanceof Container) { com = getComponentWithName((Container) comp, name); }
		}
		return com;
	}
	
	private List<File> getAllFiles(File file) {
		List<File> fileStack = new Stack<File>();
		
		file.listFiles(new FileFilter() {
			@Override
			public boolean accept(File pathname) {
				Optionals optionals = Optionals.getInstance();
				String fileEnding = optionals.getFileEnding();
				String fileDontContain = optionals.getFileNameNotContain();
				
				if(fileDontContain == null) { fileDontContain = "*?<>\\|:/\""; }
				if(fileEnding == null) { fileEnding = ""; }
				
				if(pathname.isFile() && !pathname.getName().contains(fileDontContain) && pathname.getName().endsWith(fileEnding)) {
					fileStack.add(pathname);
					return false;
				} else {
					fileStack.addAll(getAllFiles(pathname));
					return true;
				}
			}
		});
		return fileStack;
	}
}
