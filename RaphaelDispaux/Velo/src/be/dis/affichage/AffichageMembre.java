package be.dis.affichage;

import java.util.List;

import be.dis.clavier.Clavier;
import be.dis.dao.DAO_Categorie;
import be.dis.dao.DAO_Connection;
import be.dis.dao.DAO_Membre;
import be.dis.dao.DAO_Voiture;
import be.dis.main.Main;
import be.dis.pojo.*;

public class AffichageMembre {
	public static void affichageMembre(Membre membre) {
		int choix;
		do {
			System.out.println("************************************************");
			System.out.println();
			System.out.println(" " + membre.getNom() + " " + membre.getPrenom() + " Dette : " + membre.getDette());
			System.out.println(" 1. Inscription dans une catégorie");
			System.out.println(" 2. Inscrire sa voiture dans une balade");
			System.out.println(" 3. S'incrire sans voiture pour une balade");
			System.out.println(" 4. Créer sa voiture dans la bdd");
			System.out.println(" 5. Quitter");
			System.out.println();
			System.out.println("************************************************");
			System.out.print(" Que voulez-vous faire ? : ");
			choix = Clavier.lireInt();
			if(choix < 1 || choix > 5)
				System.out.println(" Veuillez faire un choix parmi les propositions.");
		}
		while(choix < 1 || choix > 5);
		switch(choix){
		case 1 :
			inscriptionCategorie(membre);
			affichageMembre(membre);
			break;
		case 2 :
			baladeConducteur(membre);
			affichageMembre(membre);
			break;
		case 3 :
			baladePassager(membre);
			affichageMembre(membre);
			break;
		case 4 :
			creationVoiture(membre);
			affichageMembre(membre);
			break;
		case 5 :
			Main.quitter();
			break;
		}
		
	}
	
	public static void inscriptionCategorie(Membre membre) {
		int choix;
		DAO_Membre dao_membre = new DAO_Membre(DAO_Connection.getInstance());
		DAO_Categorie dao_cat = new DAO_Categorie(DAO_Connection.getInstance());
		do {
			System.out.println("************************************************");
			System.out.println();
			System.out.println("	Inscription dans une catégorie");
			System.out.println(" 1. Vtt Trial");
			System.out.println(" 2. Vtt Descente");
			System.out.println(" 3. Vtt Randonnée");
			System.out.println(" 4. Cyclo");
			System.out.println(" 5. Retour");
			System.out.println();
			System.out.println("************************************************");
			System.out.print("  Numéro de la catégorie ? : ");
			choix = Clavier.lireInt();
			if(choix < 1 || choix > 5)
				System.out.println(" Veuillez faire un choix parmi les propositions.");
		}
		while(choix < 1 || choix > 5);
		switch(choix){
		case 1 :
			dao_membre.inscriptionCategorie(membre, dao_cat.find(1));
			affichageMembre(membre);
			break;
		case 2 :
			dao_membre.inscriptionCategorie(membre, dao_cat.find(2));
			affichageMembre(membre);
			break;
		case 3 :
			dao_membre.inscriptionCategorie(membre, dao_cat.find(3));
			affichageMembre(membre);
			break;
		case 4 :
			dao_membre.inscriptionCategorie(membre, dao_cat.find(4));
			affichageMembre(membre);
			break;
		case 5 :
			affichageMembre(membre);
			break;
		}
	}
	
	public static void baladeConducteur(Membre membre) {
		int choix;
		DAO_Membre dao_membre = new DAO_Membre(DAO_Connection.getInstance());
		Calendrier calendar = new Calendrier(dao_membre.listeBalade(membre));
		List<Balade> listeBalade = calendar.getListeBalade();
		System.out.println("************************************************");
		System.out.println();
		System.out.println("	Inscription dans une balade Conducteur");
		for(int i = 0; i < listeBalade.size(); i++) {
			System.out.println(" " + (i+1) 
					+ ". Depart : " + listeBalade.get(i).getDepart() 
					+ "		Date : " + listeBalade.get(i).getDate());
		}
		System.out.println();
		System.out.println("************************************************");
		System.out.print("  A quelle balade voulez-vous aller ? : ");
		System.out.print(" Entrez 0 pour un retour au menu");
		do {
			choix = Clavier.lireInt();
			if(choix < 1 || choix > listeBalade.size())
				System.out.println(" Veuillez faire un choix parmi les propositions.");
		}
		while(choix < 0 || choix > listeBalade.size());
		if(choix != 0) {
			dao_membre.inscriptionVoitureBalade(membre, listeBalade.get(choix-1));
			
		}
		else
			affichageMembre(membre);
	}
	
	public static void baladePassager(Membre membre) {
		int choix;
		DAO_Membre dao_membre = new DAO_Membre(DAO_Connection.getInstance());
		Calendrier calendar = new Calendrier(dao_membre.listeBalade(membre));
		List<Balade> listeBalade = calendar.getListeBalade();
		System.out.println("************************************************");
		System.out.println();
		System.out.println("	Inscription dans une balade Passager");
		for(int i = 0; i < listeBalade.size(); i++) {
			System.out.println(" " + (i+1) 
					+ ". Depart : " + listeBalade.get(i).getDepart() 
					+ "		Date : " + listeBalade.get(i).getDate());
		}
		System.out.println();
		System.out.println("************************************************");
		System.out.print("  A quelle balade voulez-vous aller ? : ");
		System.out.print(" Entrez 0 pour un retour au menu");
		do {
			choix = Clavier.lireInt();
			if(choix < 1 || choix > listeBalade.size())
				System.out.println(" Veuillez faire un choix parmi les propositions.");
		}
		while(choix < 0 || choix > listeBalade.size());
		if(choix != 0) {
			dao_membre.inscriptionBalade(membre, listeBalade.get(choix-1));
			
		}
		else
			affichageMembre(membre);
	}
	
	public static void creationVoiture(Membre membre) {
		int placesP, placesV;
		System.out.println("************************************************");
		System.out.println();
		System.out.println("	Inscription Voiture");
		System.out.println();
		System.out.print("	Nombres de places passagers : ");
		placesP = Clavier.lireInt();
		System.out.print("	Nombres de places vélos : ");
		placesV = Clavier.lireInt();
		
		Voiture voiture = new Voiture(
				membre,
				placesP,
				placesV);
		
		DAO_Voiture dao_voiture = new DAO_Voiture(DAO_Connection.getInstance());
		if(dao_voiture.create(voiture)) {
			System.out.println("Votre voiture a bien été enregistrée.");
			affichageMembre(membre);
		}
	}
}
