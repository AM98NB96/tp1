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
	public Erreur() {
		this.commande = "";
		this.codeErreur = "";
		
	}
	public Erreur(String commande, String codeErreur) {
		this.commande = commande;
		this.codeErreur = codeErreur;
	}
	
	@Override
	public boolean equals(Object obj) {
		return (this.codeErreur.equals(((Erreur)obj).codeErreur) && this.commande.equals(((Erreur)obj).commande));
	}

	public static Erreur verifCarEspace(char car, String tmpString, String type, Erreur err) {
		if (Character.isWhitespace(car)) {
			err = new Erreur(tmpString , "Le format du " + type + " est invalide, car il ne doit pas contenir d'espaces.");
		}
		return err;
	}
	public static Erreur verifCarSpeciaux(char car, String tmpString, String type, Erreur err) {
		if (!Character.isLetterOrDigit(car)) {
			err = new Erreur(tmpString , "Le format du " + type + " est invalide, car il ne doit pas contenir de caracteres spéciaux autre que '-'.");
		}
		return err;
	}
	public static Erreur verifCarNumerique(char car, String tmpString, String type, Erreur err) {
		if (Character.isDigit(car)) {
			err = new Erreur(tmpString , "Le format du " + type + " est invalide, car il ne doit pas contenir de caracteres numériques.");
		}
		return err;
	}
	
	public static Erreur verifFormatClient(char car, String tmpString, boolean erreur) {
		Erreur err = new Erreur();
		
		if (!Character.isLetter(car) && car != '-' && !erreur) {
			
			err = Erreur.verifCarEspace(car, tmpString, "client", err);
			err = (err.codeErreur == "") ? Erreur.verifCarSpeciaux(car, tmpString, "client", err) : err;
			err = (err.codeErreur == "") ? Erreur.verifCarNumerique(car, tmpString, "client", err) : err;
			
		}
		
		return err;
	}
	
}
