package TicTacToe_Server;

import java.io.BufferedReader;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import TicTacToe.TicTacToe_View;


enum Value {
	Cross, Point, Empty, Danger
};

enum HumanPlayer {
	Human, Computer
};


public class TicTacToe_Server {

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
	private Socket socket;
	int valueConstructor;
	String player;
	int points;
	int id;
	
	public ConnectionHandler(Socket socket){
		this.socket = socket;
		Thread t = new Thread(this);
		t.start();
	}
	
	@Override
	public void run() {
		try{
			ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
			PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
			Object object = ois.readObject();
			if(testObject(object) == 1){
				Integer[] message = (Integer[]) object;
				printArray(message);
				if(message.length == 3){
					getPlayInputs(message);
				} else {
					getWinInputs(message);
					printXML(id,points, player);
					this.safeInDB(id, player, points);
				}
			}
			if(testObject(object) == 2){
				String message = (String) object;
				out.println(message);
				System.out.println(message);
			}
			ois.close();
			socket.close();
			System.out.println("Waiting for client message is...");
		} catch (IOException e){
			e.printStackTrace();
		} catch (ClassNotFoundException e){
			e.printStackTrace();
		}
		
	}
	
	public int testObject(Object value){
		if(value.getClass() == Integer[].class){
			return 1;
		} else {
			if(value.getClass() == String.class){
				return 2;
			}
		}
		return id;
	}
	
//	private void b(Integer message) {
//		List<Integer> list = new ArrayList<Integer>();
//		Integer[] arr = list.toArray(new Integer[0]);
//		System.out.println("Array is " + Arrays.toString(arr));
//	}


	private void printArray(Integer[] arr) {
		for (Integer i : arr){
			System.out.println(i);
		}
	}
	
	public void printXML(int id, int points, String player){
		TicTacToe_XMLWriter xml = new TicTacToe_XMLWriter();
		xml.writeXML(id,points,player);
	}
	
	public void safeInDB(int id, String name, int points){
		TicTacToe_H2 h2 = new TicTacToe_H2();
		
		try {
			if(h2.isTheEntryThere("select * from PERSON where id =" + id)){
				int oldPoints = Integer.parseInt(h2.selectPreparedStatement("select * from PERSON where id =" + id));
				int newPoints = oldPoints + points;
				h2.updateWithPreparedStatement("update PERSON set points =" + newPoints);
			} else {
				h2.insertWithPreparedStatement(id, name, points);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	private void getPlayInputs(Integer[] arr){
		Value[][] board = Board.getBoard();
		int x = arr[0];
		int y = arr[1];
		valueConstructor = arr[2];
		Value value;
		
		if(valueConstructor == 1){
			value = Value.Cross;
		} else {
			value = Value.Point;
		}
		
		board[x][y] = value;
		
		System.out.println("These are Inputs: " + x + " " + y + " " + value.toString() );
	}
	
	private void getWinInputs(Integer[] arr){
		TicTacToe_H2 h2 = new TicTacToe_H2();
		
		int user = arr[0];
		int tempPoints = arr[1];
		int tempPoints1 = 0;
		try {
			tempPoints1 = Integer.parseInt(h2.selectPreparedStatement("select points from PERSON"));
		} catch (NumberFormatException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		points = tempPoints + tempPoints1;
		
		if(user == 1){
			player = "Human";
			id = 1;
		} else {
			player = "Computer";
			id = 2;
		}
		
		System.out.println("User: " + player + " Points: " + points);
	}
	
}

class Board {
	private static Value[][] board; //singleton
	
	public Board(){
	}
	
    /**
     * Factory method for returning the singleton
     * @param mainClass The main class of this program
     * @return The singleton resource locator
     */
    public static Value[][] getBoard() {
        if (board == null)
            board = new Value[3][3];
        return board;
    }
    
}

//class Point {
//	int x,y;
//	Value value;
//	
//	public Point(int x, int y, Value value){
//		this.x = x;
//		this.y = y;
//		this.value = value;
//	}
//	
//	public int getX(){
//		return this.x;
//	}
//	public int getY(){
//		return this.y;
//	}
//	public Value getValue(){
//		return value;
//	}
//}
