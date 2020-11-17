package de.to.Utils.Dialogs;

import javax.swing.JOptionPane;

import de.to.FileEditor;

public class MessageDialog {

    public static void showNotFoundDialog(Object message) {
	JOptionPane.showMessageDialog(FileEditor.getGuiInstance(), message, "Not found", JOptionPane.PLAIN_MESSAGE);
    }

    public static void showIsEmptyDialog(Object message) {
	JOptionPane.showMessageDialog(FileEditor.getGuiInstance(), message, "Empty", JOptionPane.PLAIN_MESSAGE);
    }

    public static void showToMuchFilesDialog(Object message) {
	JOptionPane.showMessageDialog(FileEditor.getGuiInstance(), message, "To much files", JOptionPane.PLAIN_MESSAGE);
    }
}
