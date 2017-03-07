package Lotto;

import javafx.application.Application;
import javafx.stage.Stage;

public class LottoStart extends Application {

		Lotto_View view;
		Lotto_Model model;
		Lotto_Controller controller;

		public static void main(String[] args) {
			launch(args);
		}
		
		// Begin a new game
		@Override
		public void start(Stage primaryStage) throws Exception {
			view = new Lotto_View(primaryStage, model );
			model = new Lotto_Model();
			controller = new Lotto_Controller(model, view);

			
			view.start();
			
		}

	}
