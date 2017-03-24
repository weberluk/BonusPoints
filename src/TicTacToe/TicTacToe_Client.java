package TicTacToe;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

import TicTacToe.TicTacToe_Model.Value;
import TicTacToe.TicTacToe_Model;
import TicTacToe.TicTacToe_Controller;

public class TicTacToe_Client {
	private TicTacToe_Controller controller;
	private ServiceLocator sl = ServiceLocator.getServiceLocator();
	
	public TicTacToe_Client(){
	}
	public void setController(TicTacToe_Controller controller) {
		this.controller = controller;
	}
	
	public void writePlayMessageToServer(int x, int y, int valueConstructor){

		try {
			Socket client = new Socket("localhost", 5555);
			sl.getLogger().info("Client gestartet, localhost, Port: 5555");
			
			//Streams
			
			//OutputStream writing
			ObjectOutputStream oos = new ObjectOutputStream(client.getOutputStream());
			Integer[] message = {x,y,valueConstructor};
			
			//Input from client
			InputStream input = client.getInputStream();
			BufferedReader reader = new BufferedReader(new InputStreamReader(input));
			//---------------------------------
			
			oos.writeObject(message);
			
			//Actualize
			oos.flush();
			
			//close reader
			oos.close();
			
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void writeWinMessageToServer(int user, int points){
		try {
			Socket client = new Socket("localhost", 5555);
			System.out.println("Client gestartet!");
			
			//Streams
			
			//OutputStream writing
			ObjectOutputStream oos = new ObjectOutputStream(client.getOutputStream());
			Integer[] message = {user, points};
			
			//Input from client
			InputStream input = client.getInputStream();
			BufferedReader reader = new BufferedReader(new InputStreamReader(input));
			//---------------------------------
			
			oos.writeObject(message);
			
			//Actualize
			oos.flush();
			
			//close reader
			oos.close();
			
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}

class Point {
	int x,y;
	Value value;
	
	public Point(int x, int y, Value value){
		this.x = x;
		this.y = y;
		this.value = value;
	}
}
