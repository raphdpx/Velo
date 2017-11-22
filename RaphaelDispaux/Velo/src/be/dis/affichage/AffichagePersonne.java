package be.dis.affichage;

import be.dis.clavier.Clavier;
import be.dis.dao.DAO_Connection;
import be.dis.dao.DAO_Membre;
import be.dis.dao.DAO_Personne;
import be.dis.main.Main;
import be.dis.pojo.*;

public class AffichagePersonne {

	private static Personne personne;
	
	public static void connexion() {
		Object user;
		String nom, prenom, mdp;
		
		System.out.println("************************************************");
		System.out.println();
		System.out.println("	Connexion");
		System.out.println();
		System.out.print("	Nom 	: ");
		nom = Clavier.lireString();
		System.out.print("	Prenom 	: ");
		prenom = Clavier.lireString();
		System.out.print("	Mdp 	: ");
		mdp = Clavier.lireString();
		System.out.println();
		System.out.println("************************************************");
		
		DAO_Personne dao_personne = new DAO_Personne(DAO_Connection.getInstance());
		user = dao_personne.connexion(nom, prenom, mdp);
		
		if(user != null) {
			if(user.getClass().equals(Membre.class)) {
				AffichageMembre.affichageMembre((Membre)user);
			}
			else if (user.getClass().equals(Responsable.class)) {
				AffichageResponsable.affichageResponsable((Responsable)user);
			}
			else if (user.getClass().equals(Tresorier.class)) {
				AffichageTresorier.affichageTresorier((Tresorier)user);
			}
			else {
				personne = (Personne)user;
				affichagePersonne(personne);
			}	
		}
		else {
			System.out.println("Connexion échouée.");
		}
	}
	
	public static void affichagePersonne(Personne user) {
		int choix;
		System.out.println("************************************************");
		System.out.println();
		System.out.println("	Bonjour " + user.getPrenom() + " " + user.getNom());
		System.out.println();
		System.out.println("	1. Devenir Membre");
		System.out.println("	2. Quitter");
		System.out.println();
		System.out.println("************************************************");
		do {
			System.out.println();
			System.out.print("	Que voulez-vous faire ? : ");
			choix = Clavier.lireInt();
			if(choix < 1 || choix > 2)
				System.out.println("	Veuillez faire un choix parmi les propositions.");
		}
		while(choix < 1 || choix > 2);
		switch(choix) {
		case 1 :
			personneToMembre(user);
			break;
		case 2 :
			Main.quitter();
		}
	}
	
	public static void personneToMembre(Personne user) {
		Membre membre = new Membre(
				user.getNom(), 
				user.getPrenom(), 
				user.getLocalite(),
				user.getDate(),
				user.getEmail(),
				user.getMdp(),
				0);
		DAO_Membre dao_membre = new DAO_Membre(DAO_Connection.getInstance());
		dao_membre.update(membre);
		AffichageMembre.affichageMembre(membre);		
	}
	
	public static void inscription() {
		String nom, prenom, localite, dateN, mail, mdp;
		System.out.println("************************************************");
		System.out.println();
		System.out.println("	Inscription");
		System.out.println();
		System.out.print("Votre nom : ");
		nom = Clavier.lireString();
		System.out.print("Votre prénom : ");
		prenom = Clavier.lireString();
		System.out.print("Votre localité : ");
		localite = Clavier.lireString();
		System.out.print("Votre date de naissance (dd-mm-yy) : ");
		dateN = Clavier.lireString();
		System.out.print("Votre e-mail : ");
		mail = Clavier.lireString();
		System.out.print("Votre mot de passe : ");
		mdp = Clavier.lireString();
		System.out.println();
		System.out.println("************************************************");
		
		Personne user = new Personne(
				nom,
				prenom,
				localite,
				dateN,
				mail,
				mdp);
		
		DAO_Personne dao_personne = new DAO_Personne(DAO_Connection.getInstance());
		if(dao_personne.create(user)) {
			affichagePersonne(user);
		}
		
	}
}
