package ThreadServer;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.sql.ResultSet;
import java.sql.SQLException;

import ConnectionServerMysql.*;
import jdk.nashorn.internal.parser.JSONParser;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

//import com.ingesup.b3.mysql.launches.Genre;

public class ThreadServer
{	
	PrintWriter out;
	BufferedReader in;
	public ThreadServer(Socket ss)
	{
		try
		{
			// flux d'écriture et de lecture
			out=new PrintWriter(ss.getOutputStream(),true);
			in=new BufferedReader(new InputStreamReader(ss.getInputStream()));
			MysqlB3B con = new MysqlB3B("films");
			ResultSet rs ;
			while(true)
			{
				JSONObject object = new JSONObject(in.readLine());
				//Recuperation de la commande 
				String commande = object.getString("commande");
				
						
				//Creation du json réponse
				JSONArray arrayRep = new JSONArray();
				
				if (commande.equals("Rafraichir"))
				{
					//Creation 
					rs = con.select("acteurs");
					
					while(rs.next())
					{
						//Appelle de la function creationJsonObjectActeurs et ajoute de chaque acteurs
						arrayRep.put(creationJsonObjetActeurs(rs));
					}
					
					out.println(arrayRep.toString());
				}
				else
				{
					if(commande.equals("Infos"))
					{
						
						JSONObject acteur = new JSONObject();
						int id = Integer.parseInt(object.getString("id"));
						
						//Appelle SQL
						rs = con.select("acteurs");
						
						
						/* A VOIR AVEC LE PROF PB DANS LA REQUETE SQL
						 * Pk pas faire un tableau qui regroupe tout les entrées de la base de données pour maximiser les appelles à la base
						rs = con.select("acteurs where codeActeur = "+ id);
						acteur = creationJsonObjetActeurs(rs);
						*/
						
						//Parcours du resultset
						while(rs.next())
						{
							//Si le code acteurs est égale à l'id sélectionné
							if(rs.getInt("codeActeur") == id)
							{
								acteur = creationJsonObjetActeurs(rs);
							}
						}
						
						//System.out.println(acteur.toString());
						
						//Envoi de l'acteur
						out.println(acteur.toString());
					}
					else
					{
						if (commande.equals("Quitter"))
						{
							System.out.println("Le serveur a quitté l'application !");
							ss.close();
						}
					}
				}
				
			}
			
		}catch (Exception e) {
			System. err .println("Serveur : "+e.getMessage());
		}
				
	}
	
	private JSONObject creationJsonObjetActeurs(ResultSet rs)
	{
		JSONObject objectRep = new JSONObject();
		
		try {
			objectRep.put("id", Integer.toString(rs.getInt("codeActeur")));
			objectRep.put("nomActeur", rs.getString("nomActeur"));
			objectRep.put("prenomActeur", rs.getString("prenomActeur"));
			objectRep.put("sexe", rs.getInt("sexeActeur"));
			objectRep.put("anneeNaiss", rs.getInt("anneeNaissanceActeur"));
						
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return objectRep;
	}
}
