/**
 * La classe Plat contient les informations du plat.
 * @param nom
 * 		Le nom du plat.
 * @param cout
 * 		Le cout du plat.
 */
public class Plat {
	
	public String nom;
	public double cout;
	
	/**
	 * Constructeur avec paramêtre de la classe plat
	 * 
	 * @param nom
	 * 		Le nom du plat
	 * 
	 */
	public Plat() {}
	
	public Plat(String nom) {
		this.nom = nom;
	}
	/**
	 * Constructeur avec paramêtre de la classe plat
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
