package be.dis.main;

import be.dis.affichage.AffichagePersonne;
import be.dis.clavier.Clavier;

public class Main {
	public static void main(String[] args) {
		demarrage();
	}
	public static void demarrage() {
		int choix;
		System.out.println("************************************************");
		System.out.println("	Programme de gestion d'un club cycliste		");
		System.out.println();
		System.out.println("	1. Connexion");
		System.out.println("	2. Inscription");
		System.out.println("	3. Quitter");
		System.out.println();
		System.out.println("************************************************");
		do {
			System.out.print("	Que voulez-vous faire ? : ");
			choix = Clavier.lireInt();
			System.out.println();
			if(choix < 1 || choix > 3)
				System.out.println("	Veuillez faire un choix parmi les propositions.");
		}
		while(choix < 1 || choix > 3);
		
		switch(choix) {
		case 1 :
			AffichagePersonne.connexion();
			demarrage();
			break;
		case 2 :
			AffichagePersonne.inscription();
			demarrage();
			break;
		case 3 :
			quitter();
			break;
		}
	}
	
	public static void quitter() {
		System.out.println("Merci d'avoir utiliser notre programme.");
		System.exit(1000);
	}
}
