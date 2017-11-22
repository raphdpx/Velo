package be.dis.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import be.dis.pojo.Balade;
import be.dis.pojo.Categorie;
import be.dis.pojo.Membre;

public class DAO_Membre extends DAO<Membre> {
	public DAO_Membre(Connection conn) { super(conn); }

	@Override
	public boolean create(Membre obj) {
		boolean bool = false;
		PreparedStatement stm = null;
		ResultSet result = null;
		try {
			stm = connect.prepareStatement(
					  "SELECT Nom, Prenom "
					  + "FROM Personne "
					  + "WHERE Nom = ? AND Prenom = ?",
					  ResultSet.TYPE_SCROLL_SENSITIVE,
					  ResultSet.CONCUR_READ_ONLY);
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
					+ 			"Mdp "
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
	public boolean delete(Membre obj) {
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

	@Override
	public boolean update(Membre obj) {
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
			
			stm = connect.prepareStatement("UPDATE Membre "
					+ "SET Dette = ? "
					+ "WHERE ID = " + obj.getId());
			stm.setFloat(1, obj.getDette());
			stm.executeUpdate();
			return true;
		}
		catch(SQLException e) { 
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public Membre find(int id) {
		Membre membre = new Membre();
		ResultSet result = null;
		try {
			PreparedStatement stm = connect.prepareStatement("SELECT * FROM Personne P "
					+ "INNER JOIN Membre M on P.ID = M.ID "
					+ "WHERE ID = ?",
					ResultSet.TYPE_SCROLL_SENSITIVE,
					ResultSet.CONCUR_READ_ONLY);
			stm.setInt(1, id);
			result = stm.executeQuery();
			
			if(result.first()) {
				membre.setId(id);
				membre.setNom(result.getString("Nom"));
				membre.setPrenom(result.getString("Prenom"));
				membre.setLocalite(result.getString("localite"));
				membre.setDate(result.getString("DateNaissance"));
				membre.setEmail(result.getString("Email"));
				membre.setMdp(result.getString("Mdp"));
				membre.setDette(result.getFloat("Dette"));
			}
			else
				membre = null;
		}
		catch(SQLException e) { e.printStackTrace(); }
		return membre;
	}
	
	public List<Membre> findAll(){
		List<Membre> ListeMembre = new ArrayList<Membre>();
		ResultSet result = null;
		try {
			PreparedStatement stm = connect.prepareStatement("SELECT * FROM Personne P "
					+ "INNER JOIN Membre M on P.ID = M.ID",
					ResultSet.TYPE_SCROLL_SENSITIVE,
					ResultSet.CONCUR_READ_ONLY);
			result = stm.executeQuery();
			
			while(result.next()) {
				Membre membre = new Membre(
						result.getString("Nom"),
						result.getString("Prenom"),
						result.getString("localite"),
						result.getString("DateNaissance"),
						result.getString("Email"),
						result.getString("Mdp"),
						result.getFloat("Dette"));
				membre.setId(result.getInt("ID"));
				ListeMembre.add(membre);
			}
		}
		catch(SQLException e) { e.printStackTrace(); }
		return ListeMembre;
	}
	
	public List<Membre> listeMembreBalade(){
		List<Membre> listeMembre = new ArrayList<Membre>();
		ResultSet result = null;
		try {
			PreparedStatement stm = connect.prepareStatement("SELECT * FROM Personne P "
					+ "INNER JOIN Membre M on P.ID = M.ID "
					+ "INNER JOIN LD_Membre_Balade L on P.ID = L.IDMembre",
					ResultSet.TYPE_SCROLL_SENSITIVE,
					ResultSet.CONCUR_READ_ONLY);
			result = stm.executeQuery();
			
			while(result.next()) {
				Membre membre = new Membre(
						result.getString("Nom"),
						result.getString("Prenom"),
						result.getString("localite"),
						result.getString("DateNaissance"),
						result.getString("Email"),
						result.getString("Mdp"),
						result.getFloat("Dette"));
				membre.setId(result.getInt("ID"));
				listeMembre.add(membre);
			}
		}
		catch(SQLException e) { e.printStackTrace(); }
		return listeMembre;
	}
	
	public List<Membre> AJoutDetteBalade(Balade balade, float dette){
		List<Membre> listeMembre = new ArrayList<Membre>();
		List<Membre> listeConducteur = new ArrayList<Membre>();
		PreparedStatement stm = null;
		ResultSet result = null;
		try {
			stm = connect.prepareStatement("SELECT * FROM Personne P "
					+ "INNER JOIN Membre M on P.ID = M.ID "
					+ "INNER JOIN Voiture V on V.IDProprio = P.ID "
					+ "INNER JOIN LD_Voiture_Balade L on V.ID = L.IDVoiture "
					+ "WHERE IDBalade = ?",
					ResultSet.TYPE_SCROLL_SENSITIVE,
					ResultSet.CONCUR_READ_ONLY);
			stm.setInt(1, balade.getId());
			result = stm.executeQuery();
			while(result.next()) {
				Membre membre = new Membre(
						result.getString("Nom"),
						result.getString("Prenom"),
						result.getString("localite"),
						result.getString("DateNaissance"),
						result.getString("Email"),
						result.getString("Mdp"),
						result.getFloat("Dette"));
				membre.setId(result.getInt("ID"));
				listeConducteur.add(membre);
			}
			stm = connect.prepareStatement("SELECT * FROM Personne P "
					+ "INNER JOIN Membre M on P.ID = M.ID "
					+ "INNER JOIN LD_Membre_Balade L on P.ID = L.IDMembre "
					+ "WHERE IDBalade = ?",
					ResultSet.TYPE_SCROLL_SENSITIVE,
					ResultSet.CONCUR_READ_ONLY);
			stm.setInt(1, balade.getId());
			result = stm.executeQuery();
			while(result.next()) {
				Membre passager = new Membre(
						result.getString("Nom"),
						result.getString("Prenom"),
						result.getString("localite"),
						result.getString("DateNaissance"),
						result.getString("Email"),
						result.getString("Mdp"),
						result.getFloat("Dette"));
				passager.setId(result.getInt("ID"));
				
				if(!listeConducteur.contains(passager)){
					passager.setDette(passager.getDette() + dette);
					this.update(passager);
				}
			}
		}
		catch(SQLException e) { e.printStackTrace(); }
		return listeMembre;
	}
	
	public boolean inscriptionCategorie(Membre membre, Categorie cat) {
		boolean bool = false;
		PreparedStatement stm = null;
		ResultSet result = null;
		try {
			stm = connect.prepareStatement(
					"SELECT * FROM LD_Membre_Cat "
					+ "WHERE IDMembre = ? AND IDCat = ?",
					ResultSet.TYPE_SCROLL_SENSITIVE,
					ResultSet.CONCUR_READ_ONLY);
			stm.setInt(1, membre.getId());
			stm.setInt(2, cat.getId());
			result = stm.executeQuery();
			if(result.first())
				System.out.println("Vous êtes déjà inscrit dans cette catégorie.");
			else {
				stm = connect.prepareStatement(""
						+ "INSERT INTO LD_Membre_Cat(IDMembre, IDCat) "
						+ "VALUES(?, ?)");
				stm.setInt(1, membre.getId());
				stm.setInt(2, cat.getId());
				stm.executeUpdate();
				bool = true;
			}
		}
		catch(SQLException e) {
			e.printStackTrace();
			return false;
		}
		return bool;
	}
	
	public List<Balade> listeBalade(Membre membre){
		List<Balade> listBalade = new ArrayList<Balade>();
		ResultSet result = null;
		try {
			PreparedStatement stm = connect.prepareStatement(
					"SELECT * FROM LD_Membre_Balade L "
					+ "INNER JOIN Balade B on L.IDBalade = B.ID "
					+ "WHERE IDMembre = " + membre.getId(),
					ResultSet.TYPE_SCROLL_SENSITIVE,
					ResultSet.CONCUR_READ_ONLY);
			result = stm.executeQuery();
			while(result.next()) {
				DAO_Voiture dao_voiture = new DAO_Voiture(DAO_Connection.getInstance());
				DAO_Categorie dao_cat = new DAO_Categorie(DAO_Connection.getInstance());
				Balade balade = new Balade(
						result.getString("Depart"),
						result.getString("DateBalade"),
						dao_voiture.findVoitureBalade(result.getInt("ID")),
						dao_cat.find(result.getInt("IDCat"))
						);
				balade.setId(result.getInt("ID"));
				listBalade.add(balade);
			}
		}
		catch(SQLException e) {
			e.printStackTrace();
			return null;
		}
		return listBalade;
	}
	
	public boolean inscriptionVoitureBalade(Membre membre, Balade balade) {
		boolean bool = false;
		DAO_Voiture dao_voiture = new DAO_Voiture(DAO_Connection.getInstance());
		PreparedStatement stm = null;
		ResultSet result = null;
		try {	
			stm = connect.prepareStatement(
				  "SELECT * "
				  + "FROM LD_Voiture_Balade "
				  + "WHERE IDVoiture = ? AND IDBalade = ? ",
				  ResultSet.TYPE_SCROLL_SENSITIVE,
				  ResultSet.CONCUR_READ_ONLY);
		stm.setInt(1, dao_voiture.findFromProprio(membre).getId());
		stm.setInt(2, balade.getId());
		result = stm.executeQuery();
		if(result.first())
			System.out.println("Cette voiture est déjà inscrite.");
		else {
			stm = connect.prepareStatement(
				  "INSERT INTO "
				+ "		LD_Voiture_Balade(IDVoiture, IDBalade)"
				+ "VALUES(?, ?)");
			stm.setInt(1, dao_voiture.findFromProprio(membre).getId());
			stm.setInt(2, balade.getId());
			stm.executeUpdate();
			bool = true;
			}
		}
		catch(SQLException e){
		e.printStackTrace();
		}
		return bool;
	}
	
	public boolean inscriptionBalade(Membre membre, Balade balade) {
		boolean bool = false;
		PreparedStatement stm = null;
		ResultSet result = null;
		try {	
			stm = connect.prepareStatement(
				  "SELECT * "
				  + "FROM LD_Membre_Balade "
				  + "WHERE IDMembre = ? AND IDBalade = ? ",
				  ResultSet.TYPE_SCROLL_SENSITIVE,
				  ResultSet.CONCUR_READ_ONLY);
			stm.setInt(1, membre.getId());
			stm.setInt(2, balade.getId());
			result = stm.executeQuery();
			if(result.first())
				System.out.println("Vous êtes déjà inscrit.");
			else {
				stm = connect.prepareStatement(
						"INSERT INTO "
								+ "LD_Membre_Balade(IDBalade, IDMembre) "
								+ "VALUES(?, ?)");
				stm.setInt(1, balade.getId());
				stm.setInt(2, membre.getId());
				stm.executeUpdate();
				bool = true;
			}
		}
		catch(SQLException e){
		e.printStackTrace();
		}
		return bool;
	}
	
}

