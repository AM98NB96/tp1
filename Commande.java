import java.util.*;

/**
 * La classe client contient les informations de la commande.
 *
 * @param client
 * 			Contient les informations du client.
 * @param plat
 * 			Contient les informations du plat choisi.
 * @param qte
 * 			La quantité.
 */
public class Commande {
	
	Client client;
	Plat plat;
	int qte;
	
	/**
	 * Constructeur de la classe commande.
	 * 
	 * @param client
	 * 			Contient les informations du client.
	 * @param plat
	 * 			Contient les informations du plat choisi.
	 * @param qte
	 * 			La quantité.
	 */
	public Commande(Client client, Plat plat, int qte) {
		this.client = client;
		this.plat = plat;
		this.qte = qte;
	}
	
}