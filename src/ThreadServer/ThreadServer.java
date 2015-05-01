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
				
				//System.out.println(commande);	
				
				//Creation du json reponse
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
						//Action
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
