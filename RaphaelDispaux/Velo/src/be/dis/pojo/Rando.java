package be.dis.pojo;

public class Rando extends Vtt {

	//Attributs
	private static final long serialVersionUID = 2906894524799223136L;
	private final String nom = "Vtt Randonnee";
	
	//Constructeurs
	public Rando() { }
	public Rando(int id) { super(id); }
	
	//Getters
	public String getNom() { return nom; }
}
