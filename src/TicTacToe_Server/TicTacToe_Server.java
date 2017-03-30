package TicTacToe_Server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.SQLException;

import javax.swing.plaf.synth.SynthSeparatorUI;

import Lotto.ServiceLocator;
import TicTacToe.TicTacToe_Model;

enum Value {
	Cross, Point, Empty, Danger
};

enum HumanPlayer {
	Human, Computer
};

public class TicTacToe_Server {

	// socket and port
	private ServerSocket server;
	private int port = 5555;

	public TicTacToe_Server() {
		try {
			server = new ServerSocket(port);
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public static void main(String[] args) {

		TicTacToe_Server example = new TicTacToe_Server();
		example.handleConnection();
	}

	private void handleConnection() {
		System.out.println("Waiting for client message got...");

		while (true) {
			try {
				Socket socket = server.accept();
				new ConnectionHandler(socket);
			} catch (IOException e) {
				e.printStackTrace();
			}

		}

	}

}

class ConnectionHandler implements Runnable {
	private TicTacToe_H2 h2 = TicTacToe_H2.getDB();
	private TicTacToe_Model model = TicTacToe_Model.getModel();
	private Socket socket;
	private int valueConstructor;
	private String player;
	private int points;
	private int id;

	public int getid() {
		return id;
	}

	public ConnectionHandler(Socket socket) {
		this.socket = socket;
		Thread t = new Thread(this);
		t.start();
	}

	// goes open and wait for a message from the client
	@Override
	public void run() {
		while (true) {
			try {
				ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
				ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
				Object object = ois.readObject();
				if (testObject(object) == 1) {
					Integer[] message = (Integer[]) object;
					printArray(message);
					if (message.length == 2){
						getWinInputs(message);
						printXML(id, points, player);
						this.safeInDB(id, player, points);
					}
					if (message.length == 3) {
						getPlayInputs(message);
					}
				}
				if (testObject(object) == 2) {
					String message = (String) object;
					out.writeObject(object);
					System.out.println(message);
				}
				System.out.println("Waiting for client message is...");
			} catch (IOException e) {
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

	}

	/**
	 * This method controls the incomes if it a Integer-Array it must be a
	 * Ingame-Message - two possibles: normal game message or a winner message
	 * if it a String it must be a chat-message
	 * 
	 * @param value
	 *            - the incoming Object from the object-reader
	 * @return - 1 for ingame - 2 for chat
	 */
	public int testObject(Object value) {
		if (value.getClass() == Integer[].class) {
			return 1;
		} else {
			if (value.getClass() == String.class) {
				return 2;
			}
		}
		return id;
	}

	private void printArray(Integer[] arr) {
		for (Integer i : arr) {
			System.out.println(i);
		}
	}

	/**
	 * prints a XML for external
	 * 
	 * @param id
	 *            - the generate id from the model
	 * @param points
	 *            - the points from the model
	 * @param player
	 *            - the player from the model
	 */
	public void printXML(int id, int points, String player) {
		TicTacToe_XMLWriter xml = new TicTacToe_XMLWriter();
		xml.writeXML(id, points, player);
	}

	/**
	 * safes the wins in the DB for internal
	 * 
	 * @param id
	 *            - the generate id from the model
	 * @param points
	 *            - the points from the model
	 * @param player
	 *            - the player from the model
	 */
	public void safeInDB(int id, String name, int points) {
		TicTacToe_H2 h2 = TicTacToe_H2.getDB();

		try {
			// is the id in the DB?
			if (h2.isTheEntryThere(id)) {
				// get old points
				int oldPoints = h2.selectPreparedStatementPoints(id);
				// summ the points
				int newPoints = oldPoints + points;
				// set the new points (calculatet)
				h2.updateWithPreparedStatement("update PERSON set points =" + newPoints);
			} else {
				// when the name is null
				if (name == "noName") {
					name = model.getName();
				}
				// when the id is null
				if (id == 0) {
					id = model.getId();
				}
				if(points == 0){
					points = model.getScore();
				}
				// create a new row in the DB
				h2.insertWithPreparedStatement(id, name, points);

			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * for the normal play-inputs set the board entry and also the sign on the
	 * board for more function on the server
	 * 
	 * @param arr
	 *            - incoming Integer-Array
	 */
	private void getPlayInputs(Integer[] arr) {
		Value[][] board = Board.getBoard();
		int x = arr[0];
		int y = arr[1];
		valueConstructor = arr[2];
		Value value;

		if (valueConstructor == 1) {
			value = Value.Cross;
		} else {
			value = Value.Point;
		}

		board[x][y] = value;
		System.out.println("These are Inputs: " + x + " " + y + " " + value.toString());
	}

	/**
	 * for the win inputs - and transform the Integer-Array so the correct
	 * informations
	 * 
	 * @param arr
	 * @throws SQLException
	 */
	private void getWinInputs(Integer[] arr) throws SQLException {
		int user = arr[0];
		int tempPoints = arr[1];
		if (user != 879) { //Code for Computer 
			if (h2.isTheEntryThere(user)) {
				player = h2.selectPreparedStatementName(user);
				id = user;
				points = tempPoints;
			} else {
				player = "noName";
				id = user;
				points = tempPoints;
			}
		} else {
			player = "computer";
			model.setName("computer");
			id = model.generateId(model.getName());
			
		}
		System.out.println("User: " + player + " Points: " + points);
	}

}

class Board {
	private static Value[][] board; // singleton

	public Board() {
	}

	/**
	 * Factory method for returning the singleton board
	 * 
	 * @param mainClass
	 *            The main class of this program
	 * @return The singleton resource locator
	 */
	public static Value[][] getBoard() {
		if (board == null)
			board = new Value[3][3];
		return board;
	}

}
