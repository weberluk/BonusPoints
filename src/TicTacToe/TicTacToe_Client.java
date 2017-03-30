package TicTacToe;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

import javafx.beans.property.SimpleStringProperty;
import TicTacToe.TicTacToe_Controller;

public class TicTacToe_Client implements Runnable {
	private TicTacToe_Controller controller;
	private ServiceLocator sl = ServiceLocator.getServiceLocator();
	private Socket client;
	
	// SimpleBooleanProperty for overwatching the chat
	private SimpleStringProperty chatMessage = new SimpleStringProperty();
	
	public TicTacToe_Client() throws UnknownHostException, IOException {
		client = new Socket("localhost", 5555);
		sl.getLogger().info("Client gestartet, localhost, Port: 5555");
		Thread t = new Thread(this);
		t.start();
	}

	public void setController(TicTacToe_Controller controller) {
		this.controller = controller;
	}
	
	//Get the MessageProperty from the chat
	public SimpleStringProperty getChatMessageProperty() {
		return this.chatMessage;
	}
	//Get the MessageProperty from the chat
	public void setChatMessageProperty(String newValue) {
		try {
			this.chatMessage.setValue(newValue);
		} catch (Exception e){
			e.printStackTrace();
		}

	}
	
	// transform the simpleStringProperty to a normal string to give it back to the controller and then to the view
	public String getChatMessageInString() {
		return chatMessage.get();
	}

	
	/**
	 * This methode sends only play-informations to the server and this hidden-board
	 * the info goes with a Integer-Array and 3 params
	 * @param x - for the x-coordinate
	 * @param y - for the y-coordinate
	 * @param valueConstructor - which value is given X / O
	 */
	public void writePlayMessageToServer(int x, int y, int valueConstructor) {

		try {
			// Streams

			// OutputStream writing
			ObjectOutputStream oos = new ObjectOutputStream(client.getOutputStream());
			Integer[] message = { x, y, valueConstructor };

			// ---------------------------------
			oos.writeObject(message);

			// Actualize
			oos.flush();

		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * This methode sends a win-message to the server - this is also for the DB
	 * the info goes with a Integer-Array and 2 params
	 * @param user - which user is the winner
	 * @param points - how many points for winning
	 */
	public void writeWinMessageToServer(int user, int points) {
		try {
			// Streams

			// OutputStream writing
			ObjectOutputStream oos = new ObjectOutputStream(client.getOutputStream());
			Integer[] message = { user, points };
			// ---------------------------------

			oos.writeObject(message);

			// Actualize
			oos.flush();

		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * For the chatting - send a message to the chat
	 * This information goes with a string
	 * @param message - normal string
	 */
	public void writeChatMessageToServer(String message) {
		try {
			// Streams

			// OutputStream writing
			ObjectOutputStream oos = new ObjectOutputStream(client.getOutputStream());

			oos.writeObject(message);

			// Actualize
			oos.flush();

		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Client goes open an wait for a message back from the server to give it to a simpleStringProperty
	 * the property is overwatching and send it to the controller and view
	 */
	@Override
	public void run() {
		while (true) {
			try {
				ObjectInputStream ois = new ObjectInputStream(client.getInputStream());
				Object object = ois.readObject();
				String chatMessage = (String) object;
				this.chatMessage.setValue(chatMessage);
			} catch (IOException e) {
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
		}
	}
}
