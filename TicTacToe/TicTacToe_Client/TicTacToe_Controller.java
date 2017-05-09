package TicTacToe_Client;

import java.io.IOException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Optional;

import TicTacToe_Client.TicTacToe_Model;
import TicTacToe_Client.TicTacToe_View;
import TicTacToe_Client.Client.TicTacToe_Client;
import TicTacToe_Client.TicTacToe_Model.HumanPlayer;
import TicTacToe_Client.TicTacToe_Model.Value;
import TicTacToe_Client.support.ServiceLocator;
import TicTacToe_Server.DataBase.TicTacToe_H2;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.GridPane;

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
		Integer id = model.generateId(model.getName());
		ArrayList<String> messagePoints1 = new ArrayList<String>();
		messagePoints1.add("4"); //DB-Request
		messagePoints1.add(id.toString());
		client.sendMessageToServer(messagePoints1);
		sl.getLogger().info("Send to client / server a request for the DB");

		// Buttons set sign for the whole board
		view.btnComputer.setOnAction((event) -> {
			changeComputer();
			view.btnComputer.setStyle("-fx-background-color: #800000; -fx-text-fill: white");
			view.tbox.setText("Computer stated");
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
			Integer idRestart = model.generateId(model.getName());
			ArrayList<String> messagePoints2 = new ArrayList<String>();
			messagePoints2.add("4"); //DB-Request
			messagePoints2.add(idRestart.toString());
			client.sendMessageToServer(messagePoints2);
			sl.getLogger().info("Send to client / server a request for the DB");
		});
		
		//Close Game
		view.closeGame.setOnAction((event) -> {
			sl.getLogger().info("Application terminated");
			ArrayList<String> messageStop = new ArrayList<String>();
			messageStop.add("5"); //Server-Stop
			messageStop.add("false");
			client.sendMessageToServer(messageStop);
			client.setCloser(false);
			client.run();
			sl.getLogger().info("Send to Server and client a stop");
			view.stop();
		});
		
		// Chat-Message-Button to send
		view.btnSend.setOnAction((event) -> {
			ArrayList<String> messageChat = new ArrayList<String>();
			messageChat.add("3"); //Chat-Message
			messageChat.add("\n" + model.getChatName() + ": " + view.input.getText());
			client.sendMessageToServer(messageChat);
		});
		
		// The input-Field can also send a Chat-Message with Enter
		view.input.setOnKeyPressed(e -> {
			if (e.getCode() == KeyCode.ENTER){
				ArrayList<String> messageChatEnter = new ArrayList<String>();
				messageChatEnter.add("3"); //Chat-Message
				messageChatEnter.add("\n" + model.getChatName() + ": " + view.input.getText());
				client.sendMessageToServer(messageChatEnter);
			}

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
				model.setChatName(name);
				model.setId(model.generateId(model.getName()));
				//write Entry in DB
				ArrayList<String> output= new ArrayList<String>();
				output.add("6"); //Insert-Statement
				output.add(Integer.toString(model.generateId(model.getName())));
				output.add(model.getName());
				client.sendMessageToServer(output);
				view.tbox.setText("Your name: " + name);
			});
		});
		
		//reset the DB
		view.resetDB.setOnAction(event -> {
			ArrayList<String> output= new ArrayList<String>();
			output.add("7"); //Delete-Statement
			client.sendMessageToServer(output);
			view.tbox.setText("DB is reseted");
		});

		//make a new XML
		view.writeXML.setOnAction(event -> {
			ArrayList<String> output = new ArrayList<String>();
			output.add("8"); //Make a XML
			client.sendMessageToServer(output);
			view.tbox.setText("XML wrote");
		});
		
		//read a XML File
		view.readXML.setOnAction(event -> {
			ArrayList<String> output = new ArrayList<String>();
			output.add("9");
			client.sendMessageToServer(output);
			view.tbox.setText("XML read");
		});
		
		// Watch the model for changing
		model.getValueProperty().addListener((obervable, oldValue, newValue) -> {
			sl.getLogger().info("ComputerLogicIsRunning");
			if (model.getPlayer() == HumanPlayer.Computer) {
				this.checkComputerPlayBevorWorkFlow(model.getXPos(), model.getYPos());
			}
		});

		// Watch the client for ChatMessage
		client.getChatMessageProperty().addListener((obervable, oldValue, newValue) -> {
			sl.getLogger().info("Message from Chat is arrived");
			view.chat.setScrollLeft(Double.MAX_VALUE);
			view.chat.appendText(client.getChatMessageInString());
			view.input.clear();
		});
		
		// Watch the client for Point
		client.getPointMessage().addListener((obervable, oldValue, newValue) -> {
			sl.getLogger().info("Message from PointSetter is arrived");
			view.points.setText(newValue);
		});

		//For the Quick Help
		
		
		view.QuickHelp.setOnAction(event -> {
			String helpText = "1.	Zuerst den TicTacToe_Server starten, dort den Button Go drücken, dann das Programm TicTacToeStart";
			helpText += "\n2.	Für den aller ersten Start muss unter User >> Reset Database die Datenbank bereit gestellt werden, das File wird im root-Verzeichnis abgelegt und heisst TicTacToe.mv.db";
			helpText += "\n3.	Zuerst unter User >> Change User einen Namen eingeben. Sollte dieser nicht eingegeben werden wird mit dem Default-user gearbeitet";
			helpText += "\n4.	Soll mit einem Computer-Gegner gespielt werden muss der Button Computer gedrückt werden. Dies muss nach jedem Neustart erneut gemacht werden, der Standard ist ohne Gegner";
			helpText += "\n5.	Die Chatfunktion reagiert mit dem Send-Button oder mit Enter. Doppelte Chat-Einträge werden nicht angezeigt ";
			helpText += "\n6.	Der Punktestand wird jeweils mit File >> New Game angezeigt. Sollte sich der Punktestand nicht ändern und es wird ein neues Spiel gestartet wird er nicht erneut angezeigt";
			helpText += "\n7.	Der hier verwendete Standard-Port ist vom Serverprogramm vorgeschlagen (5100)";
			helpText += "\n8.	Wenn der Spielstand exportiert oder importiert werden will, kann dies unter User >> Write XML / Read XML getätigt werden";
			helpText += "\n9.	Gewinnen tut wer drei gleiche Zeichen in einer Spalte, Linie, Diagonale oder Antidiagonale hat";
			helpText += "\n10.	Das Spiel kann über File >> Close Game beendet werden";
			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle("Quick Help");
			alert.setHeaderText("Qick Help");
			alert.setContentText(helpText);
			alert.showAndWait();
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
		ArrayList<String> messagePlay = new ArrayList<String>();
		messagePlay.add("1"); //Play-Message
		messagePlay.add(Integer.toString(i));
		messagePlay.add(Integer.toString(j));
		messagePlay.add(Integer.toString(getValueContructor()));
		client.sendMessageToServer(messagePlay);
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
			view.tbox.setText("We have a winner");
			view.tbox.setText("Finish");
			// write a winMessage to the server user: 1 for human 2 for computer and also the score from the model
			if(model.getPlayer() == HumanPlayer.Computer){
				model.setName("computer");
				model.setId(model.generateId(model.getName()));
			} 
			ArrayList<String> messageWin = new ArrayList<String>();
			messageWin.add("2"); //WinMessage
			messageWin.add(Integer.toString(model.getId()));
			messageWin.add(Integer.toString(model.getScore()));
			client.sendMessageToServer(messageWin);
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
		if(model.getName().equals("default") || model.getName().isEmpty()){
			model.setName("default");
		} else {
			model.setName(model.getChatName());
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
