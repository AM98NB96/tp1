package test;

import main.*;


import static org.junit.Assert.assertEquals;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.LinkedList;
import java.util.Scanner;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.sun.org.apache.xerces.internal.util.SynchronizedSymbolTable;

public class MainTest {
	
	@Before
	public void avantChaqueTest() {
		Main.clients = new LinkedList<Client>();
		Main.plats = new LinkedList<Plat>();
		Main.commandes = new LinkedList<Commande>();
		Main.erreurs = new LinkedList<Erreur>();
	}
	
	/* ***************************************************
	 * Test methode CreerFacture()
	 *************************************************** */
	@Test
	public void testCreerFacture() {
		Main.lireFichierObjet("test.txt");
		
		assertEquals(Main.creerFacture("Roger", 10.5), "Facture de Roger:\n" + 
													   "Av taxe:             10.50$\n" + 
													   "TPS(5%):              0.53$\n" + 
													   "TVQ(10%):             1.05$\n" + 
													   "Total:               12.08$");		
	}

	/* ***************************************************
	 * Test methode CreerFichierFacture()
	 *************************************************** */
	
	@Test
	public void testCreerFichierFactureMontantDeZero() {
		String resultatAttendu = "";
		
		Main.lireFichierObjet("test.txt");
		
		Main.creerFichierFacture("Facture.txt");
		
		try {
			Scanner input = new Scanner(new FileReader("Facture.txt"));
		
			while (input.hasNextLine()) {
				resultatAttendu += input.nextLine() + "\n";
			}
			input.close();
			
		} catch (FileNotFoundException e) {
			System.out.println("Le fichier n'a pas été trouvé.");
		}
		
		assertEquals(resultatAttendu, "Factures:\n" + 
									  "Facture de Roger:\n" + 
									  "Av taxe:             10.50$\n" + 
									  "TPS(5%):              0.53$\n" + 
									  "TVQ(10%):             1.05$\n" + 
									  "Total:               12.08$\n" + 
									  "Facture de Céline:\n" + 
									  "Av taxe:             20.75$\n" + 
									  "TPS(5%):              1.04$\n" + 
									  "TVQ(10%):             2.08$\n" + 
									  "Total:               23.86$\n");
	}
	
	@Test
	public void testCreerFichierFactureClientNonExistant() {
		
		String resultatAttendu = "";
		
		Main.lireFichierObjet("fichierTest\\testClientExistePas.txt");
		
		Main.creerFichierFacture("Facture.txt");
		
		try {
			Scanner input = new Scanner(new FileReader("Facture.txt"));
		
			while (input.hasNextLine()) {
				resultatAttendu += input.nextLine() + "\n";
			}
			input.close();
			
		} catch (FileNotFoundException e) {
			System.out.println("Le fichier n'a pas été trouvé.");
		}
		
		assertEquals(resultatAttendu, "Erreur de la commande: Joe Poutine 1\n" + 
									  "Code d'erreur: Le client n'existe pas.\n");			
	}
	
	@Test
	public void testCreerFichierFacturePlatNonExistant() {
		
		String resultatAttendu = "";
		
		Main.lireFichierObjet("fichierTest\\testPlatExistePas.txt");
		
		Main.creerFichierFacture("Facture.txt");
		
		try {
			Scanner input = new Scanner(new FileReader("Facture.txt"));
		
			while (input.hasNextLine()) {
				resultatAttendu += input.nextLine() + "\n";
			}
			input.close();
			
		} catch (FileNotFoundException e) {
			System.out.println("Le fichier n'a pas été trouvé.");
		}
		
		assertEquals(resultatAttendu, "Erreur de la commande: Roger Poulet 1\n" + 
									  "Code d'erreur: Le plat n'existe pas.\n");			
	}
	
	@Test
	public void testCreerFichierFactureQuantiteImpossible() {
		
		String resultatAttendu = "";
		
		Main.lireFichierObjet("fichierTest\\testQuantiteInvalide.txt");
		
		Main.creerFichierFacture("Facture.txt");
		
		try {
			Scanner input = new Scanner(new FileReader("Facture.txt"));
		
			while (input.hasNextLine()) {
				resultatAttendu += input.nextLine() + "\n";
			}
			input.close();
			
		} catch (FileNotFoundException e) {
			System.out.println("Le fichier n'a pas été trouvé.");
		}
		
		assertEquals(resultatAttendu, "Erreur de la commande: Roger Poutine -1\n" + 
									  "Code d'erreur: La quantité ne peut pas être inférieure à 0.\n" + 
									  "Erreur de la commande: Roger Poutine 0\n" + 
									  "Code d'erreur: La quantité ne peut pas être 0.\n");		
	}
	
	@Test
	public void testCreerFichierFactureFormatNonRespecter() {
		
		String resultatAttendu = "";
		
		Main.lireFichierObjet("fichierTest\\testFormatCommande.txt");
		
		Main.creerFichierFacture("Facture.txt");
		
		try {
			Scanner input = new Scanner(new FileReader("Facture.txt"));
		
			while (input.hasNextLine()) {
				resultatAttendu += input.nextLine() + "\n";
			}
			input.close();
			
		} catch (FileNotFoundException e) {
			System.out.println("Le fichier n'a pas été trouvé.");
		}
		
		assertEquals(resultatAttendu, "Erreur de la commande: Roger Poutine\n" + 
									  "Code d'erreur: Le format de la commande est invalide, car il manque un paramêtre.\n" + 
									  "Erreur de la commande: Roger Poutine blah\n" + 
									  "Code d'erreur: Le format de la commande est invalide, car blah n'est pas une quantité.\n" + 
									  "Erreur de la commande: Roger Poutine 0.5\n" + 
									  "Code d'erreur: Le format de la commande est invalide, car 0.5 n'est pas une quantité.\n");					
	}

	/* ***************************************************
	 * Test methode LireFichierObjet()
	 *************************************************** */
	
	// Test d'existance
	@Test
	public void testLireFichierObjetClientExistePas() {
		Main.lireFichierObjet("fichierTest\\testClientExistePas.txt");
		
		Erreur erreur = new Erreur("Joe Poutine 1", "Le client n'existe pas.");
		
		assertEquals(Main.erreurs.get(0), erreur);
	}
	
	@Test
	public void testLireFichierObjetPlatExistePas() {
		Main.lireFichierObjet("fichierTest\\testPlatExistePas.txt");
		
		Erreur erreur = new Erreur("Roger Poulet 1", "Le plat n'existe pas.");
		
		assertEquals(Main.erreurs.get(0), erreur);
	}

	// Test de formats
	@Test
	public void testLireFichierObjetFormatClient() {
		
		Main.lireFichierObjet("fichierTest\\testFormatClient.txt");
		
		Erreur erreur = new Erreur("Roger Dulac", "Le format du client est invalide, car il ne doit pas contenir d'espaces.");
		Erreur erreur1 = new Erreur("Robert?", "Le format du client est invalide, car il ne doit pas contenir de caracteres spéciaux autre que '-'.");
		Erreur erreur2 = new Erreur("Regeant123", "Le format du client est invalide, car il ne doit pas contenir de caracteres numériques.");
		
		assertEquals(Main.erreurs.get(0), erreur);
		assertEquals(Main.erreurs.get(1), erreur1);
		assertEquals(Main.erreurs.get(2), erreur2);
	}
	
	@Test
	public void testLireFichierObjetFormatPlat(){
		
		Main.lireFichierObjet("fichierTest\\testFormatPlat.txt");
		
		Erreur erreur = new Erreur("Poutine", "Le format du plat est invalide, car il manque un paramêtre.");
		Erreur erreur1 = new Erreur("Poutine blah", "Le format du plat est invalide, car blah n'est pas un prix valide.");
		
		assertEquals(Main.erreurs.get(0), erreur);
		assertEquals(Main.erreurs.get(1), erreur1);
	}
	
	@Test
	public void testLireFichierObjetFormatCommande(){
		
		Main.lireFichierObjet("fichierTest\\testFormatCommande.txt");
		
		Erreur erreur = new Erreur("Roger Poutine", "Le format de la commande est invalide, car il manque un paramêtre.");
		Erreur erreur1 = new Erreur("Roger Poutine blah", "Le format de la commande est invalide, car blah n'est pas une quantité.");
		Erreur erreur2 = new Erreur("Roger Poutine 0.5", "Le format de la commande est invalide, car 0.5 n'est pas une quantité.");
		
		assertEquals(Main.erreurs.get(0), erreur);
		assertEquals(Main.erreurs.get(1), erreur1);
		assertEquals(Main.erreurs.get(2), erreur2);
	}
	// Test de validité
	@Test
	public void testLireFichierObjetPrixInvalide() {
		
		Main.lireFichierObjet("fichierTest\\testPrixInvalide.txt");
		
		Erreur erreur = new Erreur("Poutine -3", "Le format du plat est invalide, car -3 n'est pas un prix valide.");

		assertEquals(Main.erreurs.get(0), erreur);
	}
	
	@Test
	public void testLireFichierObjetQuantiteInvalide() {
		
		Main.lireFichierObjet("fichierTest\\testQuantiteInvalide.txt");
		
		Erreur erreur = new Erreur("Roger Poutine -1", "La quantité ne peut pas être inférieure à 0.");
		Erreur erreur1 = new Erreur("Roger Poutine 0", "La quantité ne peut pas être 0.");
		
		assertEquals(Main.erreurs.get(0), erreur);
		assertEquals(Main.erreurs.get(1), erreur1);
	}

	@After
	public void apresChaqueTest() {
		Main.clients = null;
		Main.plats = null;
		Main.commandes = null;
		Main.erreurs = null;
	}
}
