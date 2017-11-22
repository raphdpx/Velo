package be.dis.pojo;

import java.io.Serializable;

public class Personne implements Serializable {

	//Attributs
	private static final long serialVersionUID = 1632211013962546592L;
	private int idPers;
	private String nom;
	private String prenom;
	private String localite;
	private String dateNaissance;
	private String email;
	private String mdp;
	
	//Constructeurs
	public Personne() {	}
	public Personne(
			String nom, 
			String prenom, 
			String localite,
			String date,
			String email,
			String mdp) {
		this.nom = nom;
		this.prenom = prenom;
		this.localite = localite;
		this.dateNaissance = date;
		this.email = email;
		this.mdp = mdp;
	}
	
	//Getters
	public int getId() { return idPers; }
	public String getNom() { return nom; }
	public String getPrenom() { return prenom; }
	public String getLocalite() { return localite; }
	public String getDate() { return dateNaissance; }
	public String getEmail() { return email; }
	public String getMdp() { return mdp; }
	
	//Setters
	public void setId(int id) { this.idPers = id; }
	public void setNom(String nom) { this.nom = nom; }
	public void setPrenom(String prenom) { this.prenom = prenom; }
	public void setLocalite(String localite) { this.localite = localite; }
	public void setDate(String date) { dateNaissance = date; }
	public void setEmail(String email) { this.email = email; }
	public void setMdp(String mdp) { this.mdp = mdp; }
	
}
