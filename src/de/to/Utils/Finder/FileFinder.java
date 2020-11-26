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

public class FileFinder {

    public static List<Path> getAllFilesFromSelectButton() {
	Path path = new File(
		new ComponentsFinder<JButton>().getComponentFromGuiWithName(ComponentNames.BTN_FILESELECT).getText())
			.toPath();
	Optionals optionals = Optionals.getInstance();
	String ending = optionals.getFileEnding();
	String dont = optionals.getFileNameNotContain();

	if (ending.isEmpty() && dont.isEmpty()) {
	    return getAllFiles(path);
	} else if (!ending.isEmpty() && !dont.isEmpty()) {
	    return getAllFilesWithBoth(path, ending, dont);
	} else if (ending.isEmpty()) {
	    return getAllFilesWithDontContain(path, dont);
	} else {
	    return getAllFilesWithEnding(path, ending);
	}
    }

    public static List<Path> getAllFilesForFormatting() {
	Path path = new File(
		new ComponentsFinder<JButton>().getComponentFromGuiWithName(ComponentNames.BTN_FILESELECT).getText())
			.toPath();

	return getAllFilesWithBoth(path, ".ts", "src\\mocks\\");
    }

    private static List<Path> getAllFiles(Path path) {
	try (Stream<Path> walk = Files.walk(path)) {
	    return walk.filter(Files::isRegularFile).collect(Collectors.toList());
	} catch (IOException e) {
	    e.printStackTrace();
	}
	return null;
    }

    private static List<Path> getAllFilesWithEnding(Path path, String fileEnding) {
	try (Stream<Path> walk = Files.walk(path)) {
	    return walk.filter(p -> p.toFile().isFile() && p.toString().endsWith(fileEnding))
		    .collect(Collectors.toList());
	} catch (IOException e) {
	    e.printStackTrace();
	}
	return null;
    }

    private static List<Path> getAllFilesWithDontContain(Path file, String dontContain) {
	try (Stream<Path> walk = Files.walk(file)) {
	    return walk.filter(p -> p.toFile().isFile() && !p.toString().contains(dontContain))
		    .collect(Collectors.toList());
	} catch (IOException e) {
	    e.printStackTrace();
	}
	return null;
    }

    private static List<Path> getAllFilesWithBoth(Path path, String fileEnding, String dontContain) {
	try (Stream<Path> walk = Files.walk(path)) {
	    return walk.filter(p -> {
		String name = p.toString();
		return p.toFile().isFile() && name.endsWith(fileEnding) && !name.contains(dontContain);
	    }).collect(Collectors.toList());
	} catch (IOException e) {
	    e.printStackTrace();
	}
	return null;
    }
}
