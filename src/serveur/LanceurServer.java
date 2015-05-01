package serveur;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

import org.json.JSONObject;

//import com.ingesup.java.cliserv.Serveur2;

import ThreadServer.ThreadServer;

public class LanceurServer 
{
	ThreadServer thread;
	public LanceurServer()
	{
		//// écoute du serveur
		ServerSocket ss;
		/*PrintWriter out;
		BufferedReader in;*/
		
		try {
			
			ss = new ServerSocket(12345);

			System.out.println("Serveur en écoute...");
			// un client se connecte Action bloquant
			Socket socket = ss.accept();
			
			System.out.println("Un client est connecté !");
		
			//Appelle du thread
			thread = new ThreadServer(socket);

		
		} catch (Exception e) {
			System. err .println("Serveur : "+e.getMessage());
		}
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		new LanceurServer();
	}
}
