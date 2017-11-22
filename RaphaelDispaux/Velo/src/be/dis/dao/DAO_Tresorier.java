package be.dis.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import be.dis.pojo.Tresorier;

public class DAO_Tresorier extends DAO<Tresorier> {

	public DAO_Tresorier(Connection conn) { super(conn); }

	@Override
	public boolean create(Tresorier obj) { return false; }

	@Override
	public boolean delete(Tresorier obj) { return false; }

	@Override
	public boolean update(Tresorier obj) { return false; }

	@Override
	public Tresorier find(int id) {
		Tresorier tresorier = new Tresorier();
		ResultSet result = null;
		try {
			PreparedStatement stm = connect.prepareStatement("SELECT * FROM Personne P "
					+ "INNER JOIN Tresorier T on P.ID = T.ID "
					+ "WHERE ID = ?",
					ResultSet.TYPE_SCROLL_SENSITIVE,
					ResultSet.CONCUR_READ_ONLY);
			stm.setInt(1, id);
			result = stm.executeQuery();
			
			if(result.first()) {
				tresorier.setId(id);
				tresorier.setNom(result.getString("Nom"));
				tresorier.setPrenom(result.getString("Prenom"));
				tresorier.setLocalite(result.getString("localite"));
				tresorier.setDate(result.getString("DateNaissance"));
				tresorier.setEmail(result.getString("Email"));
				tresorier.setMdp(result.getString("Mdp"));
			}
			else
				tresorier = null;
		}
		catch(SQLException e) { e.printStackTrace(); }
		return tresorier;
	}

}
