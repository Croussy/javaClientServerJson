package ConnectionServerMysql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;

public class MysqlB3B {
	private Connection connection;

	/**
	 * <b>Constructeur complet</b><br>
	 * Instancie l'objet MysqlB3B avec tous les param�tres
	 * @param ip (String) : l'ip ou le domain name
	 * @param base (String) : la base banane
	 * @param user (String) : le nom d'utilisateur reconnu par mysql
	 * @param pass (String) : son password
	 */
	public MysqlB3B(String ip, String base,String user,String pass)
	{
		try {
			// Chargement du driver
			String driver = "com.mysql.jdbc.Driver";
			Class.forName(driver);
			// Connexion
			String url = "jdbc:mysql://"+ip+"/"+base;
			connection=DriverManager.getConnection( url, user, pass );
		}
		catch( Exception e ) {
			System.err.println(e.getMessage());
		}
	}

	public MysqlB3B(String base)
	{
		this("localhost",base,"root","root");
	}

	public ResultSet select(String sql)
	{
		ResultSet res=null;
		try {
			if(!sql.contains(" "))
				sql="SELECT * FROM "+sql;
			Statement instruction = connection.createStatement();
			res = instruction.executeQuery(sql);
		}
		catch( Exception e ) {
			System.err.println(e.getMessage());
		}
		return res;
	}

	public int update(String sql)
	{
		int res=-1;
		try {
			Statement instruction = connection.createStatement();
			res = instruction.executeUpdate(sql);
		}
		catch( Exception e ) {
			System.err.println(e.getMessage());
		}
		return res;
	}
	
	public String[][] selectTable(String sql)
	{
		String[][] res=null;
		try {
			ResultSet rs=select(sql);
			ResultSetMetaData rsmd=rs.getMetaData();
			int nbCol=rsmd.getColumnCount();
			rs.last();
			int nbLig=rs.getRow();
			res=new String[nbLig+1][nbCol];
			// les �tiquettes
			for(int i=0;i<nbCol;i++)
				res[0][i]=rsmd.getColumnName(i+1);
			// les donn�es
			int i=1;
			rs.beforeFirst();
			while(rs.next())
			{
				for(int j=0;j<nbCol;j++)
					res[i][j]=rs.getString(j+1);
				i++;
			}
		}
		catch( Exception e ) {
			System.err.println(e.getMessage());
		}
		return res;
	}
}