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

		public static final Logger LOGGER = Logger.getLogger( Lotto_Controller.class.getName() );
		
		public static void main(String[] args) {
			launch(args);
		}
		
		// Begin a new game
		@Override
		public void start(Stage primaryStage) throws Exception {
			view = new Lotto_View(primaryStage, model );
			model = new Lotto_Model();
			controller = new Lotto_Controller(model, view);
			
			try {
				Handler logHandler = new FileHandler("%t/" + "TicTacToe_Logging" + "_%u" + "_%g" + ".log");
				logHandler.setLevel(Level.INFO);
				LOGGER.addHandler(logHandler);
			} catch (Exception e){
				throw new RuntimeException("Unable to initialize log files " + e.toString());
			}
			
			view.start();
			
		}

	}
