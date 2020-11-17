package de.to.Listeners.ButtonListener;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.lang.management.ManagementFactory;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.swing.ProgressMonitor;
import javax.swing.UIManager;

import com.sun.management.OperatingSystemMXBean;

import de.to.FileEditor;
import de.to.Listeners.Listeners;
import de.to.Runable.RunFormatter;
import de.to.Utils.Dialogs.MessageDialog;
import de.to.Utils.Finder.FileFinder;

public class ButtonFormatterListener extends Listeners {

    public ActionListener getButtonFormatterListener() {
	return new ActionListener() {
	    @Override
	    public void actionPerformed(ActionEvent e) {
		List<File> files = FileFinder.getAllFilesForFormatting();
		int size = files.size();

		if (size == 0) {
		    MessageDialog.showNotFoundDialog("No files found");
		    return;
		}
		if (size > 500000) {
		    MessageDialog.showToMuchFilesDialog("To much files found(" + size + "/500000)");
		    return;
		}

		System.out.println("size: " + size);

		long start = System.currentTimeMillis();
		UIManager.put("ProgressMonitor.progressText", "Find in files");
		new Thread(() -> {
		    ProgressMonitor pm = new ProgressMonitor(FileEditor.getGuiInstance().getContentPane(),
			    "Searching...", "Task starting", 0, size - 1);

		    pm.setMillisToDecideToPopup(100);
		    pm.setMillisToPopup(100);
		    for (int i = 0; i < size; i++) {
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
			new Thread(new RunFormatter(files.get(i))).start();
		    }
		    pm.setNote("Task finished");
		    System.out.println("Formatting-time: " + (System.currentTimeMillis() - start));
		}).start();
	    }
	};
    }
}
