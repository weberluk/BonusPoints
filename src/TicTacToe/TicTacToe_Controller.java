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

		// get Points
		// TicTacToe_XMLWriter xml = new TicTacToe_XMLWriter();
		TicTacToe_H2 h2 = new TicTacToe_H2();
		int id = this.generateId(model.getName());
		try {
			
			//view.points.setText(h2.selectPreparedStatement("select * from PERSON where id = ?") + id);
			view.points.setText(h2.selectPreparedStatementPrep(id));
		} catch (SQLException e) {
			e.printStackTrace();
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
			try {
				view.points.setText(h2.selectPreparedStatementPrep(id));
			} catch (SQLException e) {
				e.printStackTrace();
			}

		});
		view.closeGame.setOnAction((event) -> {
			sl.getLogger().info("Application terminated");
			view.stop();
		});
		view.btnSend.setOnAction((event) -> {
			client.writeChatMessageToServer("\n" + model.getName() + ": " + view.input.getText());
		});
		view.input.setOnKeyPressed(e -> {
			if (e.getCode() == KeyCode.ENTER)
				client.writeChatMessageToServer("\n" + model.getName() + ": " + view.input.getText());
		});

		view.changeUser.setOnAction((event) -> {
			TextInputDialog dialog = new TextInputDialog("default");
			dialog.setTitle("Change User");
			dialog.setHeaderText("We need your name");
			dialog.setContentText("Please enter your name: ");
			Optional<String> result = dialog.showAndWait();

			result.ifPresent(name ->  {
			model.setName(name);
			model.setId(this.generateId(model.getName()));
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
		model.setIndexForMiniMax(i, j);
		client.writePlayMessageToServer(i, j, getValueContructor());
		this.winProcedure(model.checkWinner(i, j, model.getSign()));
		this.setOtherSign();
		this.setOtherPlayer();
	}

	public int getValueContructor() {
		if (model.getSign() == Value.Cross) {
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
			// Score Model from the TicTacToe_miniMax
			// + 20 if win
			// -30 if even
			client.writeWinMessageToServer(getUserConvert(), model.getScore());
			view.block();
		}
	}

	public int getUserConvert() {
		if (model.getPlayer() == HumanPlayer.Human) {
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
				view.points.clear();
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

	public int generateId(String name) {

		TicTacToe_Model model = new TicTacToe_Model();
		TicTacToe_Server server = new TicTacToe_Server();
		char[] charArray;
		int i = 0;

		charArray = name.toCharArray();

		for (char a : charArray) {
			i += (int) a;
		}
		return i;
	}
}
