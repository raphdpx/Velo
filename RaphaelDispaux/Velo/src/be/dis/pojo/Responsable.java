package be.dis.pojo;

public class Responsable extends Personne {

	//Attributs
	private static final long serialVersionUID = 4311652323232790454L;
	private Categorie categorie = new Categorie(1);
	
	//Constructeurs
	public Responsable() { super(); }
	public Responsable(
			String nom, 
			String prenom, 
			String localite,
			String date,
			String email,
			String mdp,
			Categorie categorie) {
		super(	nom, 
				prenom, 
				localite,
				date,
				email,
				mdp);
		this.setCategorie(categorie);
	}
	
	//Getters
	public Categorie getCategorie() { return categorie; }
	
	//Setters
	public void setCategorie(Categorie categorie) { this.categorie = categorie; }
	
}
