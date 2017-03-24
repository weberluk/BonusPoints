package TicTacToe;


import java.util.logging.FileHandler;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;

import TicTacToe.TicTacToe_Computer;
import TicTacToe.TicTacToe_Model;
import TicTacToe.TicTacToe_View;
import TicTacToe.TicTacToe_Model.HumanPlayer;
import TicTacToe.TicTacToe_Model.Value;
import TicTacToe.ServiceLocator;
import TicTacToe.TicTacToe_Client;

public class TicTacToe_Controller {
	final private TicTacToe_Model model;
	final private TicTacToe_View view;
	final ServiceLocator sl;
	private TicTacToe_Client client;
	
	private boolean computerPlayer = false;

	public TicTacToe_Controller(TicTacToe_Model model, TicTacToe_View view, TicTacToe_Client client) {
		this.view = view;
		this.model = model;
		this.sl = ServiceLocator.getServiceLocator();
		this.client = client;

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
			this.checkComputerPlayBevorWorkFlow(0, 0);
		});
		view.buttons[0][1].setOnAction((event) -> {
			this.checkComputerPlayBevorWorkFlow(0, 1);
		});
		view.buttons[0][2].setOnAction((event) -> {
			this.checkComputerPlayBevorWorkFlow(0, 2);
		});
		view.buttons[1][0].setOnAction((event) -> {
			this.checkComputerPlayBevorWorkFlow(1, 0);
		});
		view.buttons[1][1].setOnAction((event) -> {
			this.checkComputerPlayBevorWorkFlow(1, 1);
		});
		view.buttons[1][2].setOnAction((event) -> {
			this.checkComputerPlayBevorWorkFlow(1, 2);
		});
		view.buttons[2][0].setOnAction((event) -> {
			this.checkComputerPlayBevorWorkFlow(2, 0);
		});
		view.buttons[2][1].setOnAction((event) -> {
			this.checkComputerPlayBevorWorkFlow(2, 1);
		});
		view.buttons[2][2].setOnAction((event) -> {
			this.checkComputerPlayBevorWorkFlow(2, 2);
		});

		// Menu Items
		view.newGame.setOnAction((event) -> {
			sl.getLogger().info("Start a new Game");
			cleanUp();
		});
		view.closeGame.setOnAction((event) -> {
			sl.getLogger().info("Application terminated");
			view.stop();
		});

		// Watch the model for changing
		model.getValueProperty().addListener((obervable, oldValue, newValue) -> {
			sl.getLogger().info("ComputerLogicIsRunning");
			if(model.getPlayer() == HumanPlayer.Computer){
				this.checkComputerPlayBevorWorkFlow(model.getXPos(), model.getYPos());
			}
		});

	}

	public void checkComputerPlayBevorWorkFlow(int i, int j) {
		if (this.computerPlayer == true) {
			if (model.getPlayer() == HumanPlayer.Human) {
				sl.getLogger().info("Player is a human");
				if (model.getValue() == false) {
					workFlow(i, j);
					model.setValue(true);
				}

			} else {
				sl.getLogger().info("Player is a computer");
				if (model.getValue() == true) {
					workFlow(i, j);
					model.setValue(false);
				}
			}
		} else {
			sl.getLogger().info("There is no Computer-Player activated");
			workFlow(i, j);
		}
	}

	public void workFlow(int i, int j) {
		this.setButtonProperties(i, j);
		model.setBoard(i, j);
		client.writePlayMessageToServer(i, j, getValueContructor());
		this.winProcedure(model.checkWinner(i, j, model.getSign()));
		this.setOtherSign();
		this.setOtherPlayer();
	}
	
	public int getValueContructor(){
		if(model.getSign() == Value.Cross){
			return 1;
		} else {
			return 2;
		}
	}

	public void setButtonProperties(int i, int j) {
		if (model.getSign() == Value.Cross) {
			view.buttons[i][j].setStyle("-fx-background-color: #800000; -fx-font-size: 25pt");
			view.buttons[i][j].setText("X");
			view.buttons[i][j].setDisable(true);
		} else {
			view.buttons[i][j].setStyle("-fx-background-color: #0000FF; -fx-font-size: 25pt");
			view.buttons[i][j].setText("O");
			view.buttons[i][j].setDisable(true);
		}
	}

	public void setOtherSign() {
		if (model.getSign() == Value.Cross) {
			model.setSign(Value.Point);
		} else {
			model.setSign(Value.Cross);
		}
	}

	public void setOtherPlayer() {
		if (model.getPlayer() == HumanPlayer.Human) {
			model.setPlayer(HumanPlayer.Computer);
		} else {
			model.setPlayer(HumanPlayer.Human);
		}
	}

	// give win statement and block the program
	public void winProcedure(boolean w) {
		if (w == true) {
			sl.getLogger().info("We have a winner!");
			view.tbox.setText("Finish");
			client.writeWinMessageToServer(getUserConvert(), 30);
			view.block();
		}
	}
	
	public int getUserConvert(){
		if(model.getPlayer() == HumanPlayer.Human){
			return 1;
		} else {
			return 2;
		}
	}

	public void cleanUp() {
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				view.buttons[i][j].setStyle(null);
				view.buttons[i][j].setText(null);
				view.buttons[i][j].setDisable(false);
				view.btnComputer.setStyle(null);
				view.tbox.clear();
				model.setAllEmpty(i, j);
				model.setPlayer(HumanPlayer.Human);
				model.setSign(Value.Cross);
				computerPlayer = false;
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
