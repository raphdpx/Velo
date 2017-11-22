package be.dis.pojo;

import java.io.Serializable;
import java.util.List;

public class Calendrier implements Serializable {

	//Attributs
	private static final long serialVersionUID = -7876887003758773161L;
	private List<Balade> listeBalade;
	
	//Constructeurs
	public Calendrier() { }
	public Calendrier(List<Balade> listeBalade) {
		this.setListeBalade(listeBalade);
	}
	
	//Getters
	public List<Balade> getListeBalade() { return listeBalade; }
	
	//Setters
	public void setListeBalade(List<Balade> listeBalade) { this.listeBalade = listeBalade; }
	
}
