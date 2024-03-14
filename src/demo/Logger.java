package demo;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

public class Logger {
	String fileName = "test.txt";

	public Boolean checkIfFileExists() {
		File f = new File(fileName);
		if (f.exists() && f.isFile()) {
			return true;
		} else {
			return false;
		}
	}

	public boolean writeToFile(String toWrite) throws IOException {
		if (checkIfFileExists()) {
			Path p = Paths.get(fileName);
			OutputStream out = new BufferedOutputStream(Files.newOutputStream(p, StandardOpenOption.APPEND));
			out.write(toWrite.getBytes());
			out.close();
			return true;
		} else {
			File f = new File(fileName);
			f.createNewFile();
			writeToFile(toWrite);
		}
		return false;
	}

	@SuppressWarnings("finally")
	public String readFileData() throws FileNotFoundException, IOException {
		if (checkIfFileExists()) {
			BufferedReader br = new BufferedReader(new FileReader(fileName));
			String fileData = "";
			try {
				StringBuilder sb = new StringBuilder();
				String line = br.readLine();

				while (line != null) {
					sb.append(line);
					sb.append(System.lineSeparator());
					line = br.readLine();
				}
				fileData = sb.toString();
			} finally {
				br.close();
				return fileData;
			}
		} else {
			throw new FileNotFoundException("File:" + fileName + " does not exist");
		}
	}
}
