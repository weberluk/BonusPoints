package TicTacToe;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

public class TicTacToe_Client {
	
	public static void main(String[] args){
		
		try {
			Socket client = new Socket("localhost", 5555);
			System.out.println("Client gestartet!");
			
			//Streams
			
			//OutputStream writing
			OutputStream out = client.getOutputStream();
			PrintWriter writer = new PrintWriter(out);
			
			//Input from client
			InputStream input = client.getInputStream();
			BufferedReader reader = new BufferedReader(new InputStreamReader(input));
			//---------------------------------
			
			writer.write("Hallo Server\n");
			
			//Actualize
			writer.flush();
			
			String s = null;
			
			while ((s = reader.readLine()) != null) {
				System.out.println("Empfang vom Server: " + s);
			}
			
			reader.close();
			writer.close();
			
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
