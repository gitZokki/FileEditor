package de.to.Listeners.ButtonListener;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import de.to.Listeners.Listeners;

public class ButtonFormatterListener extends Listeners {
	
	public ActionListener getButtonFormatterListener() {
		return new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println("ButtonReplaceListener.getButtonFormatterListener() not implemented");
			}
		};
	}
}
