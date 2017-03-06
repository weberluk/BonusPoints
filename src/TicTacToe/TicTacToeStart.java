package TicTacToe;

import javafx.application.Application;
import javafx.stage.Stage;

public class TicTacToeStart extends Application {
	TicTacToe_View view;
	TicTacToe_Model model;
	TicTacToe_Controller controller;

	public static void main(String[] args) {
		launch(args);
	}
	
	// Begin a new game
	@Override
	public void start(Stage primaryStage) throws Exception {
		view = new TicTacToe_View(primaryStage, model );
		model = new TicTacToe_Model();
		controller = new TicTacToe_Controller(model, view);

		
		view.start();
		
	}

}
