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
	
	static LinkedList<Client> clients = new LinkedList<Client>();
	static LinkedList<Plat> plats = new LinkedList<Plat>();
	static LinkedList<Commande> commandes = new LinkedList<Commande>();
	
	public static void main(String[] args) {
	
		try {
			lireFichierObjet("test.txt");
		}catch (Exception e) {
			System.out.println("Le fichier ne respecte pas le format demandé !");
		}
		
		try {
			creerFacture("facture.txt");
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
	static void lireFichierObjet(String fichier) throws FileNotFoundException {
		
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
	}
	/**
	 * Création du fichier d'un fichier où les factures sont sauvegardées
	 * @param nomFichier
	 * 			Contient le nom du fichier
	 * @throws FileNotFoundException
	 */
	static void creerFacture(String nomFichier) throws FileNotFoundException {
		PrintWriter sortie = new PrintWriter(nomFichier);
		sortie.println("Factures:");
		for (Client cli : clients) {
			double total = 0;
			for (Commande com : commandes) {
				total += (com.client.nom.equals(cli.nom)) ? (com.plat.cout * com.qte): 0;
			}
			sortie.println(cli.nom + " " + total + "$");
		}
		sortie.close();
	}

}