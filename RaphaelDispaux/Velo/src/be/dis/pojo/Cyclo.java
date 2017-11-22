package be.dis.pojo;

public class Cyclo extends Categorie{

	//Attributs
	private static final long serialVersionUID = -4464733388617356323L;
	private final String nom = "Cyclo";
	
	//Constructeurs
	public Cyclo() {  }
	public Cyclo(int id) { super(id); }

	//Getters
	public String getNom() { return nom; }
}
