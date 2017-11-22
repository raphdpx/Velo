package be.dis.pojo;

import java.io.Serializable;
import java.util.List;

public class Balade implements Serializable {

	//Attributs
	private static final long serialVersionUID = -734693453807054497L;
	private int id;
	private String depart;
	private String date;
	private List<Voiture> listeVoiture;
	private Categorie cat;
	
	//Constructeurs
	public Balade() { }
	public Balade(
			String depart,
			String date,
			List<Voiture> listeVoiture,
			Categorie cat) {
		this.setDepart(depart);
		this.setDate(date);
		this.setListeVoiture(listeVoiture);
		this.setCat(cat);
	}
	
	//Getters
	public int getId() { return id; }
	public String getDepart() { return depart; }
	public String getDate() { return date; }
	public List<Voiture> getListeVoiture() { return listeVoiture; }
	public Categorie getCat() { return cat; }
	
	//Setters
	public void setId(int id) { this.id = id; }
	public void setDepart(String depart) { this.depart = depart; }
	public void setDate(String date) { this.date = date; }
	public void setListeVoiture(List<Voiture> listeVoiture) { this.listeVoiture = listeVoiture; }
	public void setCat(Categorie cat) { this.cat = cat;	}
	
}
