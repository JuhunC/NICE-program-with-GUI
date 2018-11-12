package application_gui;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

import javax.swing.JFileChooser;

import application.ConfigBuilder;
import application.ProgramExecutor;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class MainController {
	final FileChooser fileChooser = new FileChooser();
	// A stage in which files can be searched after on
	private Stage fileStage = new Stage();
	@FXML
	Button rpathbtn = new Button();
	@FXML
	TextField rpath = new TextField();

	@FXML
	Button javapathbtn = new Button();
	@FXML
	TextField javapath = new TextField();
	// Should be default value on startup

	@FXML
	Button snppathbtn = new Button();
	@FXML
	TextField snppath = new TextField();

	@FXML
	Button phenopathbtn = new Button();
	@FXML
	TextField phenopath = new TextField();

	@FXML
	Button inputmspathbtn = new Button();
	@FXML
	TextField inputmspath = new TextField();

	@FXML
	// textfields for pvalue data and metasoft data, I think some must be parsed
	// into Integer except mvalue_method
	TextField min_gene = new TextField();
	@FXML
	TextField mvalue_threshold = new TextField();

	// Metasoft input
	@FXML
	TextField mvalue_method = new TextField();
	@FXML
	TextField mcmc_sample = new TextField();
	@FXML
	TextField seed = new TextField();
	@FXML
	TextField mvalue_p_thres = new TextField();
	@FXML
	TextField mvalue_prior_sigma = new TextField();
	@FXML
	TextField mvalue_prior_beta = new TextField();
	@FXML
	Button runbtn1 = new Button();
	@FXML
	Button runbtn2 = new Button();
	@FXML
	Button runbtn3 = new Button();
	@FXML
	Label updateText = new Label();
	@FXML
	CheckBox checkbox = new CheckBox();

	@FXML
	ProgressBar progressbar = new ProgressBar();

	public String workingDirectory;

	public MainController() {
	}

	@FXML
	private void initialize() {
		javapath.setText(javaExe());
	}

	@FXML
	private void configFileChooser() {
		fileChooser.setInitialDirectory(new File(System.getProperty("user.home")));
	}

	// Functions for choosing files
	@FXML
	private void chooseJavaPath() {
		// Should have a default
		fileChooser.setTitle("Open Java path File");
		// here should call generic function
		configFileChooser();
		/*
		 * fileChooser.getExtensionFilters().addAll( new
		 * FileChooser.ExtensionFilter("ALL", "*") );
		 */
		File file = fileChooser.showOpenDialog(fileStage);
		if (file != null) {
			javapath.setText(file.getAbsolutePath());
		}
	}

	@FXML
	private void chooseRPath() {
		// Should have a default
		fileChooser.setTitle("Open R path File");
		// here should call generic function
		configFileChooser();

		File file = fileChooser.showOpenDialog(fileStage);
		if (file != null) {
			rpath.setText(file.getAbsolutePath());
		}
	}

	@FXML
	private void chooseSNPPath() {
		fileChooser.setTitle("Open SNP File");
		// here should call generic function
		configFileChooser();

		File file = fileChooser.showOpenDialog(fileStage);
		if (file != null) {
			snppath.setText(file.getAbsolutePath());
		}
	}

	@FXML
	private void choosePhenoPath() {
		fileChooser.setTitle("Open Pheno File");
		// here should call generic function
		configFileChooser();
		File file = fileChooser.showOpenDialog(fileStage);
		if (file != null) {
			phenopath.setText(file.getAbsolutePath());
		}
	}

	@FXML
	private void chooseinputMSPath() {
		JFileChooser chooser = new JFileChooser();
		fileChooser.setTitle("Open work folder");
		// here should call generic function
		configFileChooser();
		chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		//
		// disable the "All files" option.
		//
		chooser.setAcceptAllFileFilterUsed(false);
		if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
			inputmspath.setText(chooser.getSelectedFile().getAbsolutePath() + "\\");
		}
		// File file = fileChooser.showOpenDialog(fileStage);
		// if (file != null) {
		// inputmspath.setText(file.getAbsolutePath());
		// }
	}

	@FXML
	private void runScript1() {
		// Gettext from these
		/*
		 * TextField rpath = new TextField(); TextField javapath = new TextField();
		 * TextField snppath = new TextField(); TextField phenopath = new TextField();
		 * //use p-value from the standard ttest using the t_test_static in the NICE
		 * package. -pvalue = ./test/p_ttest.txt //out is default -out = ./test/ but
		 * maybe this can be changed to give an output in a javafx scene TextField
		 * min_gene = new TextField(); TextField mvalue_threshold = new TextField();
		 * 
		 * TextField mvalue_method = new TextField(); TextField mcmc_sample = new
		 * TextField(); TextField seed = new TextField(); TextField mvalue_p_thres = new
		 * TextField(); TextField mvalue_prior_sigma = new TextField(); TextField
		 * mvalue_prior_beta = new TextField();
		 */
		// Buildconfig
		try {
			// provisoric:
			String out = "./";
			String msFilenameR = "inputMS.R";
			String msFilenameTxt = "inputMS.txt";
			String secondOutputfile = "posterior.txt";
			workingDirectory = inputmspath.getText();

			ConfigBuilder firstR = ConfigBuilder.builder().setRPath(rpath.getText())
					.addParameter("snp", snppath.getText()).addParameter("pheno", phenopath.getText())
					.addParameter("out", out).setROutputFile(workingDirectory + msFilenameR).build();
			// todo: define mvalue, pvalue_table
			ConfigBuilder firstJava = ConfigBuilder.builder().setJavaPath(javapath.getText())
					.addParameter("input", workingDirectory + msFilenameTxt)
					.addParameter("jar", workingDirectory + "Metasoft.jar").addParameter("mvalue", "")
					.addParameter("mvalue_method", this.mvalue_method.getText())
					.addParameter("mcmc_sample", mcmc_sample.getText()).addParameter("seed", this.seed.getText())
					.addParameter("mvalue_p_thres", this.mvalue_p_thres.getText())
					.addParameter("mvalue_prior_sigma", this.mvalue_prior_sigma.getText())
					.addParameter("mvalue_prior_beta", this.mvalue_prior_beta.getText())
					.addParameter("pvalue_table", workingDirectory + "HanEskinPvalueTable.txt")
					.addParameter("out", workingDirectory + secondOutputfile).build();
			ConfigBuilder secondR = ConfigBuilder.builder().setRPath(rpath.getText())
					.addParameter("snp", snppath.getText()).addParameter("pheno", phenopath.getText())
					.addParameter("MvalueThreshold", this.mvalue_threshold.getText())
					.addParameter("Mvalue", workingDirectory + secondOutputfile)
					.addParameter("minGeneNumber", this.min_gene.getText())
					.addParameter("Pdefault", workingDirectory + "p_ttest.txt").addParameter("out", out)
					.addParameter("NICE", workingDirectory).setROutputFile(workingDirectory + "NICE.R").build();
			System.out.println(firstR);
			System.out.println(firstJava);
			System.out.println(secondR);
			Files.write(Paths.get("outputconfig.sh"), (firstR.toString() + "\n").getBytes());
			Files.write(Paths.get("outputconfig.sh"), (firstJava.toString() + "\n").getBytes(),
					StandardOpenOption.APPEND);
			Files.write(Paths.get("outputconfig.sh"), secondR.toString().getBytes(), StandardOpenOption.APPEND);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("Error: " + e.getMessage());
		}
		runExecution(1);
	}

	private void runExecution(int i) {
		ProgramExecutor programExecutor = new ProgramExecutor(this);
		programExecutor.setExecutionphase(i);
		programExecutor.start();
	}

	@FXML
	private void runScript2() {
		runExecution(2);
	}

	@FXML
	private void runScript3() {
		runExecution(3);
	}

	public void setStatusMessage(String statusMessage) {
		updateText.setText(statusMessage);
	}

	public void updateProgressBar(double d) {
		// Must be between 0 and 1
		progressbar.setProgress(d);
	}

	private static String javaExe() {
		final String JAVA_HOME = System.getProperty("java.home");
		final File BIN = new File(JAVA_HOME, "bin");
		File exe = new File(BIN, "java");

		if (!exe.exists()) {
			// We might be on Windows, which needs an exe extension
			exe = new File(BIN, "java.exe");
		}

		if (exe.exists()) {
			return exe.getAbsolutePath();
		}

		try {
			// Just try invoking java from the system path; this of course
			// assumes "java[.exe]" is /actually/ Java
			final String NAKED_JAVA = "java";
			new ProcessBuilder(NAKED_JAVA).start();

			return NAKED_JAVA;
		} catch (IOException e) {
			return null;
		}
	}
}
