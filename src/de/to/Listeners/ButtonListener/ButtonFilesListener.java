package de.to.Listeners.ButtonListener;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JPanel;

import de.to.Enum.ComponentNames;
import de.to.Listeners.Listeners;
import de.to.Utils.Finder.ComponentsFinder;

public class ButtonFilesListener extends Listeners {

    public ActionListener getButtonFilesListener() {
	return new ActionListener() {
	    @Override
	    public void actionPerformed(ActionEvent e) {
		// JFileChooser chooser = new JFileChooser("C:\\Developement\\git"); // TODO
		// properties
		JFileChooser chooser = new JFileChooser("C:\\Developement\\git\\TESTING");
		chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		chooser.showOpenDialog(null);

		File selectedFile = chooser.getSelectedFile();
		if (selectedFile != null) {
		    JButton btnSelect = new ComponentsFinder<JButton>()
			    .getComponentFromGuiWithName(ComponentNames.BTN_FILESELECT);
		    btnSelect.setText(chooser.getSelectedFile().getAbsolutePath());
		    JComboBox<String> optionsBox = new ComponentsFinder<JComboBox<String>>()
			    .getComponentFromGuiWithName(ComponentNames.BOX_OPTIONS);
		    optionsBox.setVisible(true);
		    optionsBox.setSelectedIndex(0);
		    new ComponentsFinder<JPanel>().getComponentFromGuiWithName(ComponentNames.PANEL_OPTIONALS)
			    .setVisible(true);
		}
	    }
	};
    }
}
