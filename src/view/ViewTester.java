package view;

import controller.Controller;
import javafx.application.Application;
import javafx.stage.Stage;

public class ViewTester extends Application{

	public static void main(String[] args) {
		launch(args);		

	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		@SuppressWarnings("unused")
		Controller myController = new Controller(primaryStage);
	}
}
