import java.util.*;

/**
 * La classe client contient les informations de la commande.
 *
 * @param client
 * 			Contient les informations du client.
 * @param platCommande
 * 			Contient les informations du plat choisi.
 * @param nbDeCommande
 * 			Le nombre de fois que le plat a été commandé.
 */
public class Commande {
	
	Client client;
	Plat platCommande;
	int nbDeCommande;
	
	/**
	 * Constructeur de la classe commande.
	 * 
	 * @param client
	 * 			Contient les informations du client.
	 * @param platCommande
	 * 			Contient les informations du plat choisi.
	 * @param nbDeCommande
	 * 			Le nombre de fois que le plat a été commandé.
	 */
	public Commande(Client client, Plat platCommande, int nbDeCommande) {
		this.client = client;
		this.platCommande = platCommande;
		this.nbDeCommande = nbDeCommande;
	}
	
}