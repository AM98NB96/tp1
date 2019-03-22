package main;

/**
 * La classe erreur contient les informations de l'erreur.
 *
 * @param commande
 * 			Contient la commande erronée.
 * @param codeErreur
 * 			Contient le code d'erreur.
 */
public class Erreur {
	
	String commande;
	String codeErreur;
	
	/**
	 * Constructeur de la classe commande.
	 * 
	 * @param commande
	 * 			Contient la commande erronée.
	 * @param codeErreur
	 * 			Contient le code d'erreur.
	 */
	public Erreur(String commande, String codeErreur) {
		this.commande = commande;
		this.codeErreur = codeErreur;
	}
	
	/**
	 * Méthode pour afficher les erreurs.
	 * 
	 * @param sortie
	 * 			Sortie de fichier
	 */
	public String afficherErreur() {
		return "Erreur de la commande: " + this.commande + "\n" + "Code d'erreur: " + this.codeErreur;
	}
	
	@Override
	public boolean equals(Object obj) {
		return (this.codeErreur.equals(((Erreur)obj).codeErreur) && this.commande.equals(((Erreur)obj).commande));
	}
}
