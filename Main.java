import java.io.*;
import java.util.LinkedList;
import java.util.Scanner;

/**
 * Classe principale permettant l'execution du programme.
 * 
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
			System.out.println("Le fichier ne respecte pas le format demand� !");
		}
		
		try {
			creerFacture("facture.txt");
		} catch (Exception e) {
			System.out.println("erreur");
		}
		
	}
	/**
	 * Lecture du fichier objet pour cr�er les objets qui y sont sauvegard�s
	 * @param fichier
	 * @throws FileNotFoundException
	 */
	static void lireFichierObjet(String fichier) throws FileNotFoundException {
		
		Scanner input = new Scanner(new FileReader(fichier));
		
		String typeObj = "";
		
		while (input.hasNextLine()) {
			
			String tmpString = input.nextLine();
				//Type d'objet courrant
			if (tmpString.equals("Clients :") || tmpString.equals("Plats :") || tmpString.equals("Commandes :")) {
				
				typeObj = tmpString;
				
			}else{
				
				//S�paration des lignes du fichier d'objets
				String[] tmp = tmpString.split(" ");
				
				switch (typeObj) {
				
				//Cr�ation des clients
				case "Clients :":					
					clients.add(new Client(tmpString));					
					break;
					
				//Cr�ation des plats
				case "Plats :":
					plats.add(new Plat(tmp[0], Double.parseDouble(tmp[1])));
					break;

				//Cr�ation des commandes
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
				}
			}
		}
		System.out.println(" -- Waat -- ");
		for (Client c : clients) {
			System.out.println(c.nom);
		}
		System.out.println(" -- Wuut -- ");
		for (Plat p : plats) {
			System.out.println(p.nom + " " + p.cout);
		}
		System.out.println(" -- Woot -- ");
		for (Commande c : commandes) {
			System.out.println(c.client.nom + " " + c.plat.nom + " " + c.qte);
		}
		input.close();
	}
	/**
	 * Cr�ation du fichier d'un fichier o� les factures sont sauvegard�es
	 * @param nomFichier
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