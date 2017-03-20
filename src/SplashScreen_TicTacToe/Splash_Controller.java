package SplashScreen_TicTacToe;

import TicTacToe.TicTacToeStart;
import javafx.concurrent.Worker;

public class Splash_Controller {
	protected Splash_Model model;
	protected Splash_View view;
	protected TicTacToeStart main;

	public Splash_Controller(final TicTacToeStart main, Splash_Model model, Splash_View view) {
		this.model = model;
		this.view = view;
		this.main = main;
		

		view.progressBar.progressProperty().bind(model.initializer.progressProperty());

		model.initializer.stateProperty().addListener((observable, oldValue, newValue) -> {
			if (newValue == Worker.State.SUCCEEDED)
				main.startApp();
		});
	}
}
