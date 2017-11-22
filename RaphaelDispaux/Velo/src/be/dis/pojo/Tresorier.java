package be.dis.pojo;

public class Tresorier extends Personne {

	//Attributs
	private static final long serialVersionUID = 3534524875900032038L;
	
	//Constructeurs
	public Tresorier() { super(); }
	public Tresorier(
			String nom, 
			String prenom, 
			String localite,
			String date,
			String email,
			String mdp) {
		super(	nom, 
				prenom, 
				localite,
				date,
				email,
				mdp);
	}
}
