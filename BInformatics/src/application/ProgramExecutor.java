package application;

import java.io.IOException;
import java.nio.charset.MalformedInputException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import application_gui.MainController;

public class ProgramExecutor extends Thread {

	private String executable = "";
	private int executionphase = 0;
	private MainController mainController = null;
	private List<String> readAllLines;

	public ProgramExecutor(MainController mainController) {
		this.mainController = mainController;
	}

	public ProgramExecutor() {

	}

	public void setExecutionphase(int i) {
		this.executionphase = i;
	}

	public void run() {
		Process p = null;
		try {
			this.executable = (String) Files.readAllLines(Paths.get("outputconfig.sh")).get(executionphase - 1);
			p = Runtime.getRuntime().exec(executable);
			System.out.println("Waiting for batch file ...");
		} catch (IOException e) {
			e.printStackTrace();
		}
		mainController.setStatusMessage("Programstage " + executable + " running");
		while (p.isAlive()) {
			try {
				sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			mainController.updateProgressBar(getProgress()/1000.0);
		}
		int exitValue = p.exitValue();
		if (exitValue != 0) {
			mainController.setStatusMessage("Program Stage " + executable + " not successful");
		} else {
			mainController.setStatusMessage("Program Stage " + executable + " successful");
		}
	}

	public int getProgress() {
		List<String> readStatusLines = null;
		int progress = 0;
		String path = "";
		int startChar = 4;
		int endChar = 0;

		switch (executionphase) {
		case 1:
			startChar = 4;
			endChar = 0;
			path = mainController.workingDirectory+"inputMS.Rout";
			break;
		case 2:
			startChar = 0;
			endChar = 3;
			path = mainController.workingDirectory+"posterior.txt";
			break;
		case 3:
			break;
		default:
			break;
		}

		try {
			System.out.println("Workingdir: "+path);
			readStatusLines = Files.readAllLines(Paths.get(path));
		} catch (IOException e) {
			e.printStackTrace();
		}
		if (!readStatusLines.isEmpty()) {
			String progressString = "0";
			try {

			if (endChar == 0) {
				progressString = readStatusLines.get(readStatusLines.size() - 1).substring(startChar);
			} else {
				progressString = readStatusLines.get(readStatusLines.size() - 1).substring(startChar, endChar);
			}
				progress = Integer.parseInt(progressString);
			} catch (NumberFormatException|IndexOutOfBoundsException e) {
				// do nothing
			}
			System.out.println(progress);
		}
		return progress;
	}
}