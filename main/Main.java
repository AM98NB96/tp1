package main;
import java.io.*;
import java.sql.Array;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.LinkedList;
import java.util.Scanner;

/**
 * Classe principale permettant l'execution du programme.
 * 
 * @param client
 * 			Liste pour contenir les clients
 * @param plats
 * 			Liste pour contenir les plats
 * @param commandes
 * 			Liste pour contenir les commandes
 * 
 */
public class Main {
	
	public static LinkedList<Client> clients = new LinkedList<Client>();
	public static LinkedList<Plat> plats = new LinkedList<Plat>();
	public static LinkedList<Commande> commandes = new LinkedList<Commande>();
	public static LinkedList<Erreur> erreurs = new LinkedList<Erreur>();
	
	public static void main(String[] args) {
		lireFichierObjet("test.txt");
		
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd-HH");
		Date date = new Date();
		
		if (commandes.size() > 0) {
			creerFichierFacture("Facture-du-" + dateFormat.format(date) + ".txt");
		}
	}
	/**
	 * Lecture du fichier objet pour cr�er les objets qui y sont sauvegard�s
	 * @param fichier
	 * 			Contient le nom du fichier
	 */
	public static void lireFichierObjet(String fichier) {
		try {
			Scanner input = new Scanner(new FileReader(fichier));
		
			String typeObj = "";

			while (input.hasNextLine()) {
				
				String tmpString = input.nextLine();
				
				if (tmpString.length() > 0) {
					//Type d'objet courrant
					if (tmpString.equals("Clients :") || tmpString.equals("Plats :") || tmpString.equals("Commandes :") || tmpString.equals("Fin")) {
						
						typeObj = tmpString;
						
					}else{
						
						//S�paration des lignes du fichier d'objets
						String[] tmp = tmpString.split(" ");
						boolean erreur = false;
						
						switch (typeObj) {
						
							//Cr�ation des clients
							case "Clients :":
								for (char car : tmpString.toCharArray()) {
									if (!Character.isLetter(car) && car != '-') {
										if (Character.isWhitespace(car)) {
											erreurs.add(new Erreur(tmpString , "Le format du client est invalide, car il ne doit pas contenir d'espaces."));
										} else if (Character.isDigit(car)) {
											erreurs.add(new Erreur(tmpString , "Le format du client est invalide, car il ne doit pas contenir de caracteres num�riques."));
										} else  {
											erreurs.add(new Erreur(tmpString , "Le format du client est invalide, car il ne doit pas contenir de caracteres sp�ciaux autre que '-'."));
										}
									}
								}

								clients.add(new Client(tmpString));					
								break;
								
							//Cr�ation des plats
							case "Plats :":
								if (tmp.length == 1) {
									erreurs.add(new Erreur(tmpString , "Le format du plat est invalide, car il manque un param�tre."));
									erreur = true;
								} else {
									boolean unPoint = false;
									
									for (char car : tmp[1].toCharArray()) {
										if (!Character.isDigit(car) && (car != '.' || unPoint)) {
											erreurs.add(new Erreur(tmpString , "Le format du plat est invalide, car " + tmp[1] + " n'est pas un prix valide."));
											erreur = true;
										}
										
										unPoint = (car == '.') || unPoint;
									}
								} 

								if (!erreur) {
									plats.add(new Plat(tmp[0], Double.parseDouble(tmp[1])));
								}
								break;
			
							//Cr�ation des commandes
							case "Commandes :":
								
								Client client = new Client("");
								Plat plat = new Plat("");
								int qte = 0;
								
								// Test Client
								for (char car : tmp[0].toCharArray()) {
									if (!Character.isLetter(car) && car != '-') {
										if (Character.isDigit(car)) {
											erreurs.add(new Erreur(tmpString , "Le format du client est invalide, car il ne doit pas contenir de caracteres num�riques."));
										} else {
											erreurs.add(new Erreur(tmpString , "Le format du client est invalide, car il ne doit pas contenir de caracteres sp�ciaux autre que '-'."));
										}
										erreur = true;
									}
								}
								
								for (Client c : clients) {
									if (c.nom.equals(tmp[0])) client = c; 
								}
								
								if (client.nom.equals("")) {
									erreurs.add(new Erreur(tmpString , "Le client n'existe pas."));
									erreur = true;
								}
								
								// Test Plat
								for (Plat p : plats) {
									if (p.nom.equals(tmp[1])) plat = p;
								}
								
								if (plat.nom.equals("")) {
									erreurs.add(new Erreur(tmpString , "Le plat n'existe pas."));
									erreur = true;
								}
								
								//Test Quantit�
								if (tmp.length < 3) {
									erreurs.add(new Erreur(tmpString, "Le format de la commande est invalide, car il manque un param�tre."));
									erreur = true;
								} else {
									try {
										qte = Integer.parseInt(tmp[2]);
									} catch (NumberFormatException e) {
										erreurs.add(new Erreur(tmpString, "Le format de la commande est invalide, car " + tmp[2] + " n'est pas une quantit�."));
										erreur = true;
									}
									
									if (!erreur && qte < 0) {
										erreurs.add(new Erreur(tmpString, "La quantit� ne peut pas �tre inf�rieure � 0."));
										erreur = true;
									} else if (!erreur && qte == 0) {
										erreurs.add(new Erreur(tmpString, "La quantit� ne peut pas �tre 0."));
										erreur = true;
									}
								}
								
								if (!erreur) {
									commandes.add(new Commande(client, plat, qte));
								}
								
								break;
							case "Fin":
								// Fin de la lecture
								break;
						}
					}
				}
			}
			input.close();
			
		} catch (FileNotFoundException e) {
			System.out.println("Le fichier " + fichier + " n'a pas �t� trouv�.");
		}
	}
	
	
	
	/*
	 * Sauvegarde et affichage des factures
	*/
	
	/**
	 * Permet de cr�er la facture du total des commandes d'un client
	 * @param nomClient
	 * 			Contient le nom du client courrant
	 * @param cout
	 * 			Contient la somme avant taxe des commandes du client courrant
	 * @return
	 * 		Une facture
	 */
	public static String creerFacture(String nomClient, double cout) {
		double coutTPS = cout * 0.05;
		double coutTVQ = cout * 0.10;
		double total = cout + coutTPS + coutTVQ;
		int nbE = 21 + String.format("%.2f",
				Collections.max(Arrays.asList(cout, coutTPS, coutTVQ, total))).length();
		String facture = ("Facture de " + nomClient + ":\n" + 
				assemblerFacture(nbE, cout, "Av taxe:") + "\n" +
				assemblerFacture(nbE, coutTPS, "TPS(5%):") + "\n" +
				assemblerFacture(nbE, coutTVQ, "TVQ(10%):") + "\n" +
				assemblerFacture(nbE, total, "Total:"));
		return facture;
	}
	/**
	 * Defini le nombre d'espaces n�cessaires pour ajuster l'affichage puis assemble la chaine
	 * 
	 * @param nbEspaces
	 * 			Contient un nombre d'espaces calcul� pour emp�cher les d�bordements
	 * @param cout
	 * 			Contient le cout courrant
	 * @param titre
	 * 			Contient le titre qui d�fini le cout courrant
	 * @return
	 * 			Une ligne de la facture
	 */
	static String assemblerFacture(int nbEspaces, double cout, String titre) {
		String sCout = String.format("%.2f", cout);
		char[] espaces = new char[nbEspaces-(titre+sCout).length()];
		Arrays.fill(espaces, ' ');
		return (titre + new String(espaces) + sCout + "$");
	}
	/**
	 * Cr�ation du fichier d'un fichier o� les factures sont sauvegard�es
	 * @param nomFichier
	 * 			Contient le nom du fichier
	 */
	public static void creerFichierFacture(String nomFichier){
		PrintWriter sortie;
		
		try {
			sortie = new PrintWriter(nomFichier);
			
			//Affichage des erreurs
			for (Erreur erreur : erreurs) {
				System.out.println("Erreur de la commande: " + erreur.commande + "\n" +
								   "Code d'erreur: " + erreur.codeErreur);
				sortie.println("Erreur de la commande: " + erreur.commande + "\n" +
						   	   "Code d'erreur: " + erreur.codeErreur);
			}
			
			//Titre
			if (commandes.size() > 0) {
				System.out.println("Factures:");
				sortie.println("Factures:");
			}
			
			
			// Affichage des commandes
			for (Client client : clients) {
				double prix = 0;
				
				for (Commande com : commandes) {
					if (com.client.nom.equals(client.nom)) {
						prix += com.qte * com.plat.cout;
					}
				}
				
				if (prix > 0) {
					System.out.println(creerFacture(client.nom, prix));
					sortie.println(creerFacture(client.nom, prix));
				}
			}
			
			sortie.close();
		} catch (FileNotFoundException e) {
			System.out.println("Le fichier " + nomFichier + "n'a pas �t� trouv�.");
		}
	}

}