package test;

import main.*;

import static org.junit.Assert.assertEquals;

import java.util.LinkedList;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
public class MainTest {
	
	@Before
	public void avantChaqueTest() {
		Main.clients = new LinkedList<Client>();
		Main.plats = new LinkedList<Plat>();
		Main.commandes = new LinkedList<Commande>();
	}
	
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
													   "Code d'erreur: La quantité doit être d'au moins 1.\r\n" +
													   "Erreur de la commande: Joe Spaghetti -1\r\n" +
													   "Code d'erreur: La quantité doit être d'au moins 1.\r\n" +
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
													   "Code d'erreur: Le format de la commande n'est pas respecté.\r\n" +
													   "Facture de Joe:\r\n" + 
													   "Av taxe:            10.00$\r\n" + 
													   "TPS(5%):             0.50$\r\n" + 
													   "TVQ(10%):            1.00$\r\n" + 
											 		   "Total:              11.50$");			
	}
	
	@After
	public void apresChaqueTest() {
		Main.clients = null;
		Main.plats = null;
		Main.commandes = null;
	}
}
