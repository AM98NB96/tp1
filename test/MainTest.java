package test;

import main.*;


import static org.junit.Assert.assertEquals;

import java.io.FileNotFoundException;
import java.util.LinkedList;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

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
		
		Main.clients.add(new Client("Joe"));
		Main.clients.add(new Client("Paul"));
		
		Main.plats.add(new Plat("Spaghetti", 10));
		Main.plats.add(new Plat("Poutine", 6));
		
		Main.commandes.add(new Commande(Main.clients.get(0), Main.plats.get(0), 1));
		Main.commandes.add(new Commande(Main.clients.get(1), Main.plats.get(1), 2));
		
		assertEquals(Main.creerFacture("Facture.txt"), "Facture de Joe:\r\n" + 
													   "Av taxe:            10.00$\r\n" + 
													   "TPS(5%):             0.50$\r\n" + 
													   "TVQ(10%):            1.00$\r\n" + 
											 		   "Total:              11.50$\r\n" +
											 		   "Facture de Paul:\r\n" + 
											 		   "Av taxe:            12.00$\r\n" + 
											 		   "TPS(5%):             0.60$\r\n" + 
											 		   "TVQ(10%):            1.20$\r\n" + 
											 		   "Total:              13.80$");			
	}

	@Test
	public void testCreerFactureMontantDeZero() {
		
		Main.clients.add(new Client("Joe"));
		Main.clients.add(new Client("Paul"));
		
		Main.plats.add(new Plat("Spaghetti", 10));
		
		Main.commandes.add(new Commande(Main.clients.get(0), Main.plats.get(0), 1));
		
		assertEquals(Main.creerFacture("Facture.txt"), "Facture de Joe:\r\n" + 
													   "Av taxe:            10.00$\r\n" + 
													   "TPS(5%):             0.50$\r\n" + 
													   "TVQ(10%):            1.00$\r\n" + 
											 		   "Total:              11.50$");			
	}
	
	@Test
	public void testCreerFactureClientNonExistant() {
		
		Main.clients.add(new Client("Joe"));
		
		Main.plats.add(new Plat("Spaghetti", 10));
		
		Main.commandes.add(new Commande(Main.clients.get(0), Main.plats.get(0), 1));
		Main.commandes.add(new Commande(new Client("Paul"), Main.plats.get(0), 1));
		
		assertEquals(Main.creerFacture("Facture.txt"), "Erreur de la commande: Paul Spaghetti 1\r\n" +
													   "Code d'erreur: Le client n'existe pas.\r\n" +
													   "Facture de Joe:\r\n" + 
													   "Av taxe:            10.00$\r\n" + 
													   "TPS(5%):             0.50$\r\n" + 
													   "TVQ(10%):            1.00$\r\n" + 
											 		   "Total:              11.50$");			
	}
	
	@Test
	public void testCreerFacturePlatNonExistant() {
		
		Main.clients.add(new Client("Joe"));
		Main.clients.add(new Client("Paul"));
		
		Main.plats.add(new Plat("Spaghetti", 10));
		
		Main.commandes.add(new Commande(Main.clients.get(0), Main.plats.get(0), 1));
		Main.commandes.add(new Commande(Main.clients.get(1), new Plat("Poutine", 6), 1));
		
		assertEquals(Main.creerFacture("Facture.txt"), "Erreur de la commande: Paul Poutine 1\r\n" +
													   "Code d'erreur: Le plat n'existe pas.\r\n" +
													   "Facture de Joe:\r\n" + 
													   "Av taxe:            10.00$\r\n" + 
													   "TPS(5%):             0.50$\r\n" + 
													   "TVQ(10%):            1.00$\r\n" + 
											 		   "Total:              11.50$");			
	}
	
	@Test
	public void testCreerFactureQuantiteImpossible() {
		
		Main.clients.add(new Client("Joe"));
		
		Main.plats.add(new Plat("Spaghetti", 10));
		
		Main.commandes.add(new Commande(Main.clients.get(0), Main.plats.get(0), 0));
		Main.commandes.add(new Commande(Main.clients.get(0), Main.plats.get(0), -1));
		Main.commandes.add(new Commande(Main.clients.get(0), Main.plats.get(0), 1));
		
		assertEquals(Main.creerFacture("Facture.txt"), "Erreur de la commande: Joe Spaghetti 0\r\n" +
													   "Code d'erreur: La quantité ne peut pas être 0.\r\n" +
													   "Erreur de la commande: Joe Spaghetti -1\r\n" +
													   "Code d'erreur: La quantité ne peut pas être inférieure à 0.\r\n" +
													   "Facture de Joe:\r\n" + 
													   "Av taxe:            10.00$\r\n" + 
													   "TPS(5%):             0.50$\r\n" + 
													   "TVQ(10%):            1.00$\r\n" + 
											 		   "Total:              11.50$");			
	}
	
	@Test
	public void testCreerFactureFormatNonRespecter() {
		
		Main.clients.add(new Client("Joe"));
		
		Main.plats.add(new Plat("Spaghetti", 10));
		Main.plats.add(new Plat("Poutine", 6));
		
		Main.commandes.add(new Commande(Main.clients.get(0), Main.plats.get(0), 0));
		Main.commandes.add(new Commande(new Client("JoePoutine"), new Plat("1"), 0));
		
		assertEquals(Main.creerFacture("Facture.txt"), "Erreur de la commande: JoePoutine 0\r\n" +
													   "Code d'erreur: Le format de la commande est invalide, car il manque un paramêtre.\r\n" +
													   "Facture de Joe:\r\n" + 
													   "Av taxe:            10.00$\r\n" + 
													   "TPS(5%):             0.50$\r\n" + 
													   "TVQ(10%):            1.00$\r\n" + 
											 		   "Total:              11.50$");			
	}

	/* ***************************************************
	 * Test methode LireFichierObjet()
	 *************************************************** */
	
	// Test d'existance
	public void testLireFichierObjetClientExistePas() {
		Main.lireFichierObjet("testClientExistePas.txt");
		Erreur erreur = new Erreur("Joe Poutine 1", "Le client n'existe pas.");
		assertEquals(Main.erreurs.get(0), erreur);
	}
	
	public void testLireFichierObjetPlatExistePas() {
		Main.lireFichierObjet("testPlatExistePas.txt");
		
		Erreur erreur = new Erreur("Roger Poulet 1", "Le plat n'existe pas.");
		
		assertEquals(Main.erreurs.get(0), erreur);
	}

	// Test de formats
	public void testLireFichierObjetFormatClient() {
		
		Main.lireFichierObjet("testFormatClient.txt");
		
		Erreur erreur = new Erreur("Roger Dulac", "Le format du client est invalide, car il ne doit pas contenir d'espaces.");
		Erreur erreur1 = new Erreur("Robert?", "Le format du client est invalide, car il ne doit pas contenir de caracteres spéciaux autre que '-'.");
		Erreur erreur2 = new Erreur("Regeant123", "Le format du client est invalide, car il ne doit pas contenir de caracteres numériques.");
		
		assertEquals(Main.erreurs.get(0), erreur);
		assertEquals(Main.erreurs.get(1), erreur1);
		assertEquals(Main.erreurs.get(2), erreur2);
	}
	
	public void testLireFichierObjetFormatPlat(){
		
		Main.lireFichierObjet("testFormatPlat.txt");
		
		Erreur erreur = new Erreur("Poutine", "Le format du plat est invalide, car il manque un paramêtre.");
		Erreur erreur1 = new Erreur("Poutine blah", "Le format du plat est invalide, car blah n'est pas un prix.");
		
		assertEquals(Main.erreurs.get(0), erreur);
		assertEquals(Main.erreurs.get(1), erreur1);
		
	}
	
	public void testLireFichierObjetFormatCommande(){
		
		Main.lireFichierObjet("testFormatCommande.txt");
		
		Erreur erreur = new Erreur("Roger Poutine", "Le format de la commande est invalide, car il manque un paramêtre.");
		Erreur erreur1 = new Erreur("Roger Poutine blah", "Le format de la commande est invalide, car blah n'est pas une quantité.");
		Erreur erreur2 = new Erreur("Roger Poutine 0.5", "Le format de la commande est invalide, car 0.5 n'est pas une quantité.");
		
		assertEquals(Main.erreurs.get(0), erreur);
		assertEquals(Main.erreurs.get(1), erreur1);
		assertEquals(Main.erreurs.get(2), erreur2);
	}
	// Test de validité
	public void testLireFichierObjetPrixInvalide() {
		
		Main.lireFichierObjet("testPrixInvalide.txt");
		
		Erreur erreur = new Erreur("Poutine -3", "Le prix du plat ne peut pas être égal ou inférieur à 0.");
		
		assertEquals(Main.erreurs.get(0), erreur);
	}
	
	public void testLireFichierObjetQuantiteInvalide() {
		
		Main.lireFichierObjet("testQuantiteInvalide.txt");
		
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
	}
}
