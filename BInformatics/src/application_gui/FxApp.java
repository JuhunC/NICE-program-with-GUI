package application_gui;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class FxApp extends Application {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		launch(FxApp.class, args);
	}

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
		Scene scene = new Scene(root,1000,1000);

		stage.setScene(scene);
		stage.setTitle("NICE");
		stage.setResizable(false);
		stage.show();
	}
}