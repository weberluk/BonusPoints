package TicTacToe;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.sql.SQLException;
import java.util.ArrayList;

import javafx.beans.property.SimpleStringProperty;
import TicTacToe.TicTacToe_Controller;

public class TicTacToe_Client implements Runnable {
	private TicTacToe_Controller controller;
	private ServiceLocator sl = ServiceLocator.getServiceLocator();
	private Socket client;
	private boolean closer = true;
	private String adress = "localhost";
	private int port = 5555;

	// SimpleStringProperty for overwatching the chat
	private SimpleStringProperty chatMessage = new SimpleStringProperty();

	private SimpleStringProperty pointMessage = new SimpleStringProperty();

	public TicTacToe_Client() throws UnknownHostException, IOException {
		client = new Socket(adress, port);
		sl.getLogger().info("Client gestartet, " + adress + ", Port:" + port);
		Thread t = new Thread(this);
		t.start();
	}
	
	public void setAdress(String adress){
		this.adress = adress;
	}
	public void setPort (int port){
		this.port = port;
	}
	
	public void setCloser(boolean closer) {
		this.closer = closer;
	}

	public void setController(TicTacToe_Controller controller) {
		this.controller = controller;
	}

	// Get the MessageProperty from the chat
	public SimpleStringProperty getChatMessageProperty() {
		return this.chatMessage;
	}

	// Get the MessageProperty from the chat
	public void setChatMessageProperty(String newValue) {
		try {
			this.chatMessage.setValue(newValue);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	// Get the MessageProperty from the chat
	public SimpleStringProperty getPointMessage() {
		return this.pointMessage;
	}

	// Get the MessageProperty from the chat
	public void setPointMessage(String newValue) {
		try {
			this.pointMessage.setValue(newValue);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	// transform the simpleStringProperty to a normal string to give it back to
	// the controller and then to the view
	public String getChatMessageInString() {
		return chatMessage.get();
	}

	// transform the simpleStringProperty to a normal string to give it back to
	// the controller and then to the view
	public String getPointMessageInString() {
		return pointMessage.get();
	}

	/**
	 * Send the message to the server becomes a object
	 * 
	 * @param object
	 */
	public void sendMessageToServer(ArrayList<String> input) {
		try {
			// OutputStream writing
			ObjectOutputStream oos = new ObjectOutputStream(client.getOutputStream());

			// ---------------------------------
			oos.writeObject(input);

			// Actualize
			oos.flush();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Client goes open an wait for a message back from the server to give it to
	 * a simpleStringProperty the property is overwatching and send it to the
	 * controller and view
	 */
	@Override
	public void run() {
		while (closer) {
			try {
				ObjectInputStream ois = new ObjectInputStream(client.getInputStream());
				Object object = ois.readObject();
				ArrayList<String> input = (ArrayList<String>) object;
				Integer election = inputTransformer(input);
				if (election == 1) {
					sl.getLogger().info("IncomingMessage on Client Type 1 - For Chat");
				}
				if (election == 2) {
					sl.getLogger().info("IncomingMessage on Client Type 2 - For Points");
				}
			} catch (IOException e) {
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
		}
	}
	
	//for check the input
	private int inputTransformer(ArrayList<String> input) {
		int transformType = Integer.parseInt(input.get(0));
		input.remove(0);

		switch (transformType) {

		case 1: // Chat
			String chatMessage = (String) input.get(0);
			this.chatMessage.setValue(chatMessage);
			return 2;
		case 2: // PointMessage		
			this.pointMessage.setValue(getPointMessage(input));
			return 1;
		}
		return transformType;
	}

	public String getPointMessage(ArrayList<String> object) {
		String message = object.get(0);
		return message;
	}
}
