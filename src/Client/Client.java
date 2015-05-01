package Client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;

import org.json.JSONException;
import org.json.JSONObject;

import beans.Acteur;

public class Client 
{
	private Socket socket;
	private PrintWriter out;
	private BufferedReader in;
	public Client()
	{
		try
		{
			socket = new Socket("localhost", 12345);
			out=new PrintWriter(socket.getOutputStream(),true);
			in=new BufferedReader(new InputStreamReader(socket.getInputStream()));
			Scanner clavier=new Scanner(System.in);
		}
		catch (Exception e) 
		{
			System.err.println("Client : "+e.getMessage());
		}
	}
	
	public ArrayList<Acteur> refresh()
	{
		JSONObject object=new JSONObject();
		try {
			object.put("commande", "Rafraichir");
			out.println(object);
			
			String response = in.readLine();
			System.out.println(response);
			
		} catch (JSONException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
		
	}
	
	public void quitter()
	{
		JSONObject object = new JSONObject();
		try{
			object.put("commande", "Quitter");
			out.println(object);
			
		} catch(JSONException e){
			e.printStackTrace();
		}
	}

}
