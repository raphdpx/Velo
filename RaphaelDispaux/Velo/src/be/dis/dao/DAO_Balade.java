package be.dis.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import be.dis.pojo.Balade;
import be.dis.pojo.Categorie;
import be.dis.pojo.Responsable;

public class DAO_Balade extends DAO<Balade> {

	public DAO_Balade(Connection conn) {
		super(conn);
	}
	
	@Override
	public boolean create(Balade obj) { return false; }
	
	public boolean create(Balade obj, Responsable user) { 
		boolean bool = false;
		PreparedStatement stm = null;
		ResultSet result = null;
		try {
			stm = connect.prepareStatement("SELECT * FROM Balade "
					+ "WHERE Depart = ? AND DateBalade = ? AND IDCat = ?",
					ResultSet.TYPE_SCROLL_SENSITIVE,
					ResultSet.CONCUR_READ_ONLY);
			stm.setString(1, obj.getDepart());
			stm.setString(2, obj.getDate());
			stm.setInt(1, user.getCategorie().getId());
			result = stm.executeQuery();
			
			if(result.first())
				System.out.println("Cette balade existe déjà.");
			else {
				stm = connect.prepareStatement("INSERT INTO Balade("
						+ "Depart,"
						+ "DateBalade,"
						+ "IDCat)"
						+ "VALUES (?, ?, ?)");
				stm.setString(1, obj.getDepart());
				stm.setString(2, obj.getDate());
				stm.setInt(3, user.getCategorie().getId());
				stm.executeUpdate();
				
				stm = connect.prepareStatement("SELECT ID FROM Balade WHERE Depart = ? AND DateBalade = ?",
						ResultSet.TYPE_SCROLL_SENSITIVE,
						ResultSet.CONCUR_READ_ONLY);
				stm.setString(1, obj.getDepart());
				stm.setString(2, obj.getDate());
				result = stm.executeQuery();
				if(result.first()) {
						obj.setId(result.getInt("ID"));
						bool = true;
				}
			}
		}
		catch(SQLException e) { e.printStackTrace(); }
		return bool;
	}

	@Override
	public boolean delete(Balade obj) { return false; }

	@Override
	public boolean update(Balade obj) {	return false; }

	@Override
	public Balade find(int id) { return null; }
	
	public List<Balade> baladeFromCat(Categorie cat){
		List<Balade> listBalade = new ArrayList<Balade>();
		ResultSet result = null;
		try {
			PreparedStatement stm = connect.prepareStatement(
					"SELECT * FROM Balade "
					+ "WHERE IDCat = ?",
					ResultSet.TYPE_SCROLL_SENSITIVE,
					ResultSet.CONCUR_READ_ONLY);
			stm.setInt(1, cat.getId());
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
	
	public int placesVelo(Balade balade) {
		int placesV = 0;
		ResultSet result = null;
		try {
			PreparedStatement stm = connect.prepareStatement("SELECT * FROM Voiture V "
					+ "INNER JOIN LD_Voiture_Balade L ON V.ID = L.IDVoiture "
					+ "WHERE IDBalade = " + balade.getId(),
					ResultSet.TYPE_SCROLL_SENSITIVE,
					ResultSet.CONCUR_READ_ONLY);
			result = stm.executeQuery();
			while(result.next()) {
				placesV += result.getInt("PlacesV");
			}
			PreparedStatement stm2 = connect.prepareStatement("SELECT Count(*) FROM LD_Voiture_Balade "
					+ "WHERE IDBalade = " + balade.getId() + " GROUP BY IDBalade",
					ResultSet.TYPE_SCROLL_SENSITIVE,
					ResultSet.CONCUR_READ_ONLY);
			result = stm2.executeQuery();
			while(result.next()) {
				placesV -= result.getInt(1);
			}
			return placesV;
		}
		catch(SQLException e) {
			e.printStackTrace();
			return placesV;} 
	}
	
	public int placesPassager(Balade balade) {
		int placesP = 0;
		ResultSet result = null;
		try {
			PreparedStatement stm = connect.prepareStatement("SELECT * FROM Voiture V "
					+ "INNER JOIN LD_Voiture_Balade L ON V.ID = L.IDVoiture "
					+ "WHERE IDBalade = " + balade.getId(),
					ResultSet.TYPE_SCROLL_SENSITIVE,
					ResultSet.CONCUR_READ_ONLY);
			result = stm.executeQuery();
			while(result.next()) {
				placesP += result.getInt("PlacesP");
			}
			PreparedStatement stm2 = connect.prepareStatement("SELECT Count(*) FROM LD_Voiture_Balade "
					+ "WHERE IDBalade = " + balade.getId() + " GROUP BY IDBalade",
					ResultSet.TYPE_SCROLL_SENSITIVE,
					ResultSet.CONCUR_READ_ONLY);
			result = stm2.executeQuery();
			while(result.next()) {
				placesP -= result.getInt(1);
			}
			return placesP;
		}
		catch(SQLException e) {
			e.printStackTrace();
			return placesP;} 
	}


}