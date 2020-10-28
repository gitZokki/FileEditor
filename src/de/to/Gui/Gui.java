package de.to.Gui;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;

import de.to.Enum.EditType;

public class Gui extends JFrame{

	private static final long serialVersionUID = 1L;

	private String[] options = EditType.getDisplayString();
	
	public Gui(String name, int width, int height) {
		super(name);
		setSize(width, height);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		setLayout(new GridLayout(7, 0));
		
		initComponents();
		
		setVisible(true);
	}

	private File selectedFile = null;
	private void initComponents() {
		JButton files = new JButton("Select file");
		JComboBox<String> optionsBox = new JComboBox<String>(options); 
		optionsBox.setVisible(false);
		
		optionsBox.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println("Action performed " + optionsBox.getSelectedItem());
				EditType type = EditType.get((String) optionsBox.getSelectedItem());
				switch (type) {
				case REPLACE:
					addReplaceComponents();
					break;

				default:
					break;
				}
			}
		});
		
		files.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				JFileChooser chooser = new JFileChooser("C:\\Developement\\git"); //TODO properties
				chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
				chooser.showOpenDialog(null);
				
				selectedFile = chooser.getSelectedFile();
				if(selectedFile != null) {
					files.setText(chooser.getSelectedFile().getAbsolutePath());
					optionsBox.setVisible(true);
					optionsBox.setSelectedIndex(0);
				}
			}
		});
		
		add(files);
		add(optionsBox);
	}
	
	private void addReplaceComponents() {
		System.out.println("Gui.addReplaceComponents()");
	}
}
