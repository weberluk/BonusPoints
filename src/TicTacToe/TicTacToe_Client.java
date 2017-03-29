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
	
	public String getChatMessageInString() {
		return chatMessage.get();
	}

	

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

			// close reader
			// oos.close();

		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

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

			// close reader
			// oos.close();

		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void writeChatMessageToServer(String message) {
		try {
			// Streams

			// OutputStream writing
			ObjectOutputStream oos = new ObjectOutputStream(client.getOutputStream());

			oos.writeObject(message);

			// Actualize
			oos.flush();

			// close reader
			// oos.close();

		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

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
