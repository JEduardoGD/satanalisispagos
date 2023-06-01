package mx.egd.sat.descargopagoanalizer.util;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class FileUtil {
	
	public static Path getPathOf(String sPath) {
		Path path = Paths.get(sPath);
		return path;
	}
	
	public static List<File> getXmlFileList(Path path) {
		File[] files = path.toFile().listFiles();
		List<File> filesList = Arrays.asList(files);
		return filesList.stream()
				.filter(f -> f.getName().toLowerCase().endsWith("xml"))
				.collect(Collectors.toList());
	}
}
