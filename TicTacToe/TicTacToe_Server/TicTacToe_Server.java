package TicTacToe_Server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Logger;

import javax.swing.plaf.synth.SynthSeparatorUI;

import TicTacToe_Client.TicTacToe_Model;
import TicTacToe_Server.DataBase.TicTacToe_H2;
import TicTacToe_Server.XML.TicTacToe_XML;
import TicTacToe_Server.XML.TicTacToe_XMLReader;
import javafx.concurrent.Task;

enum Value {
	Cross, Point, Empty, Danger
};

enum HumanPlayer {
	Human, Computer
};

enum Message {
	PlayMessage, WinMessage, ChatMessage, DBMesssage, XMLMessage, QuitMessage
};

public class TicTacToe_Server {

	public TicTacToe_Server() {
	}

	private Integer port;
	private final Logger logger = Logger.getLogger("");
	private

	final Task<Void> serverTask = new Task<Void>() {
		@Override
		protected Void call() throws Exception {
			ServerSocket listener = null;
			try {
				listener = new ServerSocket(port, 10, null);
				logger.info("Listening on port " + port);

				while (true) {
					// The "accept" method waits for a request, then creates a
					// socket
					// connected to the requesting client
					Socket clientSocket = listener.accept();

					ConnectionHandler client = new ConnectionHandler(clientSocket);
				}
			} catch (Exception e) {
				System.err.println(e);
			} finally {
				if (listener != null)
					listener.close();
			}
			return null;
		}
	};

	/**
	 * Called by the controller, to start the serverSocket task
	 */
	public void startServerSocket(Integer port) {
		this.port = port;
		new Thread(serverTask).start();
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
	private volatile boolean runner = true;

	// Logger
	Logger defaultLogger = Logger.getLogger("");

	public int getid() {
		return id;
	}

	public void setRunner(boolean bool) {
		this.runner = bool;
	}

	public ConnectionHandler(Socket socket) {
		this.socket = socket;
		Thread t = new Thread(this);
		t.start();
	}

	// goes open and wait for a message from the client
	@Override
	public void run() {
		while (runner) {
			try {
				ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
				Object object = ois.readObject();
				ArrayList<String> input = (ArrayList<String>) object;
				Integer election = inputTransformer(input);
				if (election == 1) {
					defaultLogger.info("Input: 1 - PlayInputs");
				}
				if (election == 2) {
					defaultLogger.info("Input: 2 - WinMessage");
				}
				if (election == 3) {
					String message = input.get(0);
					ArrayList<String> outmessageChat = new ArrayList<String>();
					outmessageChat.add("1"); // Chat
					outmessageChat.add(message);
					sendMessageToClient(outmessageChat);
					defaultLogger.info(message);
				}
				if (election == 4) {
					Integer income = Integer.parseInt(input.get(0));
					String answer = makeADBRequest(income);
					ArrayList<String> outmessagePoints = new ArrayList<String>();
					outmessagePoints.add("2"); // PointMessage
					outmessagePoints.add(answer);
					sendMessageToClient(outmessagePoints);
				}
				if (election == 5) {
					String closer = input.get(0);
					if (closer.equals("false")) {
						this.setRunner(false);
						run();
					}
				}
				if (election == 6) {
					defaultLogger.info("Insert done");
				}
				if (election == 7) {
					defaultLogger.info("Delete DB");
				}
				if (election == 8) {
					defaultLogger.info("External XML is written");
				}
				if (election == 9) {
					defaultLogger.info("XML reader done");
				}

				defaultLogger.info("Waiting for client message is...");
			} catch (IOException e) {
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
		}

	}

	/**
	 * Send the message back to the client
	 * 
	 * @param input
	 *            ArrayList<String>
	 */
	private void sendMessageToClient(ArrayList<String> input) {
		try {
			ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());

			oos.writeObject(input);

			// Actualize
			oos.flush();

		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private int inputTransformer(ArrayList<String> input) {
		int transformType = Integer.parseInt(input.get(0));
		input.remove(0);

		switch (transformType) {

		case 1: // Game Inputs
			getPlayInputs(input);
			return 1;
		case 2: // WinInputs
			try {
				getWinInputs(input);
			} catch (SQLException e) {
				e.printStackTrace();
			}
			this.safeInDB(id, points, player);
			return 2;
		case 3: // Chat
			return 3;
		case 4: // DB-Request
			return 4;
		case 5: // Quit Server
			return 5;
		case 6: // Insert Statement
			makeAInsertDB(input);
			return 6;
		case 7: // deleteDB
			deleteDB();
			return 7;
		case 8: // write an external XML for safe the game
			ArrayList<Game> xmlArryList = new ArrayList<Game>();
			try {
				xmlArryList = h2.selectPreparedStatementForXML("SELECT * FROM PERSON");
			} catch (SQLException e) {
				e.printStackTrace();
			}
			TicTacToe_XML xml = new TicTacToe_XML(xmlArryList);
			return 8;
		case 9: // read an XML File to DB
			TicTacToe_XMLReader reader = new TicTacToe_XMLReader();
			ArrayList<Game> gameListFromXML = new ArrayList<Game>();
			gameListFromXML = reader.readXML();
			h2.insertWithInputFromXML(gameListFromXML);
			return 9;
		}
		return transformType;
	}

	// method for the db-request give back the answer
	private String makeADBRequest(Integer income) {
		String answer = null;
		try {
			answer = h2.selectPreparedStatementForDisplayTheNameAndPoints(income);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return answer;
	}

	// Method for insert a new row
	private void makeAInsertDB(ArrayList<String> input) {
		int id = Integer.parseInt(input.get(0));
		String name = input.get(1);
		try {
			h2.insertWithPreparedStatement(id, name, 0);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	// Method for delete the db
	private void deleteDB() {
		h2.deleteDB();
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
	public void safeInDB(int id, int points, String name) {
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
				if (points == 0) {
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
	private void getPlayInputs(ArrayList<String> input) {
		Value[][] board = Board.getBoard();
		int x = Integer.parseInt(input.get(0));
		int y = Integer.parseInt(input.get(1));
		valueConstructor = Integer.parseInt(input.get(2));
		Value value;

		if (valueConstructor == 1) {
			value = Value.Cross;
		} else {
			value = Value.Point;
		}

		board[x][y] = value;
		defaultLogger.info("These are Inputs: " + x + " " + y + " " + value.toString());
	}

	/**
	 * for the win inputs - and transform the Integer-Array so the correct
	 * informations
	 * 
	 * @param arr
	 * @throws SQLException
	 */
	private void getWinInputs(ArrayList<String> input) throws SQLException {
		int user = Integer.parseInt(input.get(0));
		int tempPoints = Integer.parseInt(input.get(1));
		if (user != 879) { // Code for Computer
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
		defaultLogger.info("User: " + player + " Points: " + points);
	}

	public void xmlBuilder() {

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
