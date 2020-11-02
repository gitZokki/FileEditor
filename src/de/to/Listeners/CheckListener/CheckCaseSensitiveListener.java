package de.to.Listeners.CheckListener;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JCheckBox;

import de.to.Listeners.Listeners;
import de.to.Utils.Optionals;

public class CheckCaseSensitiveListener extends Listeners {
	
	public ActionListener getCheckBoxCaseSensitiveListener(JCheckBox box) {
		return new ActionListener() {
			Optionals optionals = Optionals.getInstance();
			
			@Override
			public void actionPerformed(ActionEvent e) {
				optionals.setCaseSensitive(box.isSelected());
			}
		};
	}
}
