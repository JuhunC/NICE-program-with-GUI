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
		try {	
			//provisoric:
			String out = "./test/";
			String outFile ="inputMS.R";
			//todo: define and add out (outputfolder) parameter
			ConfigBuilder firstR = ConfigBuilder.builder().setRPath(rPathText).addParameter("snp", snpPathText).addParameter("pheno", phenoPathText).setROutputFile(outFile).build();
			System.out.println(firstR);
			//todo: define mvalue, pvalue_table
			ConfigBuilder firstJava = ConfigBuilder.builder().setJavaPath(javaPathText).addParameter("input", out+outFile).addParameter("mvalue", "").addParameter("mvalue_method", this.mvalue_method.getText()).addParameter("mcmc_sample", mcmc_sample.getText()).addParameter("seed",this.seed.getText()).addParameter("mvalue_p_thres", this.mvalue_p_thres.getText()).addParameter("mvalue_prior_sigma", this.mvalue_prior_sigma.getText()).addParameter("mvalue_prior_beta", this.mvalue_prior_beta.getText()).addParameter("pvalue_table",out+"HanEskinPvalueTable.txt").addParameter("out", out+"posterior.txt").build();
			System.out.println(firstJava);
			//todo: define new out and outFile
			ConfigBuilder secondR = ConfigBuilder.builder().setRPath(rPathText).addParameter("snp", snpPathText).addParameter("pheno", phenoPathText).addParameter("MvalueThreshold", this.mvalue_threshold.getText()).addParameter("Mvalue", out+"posterior.txt").addParameter("minGeneNumber", this.min_gene.getText()).addParameter("Pdefault", out+"p_ttest.txt").addParameter("out", out).addParameter("NICE", "./").setROutputFile("./NICE.R").build();
			System.out.println(secondR);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("Error: " + e.getMessage());
		}
	}
}

