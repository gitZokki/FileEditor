package de.to.Listeners.TextListener;

import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;

import javax.swing.JTextField;

import de.to.Enum.ComponentNames;
import de.to.Listeners.Listeners;
import de.to.Utils.Optionals;
import de.to.Utils.Finder.ComponentsFinder;

public class TextFileEndingListener extends Listeners {

    public FocusAdapter getTextFileEndingListener() {
	return new FocusAdapter() {
	    @Override
	    public void focusLost(FocusEvent e) {
		JTextField fileEnding = new ComponentsFinder<JTextField>()
			.getComponentFromGuiWithName(ComponentNames.TEXT_FILEENDING);
		Optionals.getInstance().setFileEnding(fileEnding.getText());
	    }
	};
    }
}
