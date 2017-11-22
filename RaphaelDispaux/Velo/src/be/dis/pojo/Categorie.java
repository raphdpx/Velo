package be.dis.pojo;

import java.io.Serializable;

public class Categorie implements Serializable {

	//Attributs
	private static final long serialVersionUID = 8305766030591043413L;
	private int id;
	
	//Constructeurs
	public Categorie() { }
	public Categorie(int id) { this.id = id; }
	
	//Getters
	public int getId() { return id; }	
	//Setters
	public void setId(int id) { this.id = id; }

}
