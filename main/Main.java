package main;
import java.io.*;
import java.util.Arrays;
import java.util.Collections;
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
		try {
			lireFichierObjet("test.txt");
		}catch (Exception e) {
			System.out.println("Le fichier ne respecte pas le format demandé !");
		}
		
		try {
			creerFichierFacture("facture.txt");
		} catch (Exception e) {
			System.out.println("erreur");
		}
		
	}
	/**
	 * Lecture du fichier objet pour créer les objets qui y sont sauvegardés
	 * @param fichier
	 * 			Contient le nom du fichier
	 * @throws FileNotFoundException
	 */
	public static void lireFichierObjet(String fichier) {
		try {
			Scanner input = new Scanner(new FileReader(fichier));
			String typeObj = "";
			while (input.hasNextLine()) {
				String tmpString = input.nextLine();
					//Type d'objet courrant
				if (tmpString.equals("Clients :") || tmpString.equals("Plats :") || tmpString.equals("Commandes :") || tmpString.equals("Fin")) {
					typeObj = tmpString;
				}else{
					
					//Séparation des lignes du fichier d'objets
					String[] tmp = tmpString.split(" ");
					
					switch (typeObj) {
					
						//Création des clients
						case "Clients :":
							clients.add(new Client(tmpString));					
							break;
							
						//Création des plats
						case "Plats :":
							plats.add(new Plat(tmp[0], Double.parseDouble(tmp[1])));
							break;
		
						//Création des commandes
						case "Commandes :":
							
							Client client = new Client();
							Plat plat = new Plat();
							int qte;
							
							for (Client c : clients) {
								client = (c.nom.equals(tmp[0]) ? c : client);
							}
							for (Plat p : plats) {
								plat = (p.nom.equals(tmp[1]) ? p : plat);
							}
							qte = Integer.parseInt(tmp[2]);
							
							commandes.add(new Commande(client, plat, qte));
							
							break;
						case "Fin":
							// Fin de la lecture
							break;
					}
				}
			}
			input.close();
		}catch (FileNotFoundException e) {
					e.printStackTrace();
		}
	}
	
	
	
	/*
	 * Sauvegarde et affichage des factures
	*/
	
	/**
	 * Permet de créer la facture du total des commandes d'un client
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
		String facture = ("Facture de " + nomClient + ":\r\n" + 
				assemblerFacture(nbE, cout, "Av taxe:") +
				assemblerFacture(nbE, coutTPS, "TPS(5%):") +
				assemblerFacture(nbE, coutTVQ, "TVQ(10%):") +
				assemblerFacture(nbE, total, "Total:"));
		return facture;
	}
	/**
	 * Defini le nombre d'espaces nécessaires pour ajuster l'affichage puis assemble la chaine
	 * 
	 * @param nbEspaces
	 * 			Contient un nombre d'espaces calculé pour empêcher les débordements
	 * @param cout
	 * 			Contient le cout courrant
	 * @param titre
	 * 			Contient le titre qui défini le cout courrant
	 * @return
	 * 			Une ligne de la facture
	 */
	static String assemblerFacture(int nbEspaces, double cout, String titre) {
		String sCout = String.format("%.2f", cout);
		char[] espaces = new char[nbEspaces-(titre+sCout).length()];
		Arrays.fill(espaces, ' ');
		return (titre + new String(espaces) + sCout + "$\r\n");
	}
	/**
	 * Création du fichier d'un fichier où les factures sont sauvegardées
	 * @param nomFichier
	 * 			Contient le nom du fichier
	 */
	public static void creerFichierFacture(String nomFichier){
		PrintWriter sortie;
		try {
			sortie = new PrintWriter(nomFichier);
		sortie.println("Factures:");
		for (Client cli : clients) {
			double total = 0;
			for (Commande com : commandes) {
				total += (com.client.nom.equals(cli.nom)) ? (com.plat.cout * com.qte): 0;
			}
			sortie.println(cli.nom + " " + total + "$");
		}
		sortie.close();
		} catch (FileNotFoundException e) {
			
			e.printStackTrace();
		}
	}

}