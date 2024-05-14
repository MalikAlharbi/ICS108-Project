package project;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

// Just a test class to check whether the data is read or not.

public class TestClass extends Application {

	public static void main(String[] args) {
		launch(args);

	}

	@Override
	public void start(Stage primaryStage) {
		CommonClass.loadBinaryData();
		CommonClass.mainScene = new Scene(new MainPane(), 500, 329);
		primaryStage.setResizable(false);
		primaryStage.setScene(CommonClass.mainScene);
		primaryStage.show();
	}
}