package de.to.Utils;

import javax.swing.JComponent;

import de.to.Enum.ComponentNames;

public class GetComponentWithName<T extends JComponent> {

    private T value;

    public GetComponentWithName(T comp) {
	this(comp, (String) null);
    }

    public GetComponentWithName(T comp, ComponentNames name) {
	this(comp, name.getName());
    }

    public GetComponentWithName(T comp, String name) {
	value = comp;
	setName(name);
    }

    public void setName(String name) {
	value.setName(name);
    }

    public void setName(ComponentNames name) {
	value.setName(name.getName());
    }

    public T build() {
	return value;
    }
}
