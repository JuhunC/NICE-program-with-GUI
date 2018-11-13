package application;

import java.io.IOException;
import java.nio.charset.MalformedInputException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import application_gui.MainController;
import javafx.application.Platform;
import javafx.beans.property.DoublePropertyBase;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringPropertyBase;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;

public class ProgramExecutor extends Thread {

	private String executable = "";
	private int executionphase = 0;
	private MainController mainController = null;
	private List<String> readAllLines;
	public String message = "";
	public Label messageProperty;
	public ProgressBar progressProperty;
	public CheckBox checkBoxProperty;

	public double progress;

	public ProgramExecutor(MainController mainController, Label updateText, ProgressBar progressbar,
			CheckBox checkbox) {
		this.mainController = mainController;
		this.messageProperty = updateText;
		this.progressProperty = progressbar;
		this.checkBoxProperty = checkbox;
	}

	public ProgramExecutor() {

	}

	public void setExecutionphase(int i) {
		this.executionphase = i;
	}

	public void run() {
		Process p = null;
		try {
			this.executable = (String) Files
					.readAllLines(Paths.get(MainController.workingDirectory + "outputconfig.sh"))
					.get(executionphase - 1);
			p = Runtime.getRuntime().exec(executable);
		} catch (IOException e) {
			e.printStackTrace();
		}
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				messageProperty.setText("Program Stage " + executionphase + " running");
			}
		});
		while (p.isAlive()) {
			try {
				sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			Platform.runLater(new Runnable() {
				@Override
				public void run() {
					progressProperty.setProgress(getProgress() / 1000.0);
				}
			});
		}
		int exitValue = p.exitValue();
		if (exitValue != 0) {
			Platform.runLater(new Runnable() {
				@Override
				public void run() {
					messageProperty.setText("Program Stage " + executionphase + " not successful");
					checkBoxProperty.setSelected(false);
				}
			});
		} else {
			Platform.runLater(new Runnable() {
				@Override
				public void run() {
					messageProperty.setText("Program Stage " + executionphase + " successful");
				}
			});
			if (checkBoxProperty.isSelected() && executionphase < 3) {
				this.setExecutionphase(executionphase + 1);
				this.run();
			}
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
			path = mainController.workingDirectory + "inputMS.Rout";
			break;
		case 2:
			startChar = 0;
			endChar = 3;
			path = mainController.workingDirectory + "posterior.txt";
			break;
		case 3:
			break;
		default:
			break;
		}

		try {
			System.out.println("Workingdir: " + path);
			readStatusLines = Files.readAllLines(Paths.get(path), StandardCharsets.ISO_8859_1);
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
			} catch (NumberFormatException | IndexOutOfBoundsException e) {
				// do nothing
			}
			System.out.println(progress);
		}
		return progress;
	}
}