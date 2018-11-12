package application;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import application_gui.MainController;

public class ProgramExecutor extends Thread {

	private String executable = "";
	private int executionphase = 0;
	private MainController mainController = null;

	public ProgramExecutor(MainController mainController) {
		this.mainController = mainController;
	}

	public void setExecutionphase(int i) {
		this.executionphase = i;
	}

	public void run() {
		Process p = null;
		try {
			this.executable = (String) Files.readAllLines(Paths.get("outputconfig.sh")).get(executionphase-1);
			p = Runtime.getRuntime().exec(executable);
			System.out.println("Waiting for batch file ...");
		} catch (IOException e) {
			e.printStackTrace();
		}
		mainController.setStatusMessage("Programphase "+executable+" running");	
		while(p.isAlive()) {
			try {
				wait(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			mainController.setProgress(0);
		}
		p.exitValue();
		System.out.println("Batch file done.");
	}
	
	public getProgress
}
