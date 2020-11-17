package de.to.Utils.Finder;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.swing.JButton;

import de.to.Enum.ComponentNames;
import de.to.Utils.Optionals;
import de.to.Utils.ZString;

public class FileFinder {

    public static List<File> getAllFilesFromSelectButton() {
	File file = new File(
		new ComponentsFinder<JButton>().getComponentFromGuiWithName(ComponentNames.BTN_FILESELECT).getText());
	Optionals optionals = Optionals.getInstance();
	String ending = ZString.checkCaseSens(optionals.getFileEnding());
	String dont = ZString.checkCaseSens(optionals.getFileNameNotContain());

	if (ending.isEmpty() && dont.isEmpty()) {
	    return getAllFiles(file);
	} else if (!ending.isEmpty() && !dont.isEmpty()) {
	    return getAllFilesWithBoth(file, ending, dont);
	} else if (ending.isEmpty()) {
	    return getAllFilesWithDontContain(file, dont);
	} else {
	    return getAllFilesWithEnding(file, ending);
	}
    }

    public static List<File> getAllFilesForFormatting() {
	File file = new File(
		new ComponentsFinder<JButton>().getComponentFromGuiWithName(ComponentNames.BTN_FILESELECT).getText());

	return getAllFilesWithEnding(file, ".ts");
    }

    private static List<File> getAllFiles(File file) {
	try (Stream<Path> walk = Files.walk(file.toPath())) {
	    return walk.filter(Files::isRegularFile).map(x -> x.toFile()).collect(Collectors.toList());
	} catch (IOException e) {
	    e.printStackTrace();
	}
	return null;
    }

    private static List<File> getAllFilesWithEnding(File file, String fileEnding) {
	try (Stream<Path> walk = Files.walk(file.toPath())) {
	    return walk.filter(f -> f.toFile().isFile() && getEndWith(f, fileEnding)).map(x -> x.toFile())
		    .collect(Collectors.toList());
	} catch (IOException e) {
	    e.printStackTrace();
	}
	return null;
    }

    private static List<File> getAllFilesWithDontContain(File file, String dontContain) {
	try (Stream<Path> walk = Files.walk(file.toPath())) {
	    return walk.filter(f -> f.toFile().isFile() && !getContains(f.getFileName(), dontContain))
		    .map(x -> x.toFile()).collect(Collectors.toList());
	} catch (IOException e) {
	    e.printStackTrace();
	}
	return null;
    }

    private static List<File> getAllFilesWithBoth(File file, String fileEnding, String dontContain) {
	try (Stream<Path> walk = Files.walk(file.toPath())) {
	    return walk.filter(
		    f -> f.toFile().isFile() && getEndWith(f, fileEnding) && !getContains(f.getFileName(), dontContain))
		    .map(x -> x.toFile()).collect(Collectors.toList());
	} catch (IOException e) {
	    e.printStackTrace();
	}
	return null;
    }

    private static boolean getContains(Path toCheck, String check) {
	return ZString.checkCaseSens(toCheck.toString()).contains(check);
    }

    private static boolean getEndWith(Path toCheck, String check) {
	return ZString.checkCaseSens(toCheck.toString()).endsWith(check);
    }
}
