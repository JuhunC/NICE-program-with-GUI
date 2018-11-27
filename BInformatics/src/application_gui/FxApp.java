package application_gui;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Scanner;

import application.ConfigBuilder;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class FxApp extends Application {

	public static void main(String[] args) {
		if (args.length == 0)
			// Launch UI Mode
			launch(FxApp.class, args);
		else {
			// Launch Console Mode
			String msFilenameR = "inputMS.R";
			String msFilenameTxt = "inputMS.txt";
			String secondOutputfile = "posterior.txt";
			try {
				String workingDirectory = askUserForInput("Workingdirectory", new java.io.File(".").getCanonicalPath()+"\\");
				String rpath = askUserForInput("rPath", "");
				String snpPath = askUserForInput("snpPath", "");
				String phenoPath = askUserForInput("phenoPath", "");

				ConfigBuilder firstR = ConfigBuilder.builder().setRPath(rpath).addParameter("snp", snpPath)
						.addParameter("pheno", phenoPath).addParameter("out", workingDirectory)
						.setROutputFile(workingDirectory + msFilenameR).build();
				ConfigBuilder firstJava = ConfigBuilder.builder()
						.setJavaPath("\"" + askUserForInput("javaPath", MainController.javaExe()) + "\"")
						.addParameter("jar", workingDirectory + "Metasoft.jar").addParameter("mvalue", "")
						.addParameter("input", workingDirectory + msFilenameTxt)
						.addParameter("mvalue_method", askUserForInput("mvalue_method", "mcmc"))
						.addParameter("mcmc_sample", askUserForInput("mcmc_smaple", "1000000"))
						.addParameter("seed", askUserForInput("seed", "0"))
						.addParameter("mvalue_p_thres", askUserForInput("mvalue_p_thres", "1.0"))
						.addParameter("mvalue_prior_sigma", askUserForInput("mvalue_prior_sigma", "0.05"))
						.addParameter("mvalue_prior_beta", askUserForInput("mvalue_prior_beta", "1 5"))
						.addParameter("pvalue_table", workingDirectory + "HanEskinPvalueTable.txt")
						.addParameter("output", workingDirectory + secondOutputfile).build();
				ConfigBuilder secondR = ConfigBuilder.builder().setRPath("\"" + rpath + "\"")
						.addParameter("snp", snpPath).addParameter("pheno", phenoPath)
						.addParameter("MvalueThreshold", askUserForInput("MvalueThreshold", "1.0"))
						.addParameter("Mvalue", workingDirectory + secondOutputfile)
						.addParameter("minGeneNumber", askUserForInput("Minimum number of Genes", "10"))
						.addParameter("Pdefault", workingDirectory + "p_ttest.txt")
						.addParameter("out", workingDirectory).addParameter("NICE", workingDirectory)
						.setROutputFile(workingDirectory + "NICE.R").build();
				System.out.println("-------------------------------------------");
				System.out.println("Generated Scriptfile:");
				System.out.println(firstR);
				System.out.println(firstJava);
				System.out.println(secondR);
				Path configPath = Paths.get(workingDirectory + "outputconfig.sh");
				Files.write(configPath, (firstR.toString() + "\n").getBytes());
				Files.write(configPath, (firstJava.toString() + "\n").getBytes(),
						StandardOpenOption.APPEND);
				Files.write(configPath, secondR.toString().getBytes(),
						StandardOpenOption.APPEND);
				System.out.println("-------------------------------------------");
			System.out.println("Scriptfile saved");
			String runScriptfile = askUserForInput("Do you want to run the Scriptfile?","y,N");
			if (runScriptfile.equalsIgnoreCase("y")) {
				Runtime.getRuntime().exec(workingDirectory + "outputconfig.sh");
			} else {
				System.out.println("Exiting - Bye");
			}
			} catch (Exception e) {
				e.printStackTrace();
			}
			System.exit(0);
		}
	}

	public static String askUserForInput(String description, String defaultvalue) {
		System.out.print(description + " [" + defaultvalue + "]: ");
		Scanner scanner = new Scanner(System.in);
		String input = scanner.nextLine();
		if (input.isEmpty())
			return defaultvalue;
		else
			return input;
	}

	
	// UI Mode
	@Override
	public void start(Stage stage) throws Exception {
		Pane root = new Pane(); // Root of the scene graph
		MainController controller = new MainController();
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(FxApp.class.getResource("Main.fxml"));
		try {
			root = loader.load();
		} catch (IOException e) {
			e.printStackTrace();
		}

		loader.setController(controller);
		Scene scene = new Scene(root);
		Image applicationIcon = new Image(getClass().getResourceAsStream("DNA-Helix-icon.png"));
		stage.getIcons().add(applicationIcon);
		stage.setScene(scene);
		stage.setTitle("NICE");
		stage.setResizable(false);
		stage.show();
	}
}