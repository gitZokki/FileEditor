package de.to.Listeners;

import java.awt.Component;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileFilter;
import java.util.Stack;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;

import de.to.FileEditor;
import de.to.Enum.ComponentNames;
import de.to.Enum.EditType;
import de.to.Gui.Gui;
import de.to.Runable.RunFormatter;

public class ActionListeners {
	
	public ActionListener getOptionsBoxListener(JComboBox<String> optionsBox) {
		return new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Gui gui = FileEditor.getGuiInstance();
				JCheckBox openFile = (JCheckBox) getComponentFromGuiWithName(ComponentNames.CHECK_OPENFILE);
				setStandardCheckBox(openFile);
				
				switch(EditType.getByDisplay((String) optionsBox.getSelectedItem())) {
					case REPLACE:
						gui.addReplaceComponents();
						break;
					
					case FIND:
						openFile.setSelected(true);
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
	
	public ActionListener getFilesListener() {
		return new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				JFileChooser chooser = new JFileChooser("C:\\Developement\\git"); // TODO properties
				chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
				chooser.showOpenDialog(null);
				
				File selectedFile = chooser.getSelectedFile();
				if(selectedFile != null) {
					((JButton) getComponentFromGuiWithName(ComponentNames.BTN_FILESELECT)).setText(chooser.getSelectedFile().getAbsolutePath());
					@SuppressWarnings("unchecked")
					JComboBox<String> optionsBox = (JComboBox<String>) getComponentFromGuiWithName(ComponentNames.BOX_OPTIONS);
					optionsBox.setVisible(true);
					optionsBox.setSelectedIndex(0);
					getComponentFromGuiWithName(ComponentNames.PANEL_OPTIONALS).setVisible(true);
				}
			}
		};
	}
	
	public ActionListener getButtonListener(EditType type) {
		return new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				
				switch(type) {
					case REPLACE:
						getAllFiles(null);
						break;
					
					case FIND:
						getAllFiles(null);
						break;
					
					case FORMATTER:
						new Thread(new RunFormatter(getAllFiles(null))).start();
						break;
						
					default:
						break;
				}
			}
		};
	}
	
	private void setStandardCheckBox(JCheckBox openFile) {
		openFile.setText("<html>Open edited file</html>");
		openFile.setSelected(false);
	}
	
	private Component getComponentFromGuiWithName(ComponentNames name) {
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
	
	private Stack<File> getAllFiles(File file) {
		Stack<File> fileStack = new Stack<File>();

		file.listFiles(new FileFilter() {			
			@Override
			public boolean accept(File pathname) {
				if(pathname.isFile()) {
					fileStack.push(pathname);
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
