package de.to.Listeners.ButtonListener;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;

import de.to.Enum.ComponentNames;
import de.to.Listeners.Listeners;

public class ButtonFilesListener extends Listeners{
	
	public ActionListener getButtonFilesListener() {
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
}
