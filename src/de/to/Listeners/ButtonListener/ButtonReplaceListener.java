package de.to.Listeners.ButtonListener;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import de.to.Listeners.Listeners;

public class ButtonReplaceListener extends Listeners{
	
	public ActionListener getButtonReplaceListener() {
		return new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println("ButtonReplaceListener.getButtonReplaceListener() not implemented");
			}
		};
	}
}
