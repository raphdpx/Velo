package be.dis.pojo;

public class Trial extends Vtt {

	//Attributs
	private static final long serialVersionUID = 5892421171788851559L;
	private final String nom = "Vtt Trial";
	
	//Constructeurs
	public Trial() { }
	public Trial(int id) { super(id); }
	
	//Getters
	public String getNom() { return nom; }
}
