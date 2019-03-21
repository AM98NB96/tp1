package main;
import java.io.*;
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
		
		if (commandes.size() > 0) {
			creerFacture("facture.txt");
		}
	}
	/**
	 * Lecture du fichier objet pour créer les objets qui y sont sauvegardés
	 * @param fichier
	 * 			Contient le nom du fichier
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
					boolean erreur = false;
					
					switch (typeObj) {
					
						//Création des clients
						case "Clients :":
							for (char car : tmpString.toCharArray()) {
								if (!Character.isLetter(car) && car != '-') {
									if (Character.isWhitespace(car)) {
										erreurs.add(new Erreur(tmpString , "Le format du client est invalide, car il ne doit pas contenir d'espaces."));
									} else if (Character.isDigit(car)) {
										erreurs.add(new Erreur(tmpString , "Le format du client est invalide, car il ne doit pas contenir de caracteres numériques."));
									} else  {
										erreurs.add(new Erreur(tmpString , "Le format du client est invalide, car il ne doit pas contenir de caracteres spéciaux autre que '-'."));
									}
								}
							}
							
							clients.add(new Client(tmpString));					
							break;
							
						//Création des plats
						case "Plats :":
							if (tmp.length == 1) {
								erreurs.add(new Erreur(tmpString , "Le format du plat est invalide, car il manque un paramêtre."));
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
		
						//Création des commandes
						case "Commandes :":
							
							Client client = new Client();
							Plat plat = new Plat();
							int qte = 0;
							
							// Test Client
							for (char car : tmp[0].toCharArray()) {
								if (!Character.isLetter(car) && car != '-') {
									if (Character.isDigit(car)) {
										erreurs.add(new Erreur(tmpString , "Le format du client est invalide, car il ne doit pas contenir de caracteres numériques."));
									} else {
										erreurs.add(new Erreur(tmpString , "Le format du client est invalide, car il ne doit pas contenir de caracteres spéciaux autre que '-'."));
									}
									erreur = true;
								}
							}
							
							for (Client c : clients) {
								if (c.nom.equals(tmp[0])) client = c;
								else {
									erreurs.add(new Erreur(tmpString , "Le client n'existe pas."));
									erreur = true;
								}
							}
							
							// Test Plat
							for (Plat p : plats) {
								if (p.nom.equals(tmp[1])) plat = p;
								else {
									erreurs.add(new Erreur(tmpString , "Le plat n'existe pas."));
									erreur = true;
								}
							}
							
							//Test Quantité
							if (tmp.length < 3) {
								erreurs.add(new Erreur(tmpString, "Le format de la commande est invalide, car il manque un paramêtre."));
								erreur = true;
							} else {
								try {
									qte = Integer.parseInt(tmp[2]);
								} catch (NumberFormatException e) {
									erreurs.add(new Erreur(tmpString, "Le format de la commande est invalide, car " + tmp[2] + " n'est pas une quantité."));
									erreur = true;
								}
								
								if (!erreur && qte < 0) {
									erreurs.add(new Erreur(tmpString, "La quantité ne peut pas être inférieure à 0."));
									erreur = true;
								} else if (!erreur && qte == 0) {
									erreurs.add(new Erreur(tmpString, "La quantité ne peut pas être 0."));
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
			input.close();
			
		} catch (FileNotFoundException e) {
			System.out.println("Le fichier " + fichier + " n'a pas été trouvé.");
		}
	}
	/**
	 * Création du fichier d'un fichier où les factures sont sauvegardées
	 * @param nomFichier
	 * 			Contient le nom du fichier
	 */
	public static String creerFacture(String nomFichier) {
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
			System.out.println("Le fichier " + nomFichier + "n'a pas été trouvé.");
		}
		
		return "";
	}

}