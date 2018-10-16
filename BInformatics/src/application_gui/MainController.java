package application_gui;

import application.ConfigBuilder;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class MainController {
	@FXML
	Button btn = new Button();
	@FXML
	Label text = new Label();
	@FXML
	TextField par1java = new TextField();

	public MainController() {

	}

	@FXML
	private void initialize() {
	}

	@FXML
	private void changeText() {
		try {
			ConfigBuilder.builder().setJavaPath(par1java.getText()).addParameter("", "").build();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			text.setText("Error: " + e.getMessage());
		}
	}
}
