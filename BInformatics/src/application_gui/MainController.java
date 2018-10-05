package application_gui;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

public class MainController {
	@FXML
	Button btn = new Button();
	@FXML
	Label text = new Label();
	
	public MainController() {
		
	}
	
    @FXML
    private void initialize() {
    }
    @FXML
    private void changeText() {
    	text.setText("smart");
    }
}
