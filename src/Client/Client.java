package Client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import beans.Acteur;
import beans.Genre;

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
		ArrayList<Acteur> listeActeurs = new ArrayList<Acteur>();
		Acteur actor;
		//Création d'une variable json object pour récuperer les données de chaque acteurs
		JSONObject acteurJson = new JSONObject();
		try {

			//Envoi de la commande rafraichir
			object.put("commande", "Rafraichir");
			out.println(object);

			//Recuperation des données en json array
			JSONArray jsonArray = new JSONArray(in.readLine());
					
			//Recupération des objets json à partir du array json
			for (int i = 0; i < jsonArray.length(); i++)
			{
				acteurJson = jsonArray.getJSONObject(i);
				actor = new Acteur(acteurJson.getInt("id"), acteurJson.getString("nomActeur"), acteurJson.getString("prenomActeur"));
				listeActeurs.add(actor);
			}
							
		} catch (JSONException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return listeActeurs;
		
	}
	public Acteur infos(int id)
	{
		JSONObject objet = new JSONObject();
		Acteur acteur = null;
		Genre sexe = null;
		
		try
		{
			objet.put("commande", "Infos");
			objet.put("id", id);
			
			System.out.println(objet.get("commande"));
			
			out.println(objet);
			
			JSONObject actor = new JSONObject(in.readLine());
			
			sexe = actor.getInt("sexe") == 1?Genre.Feminin:Genre.Masculin;			
			acteur = new Acteur(actor.getInt("id"), actor.getString("nomActeur"), actor.getString("prenomActeur"), sexe, actor.getInt("anneeNaiss"));
			
			
		} catch (JSONException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return acteur;
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
