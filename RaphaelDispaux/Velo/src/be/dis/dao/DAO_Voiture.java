package be.dis.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import be.dis.pojo.Membre;
import be.dis.pojo.Voiture;

public class DAO_Voiture extends DAO<Voiture> {
	
	public DAO_Voiture(Connection conn){ super(conn); }
	
	@Override
	public boolean create(Voiture obj) {
		boolean bool = false;
		PreparedStatement stm = null;
		ResultSet result = null;
		try {
			stm = connect.prepareStatement(
					  "SELECT * "
					  + "FROM Voiture "
					  + "WHERE IDProprio = ?",
					  ResultSet.TYPE_SCROLL_SENSITIVE,
					  ResultSet.CONCUR_READ_ONLY);
			stm.setInt(1, obj.getProprio().getId());
			result = stm.executeQuery();
			if(result.first())
				System.out.println("Cette voiture est déjà inscrite.");
			else {
				stm = connect.prepareStatement(
					  "INSERT INTO "
					+ "		Voiture("
					+ "IDProprio, "
					+ "PlacesP, "
					+ "PlacesV) "
					+ "VALUES(?, ?, ?)");
				stm.setInt(1, obj.getProprio().getId());
				stm.setInt(2, obj.getPlacesP());
				stm.setInt(3, obj.getPlacesV());
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
	public boolean delete(Voiture obj) { return false; }
	
	@Override
	public boolean update(Voiture obj) { return false; }
	
	@Override
	public Voiture find(int id) { return null; }
	
	public Voiture findFromProprio(Membre membre) {
		Voiture voiture = new Voiture();
		ResultSet result = null;
		try {
			PreparedStatement stm = connect.prepareStatement(
					"SELECT * FROM Voiture V "
					+ "WHERE IDProprio = " + membre.getId(),
					ResultSet.TYPE_SCROLL_SENSITIVE,
					ResultSet.CONCUR_READ_ONLY);
			result = stm.executeQuery();
			if(result.first()) {
				voiture.setId(result.getInt("ID"));
				voiture.setPlacesP(result.getInt("PlacesP"));
				voiture.setPlacesV(result.getInt("PlacesV"));
				voiture.setProprio(membre);
			}
		}
		catch(SQLException e) {
			e.printStackTrace();
			return null;
		}
		return voiture;
	}
	public List<Voiture> findVoitureBalade(int id) { 
		List<Voiture> listeVoiture = new ArrayList<Voiture>();
		ResultSet result = null;
		DAO_Membre membre = new DAO_Membre(DAO_Connection.getInstance());
		try {
			PreparedStatement stm = connect.prepareStatement("SELECT * FROM Voiture V "
					+ "INNER JOIN LD_Voiture_Balade L ON V.ID = L.IDVoiture "
					+ "WHERE IDBalade = " + id,
					ResultSet.TYPE_SCROLL_SENSITIVE,
					ResultSet.CONCUR_READ_ONLY);
			result = stm.executeQuery();
			while(result.next()) {
				Voiture voiture = new Voiture(
						membre.find(result.getInt("IDProprio")),
						result.getInt("PlacesP"),
						result.getInt("PlacesV"));
				voiture.setId(result.getInt("ID"));
				listeVoiture.add(voiture);
			}
			return listeVoiture;
		}
		catch(SQLException e) {
			e.printStackTrace();
			return null;} 
	}
}

