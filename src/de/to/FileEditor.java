package de.to;

import de.to.Gui.Gui;

public class FileEditor {

    private static Gui guiInstance;

    public static synchronized Gui getGuiInstance() {
	return guiInstance;
    }

    public static void main(String[] args) {
	new FileEditor();
    }

    public FileEditor() {
	guiInstance = new Gui("FileEditor", 500, 350);
    }
}
