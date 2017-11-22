package be.dis.affichage;

import java.util.List;

import be.dis.clavier.Clavier;
import be.dis.dao.DAO_Connection;
import be.dis.dao.DAO_Membre;
import be.dis.main.Main;
import be.dis.pojo.Membre;
import be.dis.pojo.Tresorier;

public class AffichageTresorier {
	public static void affichageTresorier(Tresorier user) {
		int choix;
		do {
			System.out.println("************************************************");
			System.out.println();
			System.out.println(" 1. Gérer la dette d'un membre");
			System.out.println(" 2. Quitter");
			System.out.println();
			System.out.println("************************************************");
			System.out.print(" Que voulez-vous faire ? : ");
			choix = Clavier.lireInt();
			if(choix < 1 || choix > 2)
				System.out.println(" Veuillez faire un choix parmi les propositions.");
		}
		while(choix < 1 || choix > 2);
		switch(choix){
		case 1 :
			gererDette(user);
			affichageTresorier(user);
			break;
		case 2 :
			Main.quitter();
			break;
		}	
	}
	
	public static void gererDette(Tresorier user) {
		int choix;
		float dette;
		DAO_Membre dao_membre = new DAO_Membre(DAO_Connection.getInstance());
		List<Membre> listeMembre = dao_membre.findAll();
		System.out.println("************************************************");
		System.out.println();
		System.out.println("	Gérer Dette Membre");
		for(int i = 0; i < listeMembre.size(); i++) {
			System.out.println(" " + (i+1) 
					+ ". Nom : " + listeMembre.get(i).getNom() 
					+ "		Prenom : " + listeMembre.get(i).getPrenom()
					+ "		Dette : " + listeMembre.get(i).getDette());
		}
		System.out.println();
		System.out.println("************************************************");
		System.out.print("  De quel membre ? : ");
		System.out.print(" Entrez 0 pour un retour au menu");
		do {
			choix = Clavier.lireInt();
			System.out.print(" Entrez 0 pour un retour au menu");
			if(choix < 1 || choix > listeMembre.size())
				System.out.println(" Veuillez faire un choix parmi les propositions.");
		}
		while(choix < 0 || choix > listeMembre.size());
		if(choix != 0) {
			System.out.println("************************************************");
			System.out.print("  Entrez le montant à retirer de la dette ? : ");
			dette = Clavier.lireFloat();
			listeMembre.get(choix - 1).setDette((listeMembre.get(choix - 1).getDette() - dette));
			dao_membre.update(listeMembre.get(choix - 1));
		}
		else
			affichageTresorier(user);
	}
}
