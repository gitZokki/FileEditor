package de.to.Listeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JCheckBox;
import javax.swing.JComboBox;

import de.to.FileEditor;
import de.to.Enum.ComponentNames;
import de.to.Enum.EditType;
import de.to.Gui.Gui;
import de.to.Utils.Finder.ComponentsFinder;

public class Listeners {

    public ActionListener getOptionsBoxListener() {
	return new ActionListener() {
	    @Override
	    public void actionPerformed(ActionEvent e) {
		Gui gui = FileEditor.getGuiInstance();
		JCheckBox openFile = new ComponentsFinder<JCheckBox>()
			.getComponentFromGuiWithName(ComponentNames.CHECK_OPENFILE);
		JComboBox<String> optionsBox = new ComponentsFinder<JComboBox<String>>()
			.getComponentFromGuiWithName(ComponentNames.BOX_OPTIONS);
		openFile.setText("<html>Open edited file</html>");

		switch (EditType.getByDisplay((String) optionsBox.getSelectedItem())) {
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
}
