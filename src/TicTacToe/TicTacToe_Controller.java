package TicTacToe;


import TicTacToe.TicTacToe_Model.HumanPlayer;
import TicTacToe.TicTacToe_Model.Value;

public class TicTacToe_Controller {
	final private TicTacToe_Model model;
	final private TicTacToe_View view;

	private boolean computerPlayer = false;

	public TicTacToe_Controller(TicTacToe_Model model, TicTacToe_View view) {
		this.view = view;
		this.model = model;
		
		// set in the model all on the board empty
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				model.setAllEmpty(i, j);
			}
		}

		// Buttons set sign
		view.btnComputer.setOnAction((event) -> {
			changeComputer();
			view.btnComputer.setStyle("-fx-background-color: #800000; -fx-text-fill: white");
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

		// Menu Items
		view.newGame.setOnAction((event) -> {
			cleanUp();
		});
		view.closeGame.setOnAction((event) -> {
			view.stop();
		});

	}

	public void setButtonProperties(int i, int j) {
		if (model.getSign() == Value.Cross) {
			view.buttons[i][j].setStyle("-fx-background-color: #800000; -fx-font-size: 25pt");
			view.buttons[i][j].setText("X");
			view.buttons[i][j].setDisable(true);
			model.setBoard(i, j);
			winProcedure(model.checkWinner(i, j, Value.Cross));

		} else {
				view.buttons[i][j].setStyle("-fx-background-color: #0000FF; -fx-font-size: 25pt");
				view.buttons[i][j].setText("O");
				view.buttons[i][j].setDisable(true);
				model.setBoard(i, j);
				winProcedure(model.checkWinner(i, j, Value.Point));
			} 

		if (model.getPlayer() == HumanPlayer.Human && computerPlayer == true){
			model.setPlayer(HumanPlayer.Computer);
		} else {
			model.setPlayer(HumanPlayer.Human);
			if(model.getSign() == Value.Cross){
			model.setSign(Value.Point);
			} else {
				model.setSign(Value.Cross);
			}
		}

	}
	

	// give win statement and block the program
	public void winProcedure(boolean w) {
		if (w == true) {
			System.out.println("You win!!");
			view.block();
		}
	}

	public void cleanUp() {
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				view.buttons[i][j].setStyle(null);
				view.buttons[i][j].setText(null);
				view.buttons[i][j].setDisable(false);
				view.btnComputer.setStyle(null);
				model.setAllEmpty(i, j);
				model.setPlayer(HumanPlayer.Human);
				model.setSign(Value.Cross);
			}
		}
	}

	public void changeComputer() {
		if (this.computerPlayer == true) {
			this.computerPlayer = false;
		} else {
			this.computerPlayer = true;
		}
	}

}
