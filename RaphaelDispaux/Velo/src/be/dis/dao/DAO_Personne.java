package be.dis.dao;

import java.sql.*;

import be.dis.pojo.*;

public class DAO_Personne extends DAO<Personne> {
	
	public DAO_Personne(Connection conn) {
		super(conn);
	}
	
	@Override
	public boolean create(Personne obj) {
		boolean bool = false;
		PreparedStatement stm = null;
		ResultSet result = null;
		try {
			stm = connect.prepareStatement(
					  "SELECT Nom, Prenom "
					  + "FROM Personne "
					  + "WHERE Nom = ? AND Prenom = ?");
			stm.setString(1, obj.getNom());
			stm.setString(2, obj.getPrenom());
			result = stm.executeQuery();
			if(result.first())
				System.out.println("Cette personne est déjà inscrite.");
			else {
				stm = connect.prepareStatement(
					  "INSERT INTO "
					+ "		Personne(Nom,"
					+ 			"Prenom,"
					+ 			"Localite, "
					+ 			"DateNaissance, "
					+ 			"Email, "
					+ 			"Mdp)"
					+ "VALUES(?, ?, ?, ?, ?, ?)");
				stm.setString(1, obj.getNom());
				stm.setString(2, obj.getPrenom());
				stm.setString(3, obj.getLocalite());
				stm.setString(4, obj.getDate());
				stm.setString(5, obj.getEmail());
				stm.setString(6, obj.getMdp());
				stm.executeUpdate();
			
				result = stm.getGeneratedKeys();
			
				while(result.next()) {
					obj.setId(result.getInt(1));
				}
				
				bool = true;
			}
		}
		catch(SQLException e){
			e.printStackTrace();
		}
		return bool;
	}
	
	@Override
	public boolean delete(Personne obj) {
		try {
			PreparedStatement stm = connect.prepareStatement("DELETE FROM Personne WHERE ID = ?");
			stm.setInt(1, obj.getId());
			stm.executeUpdate();
			return true;
		}
		catch(SQLException e) { 
			e.printStackTrace();
			return false;
		}
	}
	
	public boolean update(Personne obj) {
		PreparedStatement stm = null;
		try {
			stm = connect.prepareStatement("UPDATE Personne "
					+ "SET Nom = ?, "
					+ "Prenom = ?, "
					+ "Localite = ?, "
					+ "DateNaissance = ?, "
					+ "Email = ?, "
					+ "Mdp = ? "
					+ "WHERE ID = " + obj.getId());
			stm.setString(1, obj.getNom());
			stm.setString(2, obj.getPrenom());
			stm.setString(3, obj.getLocalite());
			stm.setString(4, obj.getDate());
			stm.setString(5, obj.getEmail());
			stm.setString(6, obj.getMdp());
			stm.executeUpdate();
			return true;
		}
		catch(SQLException e) { 
			e.printStackTrace();
			return false;
		}	
	}
	
	public Personne find(int id) {
		Personne personne = new Personne();
		ResultSet result = null;
		try {
			PreparedStatement stm = connect.prepareStatement("SELECT * FROM Personne P WHERE ID = ?", ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
			result = stm.executeQuery();
			
			if(result.first()) {
				personne.setId(id);
				personne.setNom(result.getString("Nom"));
				personne.setPrenom(result.getString("Prenom"));
				personne.setLocalite(result.getString("localite"));
				personne.setDate(result.getString("DateNaissance"));
				personne.setEmail(result.getString("Email"));
				personne.setMdp(result.getString("Mdp"));
			}
			else
				personne = null;
		}
		catch(SQLException e) { e.printStackTrace(); }
		return personne;
	}
	
	public Personne connexion(String nom, String prenom, String mdp) {
		Personne p;
		PreparedStatement stmt = null;
		ResultSet result = null;
		try {
			stmt = connect.prepareStatement("SELECT * FROM Personne WHERE nom = ? AND prenom = ? AND mdp = ?",
					ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
			stmt.setString(1, nom);
			stmt.setString(2, prenom);
			stmt.setString(3, mdp);
			result = stmt.executeQuery();
			if(result.first()) {
				String p_nom = result.getString("Nom");
				String p_prenom = result.getString("Prenom");
				String p_date = result.getString("DateNaissance");
				String p_mail = result.getString("Email");
				int p_ID = result.getInt("ID");
				DAO_Membre dao_m = new DAO_Membre(DAO_Connection.getInstance());
				Membre m = dao_m.find(p_ID);
				if(m!=null) {
					m.setNom(p_nom);
					m.setPrenom(p_prenom);
					m.setDate(p_date);
					m.setEmail(p_mail);
					m.setId(p_ID);
					m.setMdp(mdp);
					return m;
				}
				else {
					DAO_Responsable dao_r = new DAO_Responsable(DAO_Connection.getInstance());
					Responsable r = dao_r.find(p_ID);
					if(r!=null) {
						r.setNom(p_nom);
						r.setPrenom(p_prenom);
						r.setDate(p_date);
						r.setEmail(p_mail);
						r.setId(p_ID);
						r.setMdp(mdp);
						r.setCategorie(dao_r.find(p_ID).getCategorie());
						return r;
					}
					else {
						DAO_Tresorier dao_t = new DAO_Tresorier(DAO_Connection.getInstance());
						Tresorier t = dao_t.find(p_ID);
						if(t!=null) {
							t.setNom(p_nom);
							t.setPrenom(p_prenom);
							t.setDate(p_date);
							t.setEmail(p_mail);
							t.setId(p_ID);
							t.setMdp(mdp);
							return t;
						}
						else {
							p = new Personne(result.getString("nom"),result.getString("prenom"), result.getString("dateNaissance"),
									result.getString("telephone"), result.getString("mail"), mdp);
							p.setId(result.getInt("idPersonne"));
							return p;
						}
					}
				}
			}
			else {
				p = null;
				return p;
			}
		}
		catch(SQLException e){
			e.printStackTrace();
			return null;
		}
}
/*	public Personne connexion(String nom, String prenom, String mdp) {
		try {
			PreparedStatement stm = connect.prepareStatement("SELECT * FROM Personne "
					+ "WHERE Nom = ? AND Prenom = ? AND Mdp = ?", 
					ResultSet.TYPE_SCROLL_SENSITIVE, 
					ResultSet.CONCUR_READ_ONLY);
			stm.setString(1, nom);
			stm.setString(2, prenom);
			stm.setString(3, mdp);
			ResultSet result = stm.executeQuery();
			
			if(result.first()) {
				int iD = result.getInt("ID");
				DAO_Membre dao_membre = new DAO_Membre(DAO_Connection.getInstance());
				Membre membre = dao_membre.find(iD);
				if(membre != null) {
					return membre;
				}
				else {
					DAO_Responsable dao_responsable = new DAO_Responsable(DAO_Connection.getInstance());
					Responsable responsable = dao_responsable.find(iD);
					if(responsable != null) {
						return responsable;
					}
					else {
						DAO_Tresorier dao_tresorier = new DAO_Tresorier(DAO_Connection.getInstance());
						Tresorier tresorier = dao_tresorier.find(iD);
						if(tresorier != null) {
							return tresorier;
						}
						else {
							Personne personne = new Personne(
									result.getString("Nom"),
									result.getString("Prenom"),
									result.getString("Localite"),
									result.getString("DateNaissance"),
									result.getString("Email"),
									mdp);
							return personne;
						}
					}
				}
			}
			else
				return null;
		}
		catch(SQLException e) {
			e.printStackTrace();
			return null;
		}
	} */
}
