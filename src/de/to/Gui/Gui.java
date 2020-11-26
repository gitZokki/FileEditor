package de.to.Gui;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.Border;

import de.to.Enum.ComponentNames;
import de.to.Enum.EditType;
import de.to.Listeners.Listeners;
import de.to.Listeners.ButtonListener.ButtonFilesListener;
import de.to.Listeners.ButtonListener.ButtonFindListener;
import de.to.Listeners.ButtonListener.ButtonFormatterListener;
import de.to.Listeners.ButtonListener.ButtonReplaceListener;
import de.to.Listeners.CheckListener.CheckOpenFileListener;
import de.to.Listeners.TextListener.TextFileDontContainListener;
import de.to.Listeners.TextListener.TextFileEndingListener;
import de.to.Utils.GetComponentWithName;

public class Gui extends JFrame {

    private static final long serialVersionUID = 1L;

    private final int BORDER = 5;

    private JPanel panel = new JPanel();

    public Gui(String name, int width, int height) {
	super(name);
	setSize(width, height);
	setMinimumSize(new Dimension(width, height));
	setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	setLocationRelativeTo(null);
	setLayout(getLayout(2, 0));

	initComponents();

	setVisible(true);
    }

    private void initComponents() {
	JPanel panel = new JPanel();
	panel.setLayout(getLayout(3, 0));

	JButton files = new GetComponentWithName<JButton>(new JButton("Select file"), ComponentNames.BTN_FILESELECT)
		.build();
	JComboBox<String> optionsBox = new GetComponentWithName<JComboBox<String>>(
		new JComboBox<String>(EditType.getDisplayStrings()), ComponentNames.BOX_OPTIONS).build();
	optionsBox.setVisible(false);

	JPanel optionals = new GetComponentWithName<JPanel>(new JPanel(), ComponentNames.PANEL_OPTIONALS).build();
	optionals.setLayout(getLayout(0, 3));
	optionals.setVisible(false);

	JTextField fileEnding = new GetComponentWithName<JTextField>(new JTextField(), ComponentNames.TEXT_FILEENDING)
		.build();
	fileEnding.setBorder(BorderFactory.createTitledBorder("File ending?"));
	fileEnding.addFocusListener(new TextFileEndingListener().getTextFileEndingListener());

	JTextField fileDontContain = new GetComponentWithName<JTextField>(new JTextField(),
		ComponentNames.TEXT_FILEDONTCONTAIN).build();
	fileDontContain.setBorder(BorderFactory.createTitledBorder("Filename don't contain?"));
	fileDontContain.addFocusListener(new TextFileDontContainListener().getTextFileDontContainListener());

	JPanel checkBoxs = new JPanel();
	checkBoxs.setLayout(getLayout(2, 0));

	JCheckBox openFile = new GetComponentWithName<JCheckBox>(new JCheckBox(), ComponentNames.CHECK_OPENFILE)
		.build();
	openFile.addActionListener(new CheckOpenFileListener().getCheckBoxOpenFileListener(openFile));

	checkBoxs.add(openFile);

	optionals.add(fileEnding);
	optionals.add(fileDontContain);
	optionals.add(checkBoxs);

	optionsBox.addActionListener(new Listeners().getOptionsBoxListener());

	files.addActionListener(new ButtonFilesListener().getButtonFilesListener());

	panel.add(files);
	panel.add(optionsBox);
	panel.add(optionals);
	add(panel);
	add(this.panel);

	panel.setBorder(getBorder(BORDER, BORDER, 0, BORDER));
    }

    public void addReplaceComponents() {
	JTextField find = new GetComponentWithName<JTextField>(new JTextField(), ComponentNames.TEXT_FIND).build();
	find.setBorder(BorderFactory.createTitledBorder("Find"));

	JTextField replaceWith = new GetComponentWithName<JTextField>(new JTextField(), ComponentNames.TEXT_REPLACE)
		.build();
	replaceWith.setBorder(BorderFactory.createTitledBorder("Replace with"));

	JButton replace = new JButton("Replace");
	replace.addActionListener(new ButtonReplaceListener().getButtonReplaceListener());

	addCompoents(3, 0, find, replaceWith, replace);
    }

    public void addFindComponents() {
	JTextField text = new GetComponentWithName<JTextField>(new JTextField(), ComponentNames.TEXT_FIND).build();
	text.setBorder(BorderFactory.createTitledBorder("Find"));

	JButton find = new JButton("Find");
	find.addActionListener(new ButtonFindListener().getButtonFindListener());

	addCompoents(2, 0, text, find);
    }

    public void addFormatterComponents() {
	JButton formatter = new JButton("Formatter");
	formatter.addActionListener(new ButtonFormatterListener().getButtonFormatterListener());

	addCompoents(3, 0, Box.createVerticalGlue(), formatter);
    }

    private void addCompoents(int rows, int cols, Component... comps) {
	panel.removeAll();
	panel.setLayout(getLayout(rows, cols));
	panel.setBorder(getBorder(0, BORDER, BORDER, BORDER));

	for (Component component : comps) {
	    panel.add(component);
	}
	revalidate();
    }

    private GridLayout getLayout(int rows, int cols) {
	return getLayout(rows, cols, BORDER, BORDER);
    }

    private GridLayout getLayout(int rows, int cols, int h, int v) {
	GridLayout layout = new GridLayout(rows, cols);
	layout.setHgap(h);
	layout.setVgap(v);
	return layout;
    }

    @SuppressWarnings("unused")
    private List<Component> getAllComponents(Container container) {
	Component[] comps = container.getComponents();
	List<Component> compList = new ArrayList<Component>();
	for (Component comp : comps) {
	    compList.add(comp);
	    if (comp instanceof Container) {
		compList.addAll(getAllComponents((Container) comp));
	    }
	}
	return compList;
    }

    private Border getBorder(int top, int left, int bottom, int right) {
	return BorderFactory.createEmptyBorder(top, left, bottom, right);
    }
}
