package be.dis.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import be.dis.pojo.Responsable;

public class DAO_Responsable extends DAO<Responsable> {

	public DAO_Responsable(Connection conn) { super(conn); }

	@Override
	public boolean create(Responsable obj) { return false; }

	@Override
	public boolean delete(Responsable obj) { return false; }

	@Override
	public boolean update(Responsable obj) { return false; }

	@Override
	public Responsable find(int id) {
		DAO_Categorie dao_categorie = new DAO_Categorie(DAO_Connection.getInstance());
		Responsable responsable = new Responsable();
		ResultSet result = null;
		try {
			PreparedStatement stm = connect.prepareStatement("SELECT * FROM Personne P "
					+ "INNER JOIN Responsable R on P.ID = R.ID "
					+ "WHERE ID = ?",
					ResultSet.TYPE_SCROLL_SENSITIVE,
					ResultSet.CONCUR_READ_ONLY);
			stm.setInt(1, id);
			result = stm.executeQuery();
			
			if(result.first()) {
				responsable.setId(id);
				responsable.setNom(result.getString("Nom"));
				responsable.setPrenom(result.getString("Prenom"));
				responsable.setLocalite(result.getString("localite"));
				responsable.setDate(result.getString("DateNaissance"));
				responsable.setEmail(result.getString("Email"));
				responsable.setMdp(result.getString("Mdp"));
				responsable.setCategorie(dao_categorie.find(result.getInt("IDCat")));
			}
			else
				responsable = null;
		}
		catch(SQLException e) { e.printStackTrace(); }
		return responsable;
	}

	public void setCat(Responsable resp) {
		DAO_Categorie dao_categorie = new DAO_Categorie(DAO_Connection.getInstance());
		ResultSet result = null;
		try {
			PreparedStatement stm = connect.prepareStatement("SELECT * FROM Responsable "
					+ "WHERE ID = ?",
					ResultSet.TYPE_SCROLL_SENSITIVE,
					ResultSet.CONCUR_READ_ONLY);
			stm.setInt(1, resp.getId());
			result = stm.executeQuery();
			
			if(result.first()) {
				resp.setCategorie(dao_categorie.find(result.getInt("IDCat")));
			}
			else {
				System.out.println("Catégorie non trouvée.");
			}
		}
		catch(SQLException e) { e.printStackTrace(); }
	}
}
