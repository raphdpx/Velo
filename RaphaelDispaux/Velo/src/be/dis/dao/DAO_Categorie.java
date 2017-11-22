package be.dis.dao;

import java.sql.*;
import be.dis.pojo.Categorie;
import be.dis.pojo.Cyclo;
import be.dis.pojo.Descente;
import be.dis.pojo.Rando;
import be.dis.pojo.Trial;

public class DAO_Categorie extends DAO<Categorie> {
	
	public DAO_Categorie(Connection conn) { super(conn); }

	@Override
	public boolean create(Categorie obj) { return false; }

	@Override
	public boolean delete(Categorie obj) { return false; }

	@Override
	public boolean update(Categorie obj) { return false; }

	@Override
	public Categorie find(int id) {
		Categorie cat = new Categorie();
		try {
			PreparedStatement stm = connect.prepareStatement("SELECT Nom FROM Categorie WHERE ID = ?",
					 ResultSet.TYPE_SCROLL_SENSITIVE,
					 ResultSet.CONCUR_READ_ONLY);
			stm.setInt(1, id);
			ResultSet result = stm.executeQuery();
			if(result.first()) {
				String nom = result.getString("Nom");
				switch(nom) {
				case "Cyclo" :
					cat = new Cyclo();
					cat.setId(4);
					break;
				case "Vtt Trial" :
					cat = new Trial();
					cat.setId(1);
					break;
				case "Vtt Descente" :
					cat = new Descente();
					cat.setId(2);
					break;
				case "Vtt Randonnee" :
					cat = new Rando();
					cat.setId(3);
					break;
				}
			}
			else
				return null;
		}
		catch(SQLException e) { e.printStackTrace(); }
		return cat;
	}

}
