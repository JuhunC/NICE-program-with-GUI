package application_gui;

import java.io.File;

import application.ConfigBuilder;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class MainController {
    final FileChooser fileChooser = new FileChooser();
    //A stage in which files can be searched after on
	private Stage fileStage = new Stage();
	@FXML
	Button rpathbtn = new Button();
	@FXML
	TextField rpath = new TextField();
	String rPathText = new String();

	@FXML
	Button javapathbtn = new Button();
	@FXML
	TextField javapath = new TextField();
	//Should be default value on startup
	String javaPathText = new String();

	@FXML
	Button snppathbtn = new Button();
	@FXML
	TextField snppath = new TextField();
	String snpPathText = new String();

	@FXML
	Button phenopathbtn = new Button();
	@FXML
	TextField phenopath = new TextField();
	String phenoPathText = new String();

	@FXML
	//textfields for pvalue data and metasoft data, I think some must be parsed into Integer except mvalue_method
	TextField min_gene = new TextField();
	@FXML
	TextField mvalue_threshold = new TextField();
	
	//Metasoft input
	@FXML TextField mvalue_method = new TextField();
	@FXML TextField mcmc_sample = new TextField();
	@FXML TextField seed = new TextField();
	@FXML TextField mvalue_p_thres = new TextField();
	@FXML TextField mvalue_prior_sigma = new TextField();
	@FXML TextField mvalue_prior_beta = new TextField();
	@FXML
	Button runbtn = new Button();
	
	public MainController() {
	}

	@FXML
	private void initialize() {
	}
	
	@FXML
	private void configFileChooser() {
		fileChooser.setInitialDirectory(
                new File(System.getProperty("user.home"))
            );   
	}
	//Functions for choosing files
	@FXML
	private void chooseJavaPath() {
		//Should have a default
		fileChooser.setTitle("Open Java path File");
		//here should call generic function   
		configFileChooser();
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("EXE", "*.exe")
            );
		File file = fileChooser.showOpenDialog(fileStage);
        if (file != null) {
            javaPathText = file.getAbsolutePath();
            javapath.setText(javaPathText);
        }
	}
	@FXML
	private void chooseRPath() {
		//Should have a default
		fileChooser.setTitle("Open R path File");
		//here should call generic function   
		configFileChooser();
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("EXE", "*.exe")
            );
		File file = fileChooser.showOpenDialog(fileStage);
        if (file != null) {
            rPathText = file.getAbsolutePath();
            rpath.setText(rPathText);
        }
	}
	@FXML
	private void chooseSNPPath() {
		fileChooser.setTitle("Open SNP File");
		//here should call generic function   
		configFileChooser();
		File file = fileChooser.showOpenDialog(fileStage);
        if (file != null) {
            snpPathText = file.getAbsolutePath();
            snppath.setText(snpPathText);
        }
	}
	@FXML
	private void choosePhenoPath() {
		fileChooser.setTitle("Open Pheno File");
		//here should call generic function   
		configFileChooser();
		File file = fileChooser.showOpenDialog(fileStage);
        if (file != null) {
            phenoPathText = file.getAbsolutePath();
            phenopath.setText(phenoPathText);
        }
	}
	
	@FXML
	private void runScript() {
		//Gettext from these
		TextField rpath = new TextField();
		TextField javapath = new TextField();
		TextField snppath = new TextField();
		TextField phenopath = new TextField();
		//use p-value from the standard ttest using the t_test_static in the NICE package. -pvalue = ./test/p_ttest.txt
		//out is default -out = ./test/ but maybe this can be changed to give an output in a javafx scene
		TextField min_gene = new TextField();
		TextField mvalue_threshold = new TextField();
		
		TextField mvalue_method = new TextField();
		TextField mcmc_sample = new TextField();
		TextField seed = new TextField();
		TextField mvalue_p_thres = new TextField();
		TextField mvalue_prior_sigma = new TextField();
		TextField mvalue_prior_beta = new TextField();
		//Buildconfig
		/*
		try {
			ConfigBuilder.builder().setJavaPath(par1java.getText()).addParameter("", "").build();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			text.setText("Error: " + e.getMessage());
		}*/
	}
}

