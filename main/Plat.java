package main;
/**
 * La classe Plat contient les informations du plat.
 * @param nom
 * 		Le nom du plat.
 * @param cout
 * 		Le cout du plat.
 */
public class Plat {
	
	String nom;
	double cout;
	
	/**
	 * Constructeur avec paramÍtre de la classe plat
	 * 
	 * @param nom
	 * 		Le nom du plat
	 * 
	 */
	public Plat(String nom) {
		this.nom = nom;
	}
	/**
	 * Constructeur avec paramÍtre de la classe plat
	 * 
	 * @param nom
	 * 		Le nom du plat
	 * @param cout
	 * 		Le cout du plat
	 * 
	 */
	
	public Plat(String nom, double cout) {
		this.nom = nom;
		this.cout = cout;
	}
	
}
