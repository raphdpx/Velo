package be.dis.pojo;

import java.util.List;

public class Membre extends Personne {
	
	//Attributs
	private static final long serialVersionUID = 652771868407863792L;
	private float dette;
	private List<Categorie> listeCat;
	
	//Constructeurs
	public Membre() { super(); }
	public Membre(
			String nom, 
			String prenom, 
			String localite,
			String date,
			String email,
			String mdp,
			float dette) {
		super(nom, 
				prenom, 
				localite,
				date,
				email,
				mdp);
		this.dette = dette;
	}
	
	
	//Getters
	public float getDette() { return dette; }
	public List<Categorie> getCat() { return listeCat; }
	
	//Setters
	public void setDette(float dette) { this.dette = dette; }
	public void setListCat(List<Categorie> listeCat) { this.listeCat = listeCat; }
	
	//Methode Ajout Categorie avec modification de la dette
	public void addCat(Categorie cat) {
		if(!listeCat.contains(cat)) { 
			if (listeCat.isEmpty())
				this.dette += 20;
			else
				this.dette += 5;
			listeCat.add(cat);
		}
	}
}
