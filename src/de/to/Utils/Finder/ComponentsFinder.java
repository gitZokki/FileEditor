package de.to.Utils.Finder;

import java.awt.Component;
import java.awt.Container;

import javax.swing.JComponent;

import de.to.FileEditor;
import de.to.Enum.ComponentNames;
import de.to.Gui.Gui;

public class ComponentsFinder<T extends JComponent> {

    public T getComponentFromGuiWithName(ComponentNames name) {
	return getComponentFromGuiWithName(name.getName());
    }

    private T getComponentFromGuiWithName(String name) {
	Gui gui = FileEditor.getGuiInstance();
	return getComponentWithName(gui.getContentPane(), name);
    }

    @SuppressWarnings("unchecked")
    private T getComponentWithName(Container container, String name) {
	T com = null;
	for (Component comp : container.getComponents()) {
	    if (com != null) {
		break;
	    }
	    if (comp.getName() != null) {
		if (comp.getName().contentEquals(name)) {
		    return (T) comp;
		}
	    }
	    if (comp instanceof Container) {
		com = getComponentWithName((Container) comp, name);
	    }
	}
	return com;
    }
}
