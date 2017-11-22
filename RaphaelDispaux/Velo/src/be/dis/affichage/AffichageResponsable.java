package be.dis.affichage;

import java.util.ArrayList;
import java.util.List;

import be.dis.clavier.Clavier;
import be.dis.dao.DAO_Balade;
import be.dis.dao.DAO_Connection;
import be.dis.dao.DAO_Membre;
import be.dis.main.Main;
import be.dis.pojo.Balade;
import be.dis.pojo.Calendrier;
import be.dis.pojo.Responsable;
import be.dis.pojo.Voiture;

public class AffichageResponsable {
	public static void affichageResponsable(Responsable user) {
		int choix;
		do {
			System.out.println("************************************************");
			System.out.println();
			System.out.println(		user.getCategorie().getId());
			System.out.println(" 1. Ajouter une balade");
			System.out.println(" 2. Ajouter dette de transport");
			System.out.println(" 3. Quitter");
			System.out.println();
			System.out.println("************************************************");
			System.out.print(" Que voulez-vous faire ? : ");
			choix = Clavier.lireInt();
			if(choix < 1 || choix > 3)
				System.out.println(" Veuillez faire un choix parmi les propositions.");
		}
		while(choix < 1 || choix > 3);
		switch(choix){
		case 1 :
			ajouterBalade(user);
			affichageResponsable(user);
			break;
		case 2 :
			ajouterDette(user);			
			affichageResponsable(user);
			break;
		case 3 :
			Main.quitter();
			break;
		}	
	}
	
	public static void ajouterBalade(Responsable user) {
		String depart, dateBalade;
		System.out.println("************************************************");
		System.out.println();
		System.out.println("	Ajout Balade");
		System.out.println();
		System.out.print("	Ville du départ : ");
		depart = Clavier.lireString();
		System.out.print("	Date de la balade : ");
		dateBalade = Clavier.lireString();
		
		List<Voiture> listeV = new ArrayList<Voiture>();
		
		Balade balade = new Balade(
				depart,
				dateBalade,
				listeV,
				user.getCategorie());
		
		DAO_Balade dao_balade = new DAO_Balade(DAO_Connection.getInstance());
		if(dao_balade.create(balade)) {
			System.out.println("La balade a bien été enregistrée.");
			affichageResponsable(user);
		}
	}
	
	public static void ajouterDette(Responsable user) {
		int choix;
		float dette;
		DAO_Balade dao_balade = new DAO_Balade(DAO_Connection.getInstance());
		DAO_Membre dao_membre = new DAO_Membre(DAO_Connection.getInstance());
		Calendrier calendar = new Calendrier(dao_balade.baladeFromCat(user.getCategorie()));
		List<Balade> listeBalade = calendar.getListeBalade();
		System.out.println("************************************************");
		System.out.println();
		System.out.println("	Ajout Dette Passager");
		for(int i = 0; i < listeBalade.size(); i++) {
			System.out.println(" " + (i+1) 
					+ ". Depart : " + listeBalade.get(i).getDepart() 
					+ "		Date : " + listeBalade.get(i).getDate());
		}
		System.out.println();
		System.out.println("************************************************");
		System.out.print("  De quelle balade ? : ");
		System.out.print(" Entrez 0 pour un retour au menu");
		do {
			choix = Clavier.lireInt();
			if(choix < 1 || choix > listeBalade.size())
				System.out.println(" Veuillez faire un choix parmi les propositions.");
		}
		while(choix < 0 || choix > listeBalade.size());
		if(choix != 0) {
			System.out.println("************************************************");
			System.out.print("  Entrez le montant de la dette ? : ");
			dette = Clavier.lireFloat();
			dao_membre.AJoutDetteBalade(listeBalade.get(choix-1), dette);
		}
		else
			affichageResponsable(user);
	}
}
