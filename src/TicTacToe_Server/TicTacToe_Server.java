package TicTacToe_Server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class TicTacToe_Server {
	
	public static void main (String[] args) {
		
		try {
			ServerSocket server = new ServerSocket(5555);
			System.out.println("Server gestartet!");
			
			Socket client = server.accept();
			
			//Streams
			
			//OutputStream writing
			OutputStream out = client.getOutputStream();
			PrintWriter writer = new PrintWriter(out);
			
			//Input from client
			InputStream input = client.getInputStream();
			BufferedReader reader = new BufferedReader(new InputStreamReader(input));
			//---------------------------------
			
			String s = null;
			
			while ((s = reader.readLine()) != null) {
				writer.write(s + "\n");
				writer.flush();
				System.out.println("Empfang vom Client: " + s);
			}
			
			writer.close();
			reader.close();
			
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
