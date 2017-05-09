package TicTacToe_Server;


import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.application.Application;
import javafx.stage.Stage;

public class TicTacToe_Server_Start extends Application{
	private TicTacToe_Server_View view;
	private TicTacToe_Server_Controller controller;
	private TicTacToe_Server model;
	
    public static void main(String[] args) {
        launch(args);
    }

	@Override
	public void start(Stage primaryStage) throws Exception {
        // Part of the GUI will contain log output from our own handler
        TextAreaHandler textAreaHandler = new TextAreaHandler();
        textAreaHandler.setLevel(Level.INFO);
        Logger defaultLogger = Logger.getLogger("");
        defaultLogger.addHandler(textAreaHandler);		
		
     // Initialize the GUI
		model = new TicTacToe_Server();
		view = new TicTacToe_Server_View(primaryStage, model, textAreaHandler.getTextArea());
		controller = new TicTacToe_Server_Controller(model, view);
		
		view.start();
		
	}
	
    /**
     * The stop method is the opposite of the start method. It provides an
     * opportunity to close down the program gracefully, when the program has
     * been closed.
     */
    @Override
    public void stop() {
        if (view != null)
            view.stop();
    }

}
