package TicTacToe;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;
import TicTacToe.TicTacToe_Controller;
import TicTacToe.TicTacToe_Model;
import TicTacToe.TicTacToe_View;
import TicTacToe.TicTacToe_Computer;
import TicTacToe.Translator;
import TicTacToe.Configuration;

import java.io.IOException;
import java.net.UnknownHostException;
import java.util.logging.FileHandler;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;

import SplashScreen_TicTacToe.Splash_Controller;
import SplashScreen_TicTacToe.Splash_Model;
import SplashScreen_TicTacToe.Splash_View;
import TicTacToe.ServiceLocator;

public class TicTacToeStart extends Application {
	// Splash
	private static TicTacToeStart mainProgram; // singleton
	private Splash_View splashView;

	// Regular
	private TicTacToe_View view;
	private TicTacToe_Model model;
	private TicTacToe_Controller controller;
	//private TicTacToe_Computer computer;
	private TicTacToe_MiniMax ticTacToe_MiniMax;
	private ServiceLocator serviceLocator;
	private TicTacToe_Client client;

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void init() {
		if (mainProgram == null) {
			mainProgram = this;
		} else {
			Platform.exit();
		}
	}

	// Begin a new game
	@Override
	public void start(Stage primaryStage) {
		// Create and display the splash screen and model
		Splash_Model splashModel = new Splash_Model();
		splashView = new Splash_View(primaryStage, splashModel);
		new Splash_Controller(this, splashModel, splashView);
		splashView.start();

		// Display the splash screen and begin the initialization
		splashModel.initialize();

	}

	public void startApp(){
		Stage stage = new Stage();
		;
		
		// Initialize the application MVC components. Note that these components
		// can only be initialized now, because they may depend on the
		// resources initialized by the splash screen
		model = new TicTacToe_Model();
		view = new TicTacToe_View(stage, model);
		try {
			client = new TicTacToe_Client();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		new TicTacToe_Controller(model, view, client);
		//computer = new TicTacToe_Computer(model);
		ticTacToe_MiniMax = new TicTacToe_MiniMax(model);
		//model.setComputer(computer);
		model.setMiniMax(ticTacToe_MiniMax);
		client.setController(controller);

		// Resources are now initialized
		serviceLocator = ServiceLocator.getServiceLocator();

		// Close the splash screen, and set the reference to null, so that all
		// Splash_XXX objects can be garbage collected
		splashView.stop();
		splashView = null;

		view.start();
	}

	@Override
	public void stop() {
		serviceLocator.getConfiguration().save();
		if (view != null) {
			// Make the view invisible
			view.stop();
		}

		// More cleanup code as needed

		serviceLocator.getLogger().info("Application terminated");
	}

	// Static getter for a reference to the main program object
	protected static TicTacToeStart getMainProgram() {
		return mainProgram;
	}

}
