package be.dis.pojo;

import java.io.Serializable;

public class Voiture implements Serializable {

	//Attributs
	private static final long serialVersionUID = -3383891340639658054L;
	private int id;
	private Membre proprio;
	private int placesP;
	private int placesV;
	
	//Construceurs
	public Voiture() { }
	public Voiture(
			Membre proprio,
			int placesP,
			int placesV) {
		this.proprio = proprio;
		this.placesP = placesP;
		this.placesV = placesV;
	}
	
	//Getters
	public int getId() { return id; }
	public Membre getProprio() { return proprio; }
	public int getPlacesP() { return placesP; }
	public int getPlacesV() { return placesV; }
	
	//Setters
	public void setId(int id) { this.id = id; }
	public void setProprio(Membre proprio) { this.proprio = proprio; }
	public void setPlacesP(int placesP) { this.placesP = placesP; }
	public void setPlacesV(int placesV) { this.placesV = placesV; }
	
}
