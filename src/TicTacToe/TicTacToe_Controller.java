package TicTacToe;

import java.sql.SQLException;
import java.util.Optional;

import TicTacToe.TicTacToe_Model;
import TicTacToe.TicTacToe_View;
import TicTacToe.TicTacToe_Model.HumanPlayer;
import TicTacToe.TicTacToe_Model.Value;
import TicTacToe_Server.TicTacToe_H2;
import TicTacToe_Server.TicTacToe_Server;
import javafx.scene.control.TextInputDialog;
import javafx.scene.input.KeyCode;
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

		// get Points from the Database with the name in the model
		TicTacToe_H2 h2 = TicTacToe_H2.getDB();
		int id = model.generateId(model.getName());
		try {
			view.points.setText(h2.selectPreparedStatementForDisplayTheNameAndPoints(id));
		} catch (SQLException e) {
			e.printStackTrace();
		}

		// Buttons set sign for the whole board
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
		// NewGame Button to Start a new Game also get the Name and the Points new 
		view.newGame.setOnAction((event) -> {
			sl.getLogger().info("Start a new Game");
			cleanUp();
			try {
				int idRestart = model.generateId(model.getName());
				view.points.setText(h2.selectPreparedStatementForDisplayTheNameAndPoints(idRestart));
			} catch (SQLException e) {
				e.printStackTrace();
			}
		});
		
		//Close Game
		view.closeGame.setOnAction((event) -> {
			sl.getLogger().info("Application terminated");
			view.stop();
		});
		
		// Chat-Message-Button to send
		view.btnSend.setOnAction((event) -> {
			client.writeChatMessageToServer("\n" + model.getName() + ": " + view.input.getText());
		});
		
		// The input-Field can also send a Chat-Message with Enter
		view.input.setOnKeyPressed(e -> {
			if (e.getCode() == KeyCode.ENTER)
				client.writeChatMessageToServer("\n" + model.getName() + ": " + view.input.getText());
		});

		// Menu change user for set a new user
		view.changeUser.setOnAction((event) -> {
			TextInputDialog dialog = new TextInputDialog();
			dialog.setTitle("Change User");
			dialog.setHeaderText("We need your name");
			dialog.setContentText("Please enter your name: ");
			Optional<String> result = dialog.showAndWait();

			// When you give a new Name it set a new Name and calculate a new Id for the Model
			result.ifPresent(name -> {
				model.setName(name);
				model.setId(model.generateId(model.getName()));
				//write Entry in DB
				try {
					h2.insertWithPreparedStatement(model.getId(), model.getName(), 0);
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			});
		});

		// Watch the model for changing
		model.getValueProperty().addListener((obervable, oldValue, newValue) -> {
			sl.getLogger().info("ComputerLogicIsRunning");
			if (model.getPlayer() == HumanPlayer.Computer) {
				this.checkComputerPlayBevorWorkFlow(model.getXPos(), model.getYPos());
			}
		});

		// Watch the client for Message
		client.getChatMessageProperty().addListener((obervable, oldValue, newValue) -> {
			sl.getLogger().info("Message from Chat is arrived");
			view.chat.setScrollLeft(Double.MAX_VALUE);
			view.chat.appendText(client.getChatMessageInString());
			view.input.clear();
		});

	}

	// Is it a a computer-Player or a human and go to the Workflow
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

	// The Workflow for the game becomes to int and set this in the board
	// also writing messages to the server
	public void workFlow(int i, int j) {
		this.setButtonProperties(i, j);
		model.setBoard(i, j);
		model.setIndexForMiniMax(i, j);
		client.writePlayMessageToServer(i, j, getValueContructor());
		this.winProcedure(model.checkWinner(i, j, model.getSign()));
		this.setOtherSign();
		this.setOtherPlayer();
	}

	// For messaging to the server - for the int-array
	public int getValueContructor() {
		if (model.getSign() == Value.Cross) {
			return 1;
		} else {
			return 2;
		}
	}

	// The Buttons for the game
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

	// Change the Sign after every board-setting
	public void setOtherSign() {
		if (model.getSign() == Value.Cross) {
			model.setSign(Value.Point);
		} else {
			model.setSign(Value.Cross);
		}
	}

	// Change the player after every board-setting
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
			// write a winMessage to the server user: 1 for human 2 for computer and also the score from the model
			if(model.getPlayer() == HumanPlayer.Computer){
				model.setName("computer");
				model.setId(model.generateId(model.getName()));
			} 
			client.writeWinMessageToServer(model.getId(), model.getScore());
			view.block();
		}
	}
	
	// Cleans all up
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
				view.points.clear();
			}
		}
	}

	// Is it a computer-turn or not
	public void changeComputer() {
		if (this.computerPlayer == true) {
			this.computerPlayer = false;
		} else {
			this.computerPlayer = true;
		}
	}
	

}
