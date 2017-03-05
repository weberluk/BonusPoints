package TicTacToe;

import javafx.scene.control.Button;

public class TicTacToe_Controller {
	final private TicTacToe_Model model;
	final private TicTacToe_View view;

	
	
	public TicTacToe_Controller(TicTacToe_Model model, TicTacToe_View view) {
		this.view = view;
		this.model = model;

		
		//Buttons set sign
		view.btnAuto.setOnAction((event) -> {
			view.btnAuto.setStyle("-fx-background-color: #1d1d1d; -fx-text-fill: white");
		});
		view.buttons[0][0].setOnAction((event) -> {
			setButtonProperties(0, 0);
		});
		view.buttons[0][1].setOnAction((event) -> {
			setButtonProperties(0, 1);
		});
		view.buttons[0][2].setOnAction((event) -> {
			setButtonProperties(0, 2);
		});
		view.buttons[1][0].setOnAction((event) -> {
			setButtonProperties(1, 0);
		});
		view.buttons[1][1].setOnAction((event) -> {
			setButtonProperties(1, 1);
		});
		view.buttons[1][2].setOnAction((event) -> {
			setButtonProperties(1, 2);
		});
		view.buttons[2][0].setOnAction((event) -> {
			setButtonProperties(2, 0);
		});
		view.buttons[2][1].setOnAction((event) -> {
			setButtonProperties(2, 1);
		});
		view.buttons[2][2].setOnAction((event) -> {
			setButtonProperties(2, 2);
		});
		
		//Menu Items
		view.newGame.setOnAction((event) -> {
			view.start();
		});
		view.closeGame.setOnAction((event) -> {
			view.stop();
		});
		
		
		
	}

	public void setButtonProperties(int i, int j) {
		if (model.getUser() == false) {
			view.buttons[i][j].setStyle("-fx-background-color: #800000; -fx-font-size: 25pt");
			view.buttons[i][j].setText("X");
			view.buttons[i][j].setDisable(true);
			model.checkWinner(i, j);
		} else {
			view.buttons[i][j].setStyle("-fx-background-color: #0000FF; -fx-font-size: 25pt");
			view.buttons[i][j].setText("O");
			view.buttons[i][j].setDisable(true);
			model.checkWinner(i, j);
		}
		if (model.getUser() == false) {
			model.setUser(true);
		} else {
			model.setUser(false);
		}

	}

}
