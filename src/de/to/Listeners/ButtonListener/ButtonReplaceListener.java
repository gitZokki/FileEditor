package de.to.Listeners.ButtonListener;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.lang.management.ManagementFactory;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.swing.JTextField;
import javax.swing.ProgressMonitor;
import javax.swing.UIManager;

import com.sun.management.OperatingSystemMXBean;

import de.to.FileEditor;
import de.to.Enum.ComponentNames;
import de.to.Listeners.Listeners;
import de.to.Runable.RunReplace;
import de.to.Utils.Dialogs.MessageDialog;
import de.to.Utils.Finder.ComponentsFinder;
import de.to.Utils.Finder.FileFinder;

public class ButtonReplaceListener extends Listeners {

    public ActionListener getButtonReplaceListener() {
	return new ActionListener() {
	    @Override
	    public void actionPerformed(ActionEvent e) {
		String find = new ComponentsFinder<JTextField>().getComponentFromGuiWithName(ComponentNames.TEXT_FIND)
			.getText();
		String replace = new ComponentsFinder<JTextField>()
			.getComponentFromGuiWithName(ComponentNames.TEXT_REPLACE).getText();
		if (find.trim().isEmpty()) {
		    MessageDialog.showIsEmptyDialog("The find field is empty!");
		    return;
		}

		List<File> files = FileFinder.getAllFilesFromSelectButton();
		int size = files.size();

		if (size == 0) {
		    MessageDialog.showNotFoundDialog("No files found");
		    return;
		}
		if (size > 500000) {
		    MessageDialog.showToMuchFilesDialog("To much files found(" + size + "/500000)");
		    return;
		}

		System.out.println("Files count: " + size);

		long start = System.currentTimeMillis();
		UIManager.put("ProgressMonitor.progressText", "Find in files");
		new Thread(() -> {
		    ProgressMonitor pm = new ProgressMonitor(FileEditor.getGuiInstance().getContentPane(),
			    "Replacing...", "Task starting", 0, size - 1);

		    pm.setMillisToDecideToPopup(100);
		    pm.setMillisToPopup(100);
		    for (int i = 1; i < size; i++) {
			pm.setNote("File: " + i);
			pm.setProgress(i);

			OperatingSystemMXBean o = (OperatingSystemMXBean) ManagementFactory.getOperatingSystemMXBean();
			if (o.getSystemCpuLoad() == 1d) {
			    try {
				TimeUnit.MILLISECONDS.sleep(50);
			    } catch (InterruptedException e1) {
				e1.printStackTrace();
			    }
			}
			new Thread(new RunReplace(files.get(i), find, replace)).start();
		    }
		    pm.setNote("Task finished");
		    System.out.println("Time for replacing: " + (System.currentTimeMillis() - start));
		}).start();
	    }
	};
    }
}
