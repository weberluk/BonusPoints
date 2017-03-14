package Lotto;

import java.util.logging.FileHandler;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.stage.Stage;

public class LottoStart extends Application {

		Lotto_View view;
		Lotto_Model model;
		Lotto_Controller controller;
		ServiceLocator sl;
		
		public static void main(String[] args) {
			launch(args);
		}
		
		// Begin a new game
		@Override
		public void start(Stage primaryStage) throws Exception {
			view = new Lotto_View(primaryStage, model );
			model = new Lotto_Model();
			controller = new Lotto_Controller(model, view);
			sl = ServiceLocator.getServiceLocator();
			
            // Create the service locator to hold our resources
            sl = ServiceLocator.getServiceLocator();
            
            // Initialize the resources in the service locator
            sl.setLogger(sl.configureLogging());
			
			view.start();
			
		}
		
		

	}
