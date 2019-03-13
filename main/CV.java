package main;
import java.util.*;

/**
 * La classe CV contient les informations du CV des usagers.
 * 
 * @param nom
 * 			Nom de l'usager.
 * @param prenom
 * 			Prenom de l'usager.
 * @param formation
 * 			Formation de l'usager.
 * @param experience
 * 			Experience de l'usager.
 * @param competence
 * 			Competence de l'usager.
 * @param attente
 * 			Attente de l'usager vis-à-vis le cours.
 */

public class CV {
	
	private String nom;
	private String prenom;
	private String formation;
	private int experience;
	List<String> competence;
	private String  attente;
	
	/**
	 * Constructeur de la classe CV.
	 * 
	 * @param nom
	 * 			Nom de l'usager.
	 * @param prenom
	 * 			Prenom de l'usager.
	 * @param formation
	 * 			Formation de l'usager.
	 * @param experience
	 * 			Experience de l'usager.
	 * @param competence
	 * 			Competence de l'usager.
	 * @param attente
	 * 			Attente de l'usager vis-à-vis le cours.
	 */
	
	public CV(String nom, String prenom, String formation, int experience, List<String> competences, String attentes) {
        this.nom = nom;
        this.prenom = prenom;
        this.formation = formation;
        this.experience = experience;
        this.competence = competences;
        this.attente = attentes;
    }
	
	/**
	 * La méthode affiche permet d'afficher les informations de l'usager.
	 */
	
	public void affiche() {
		System.out.print("Nom: " + this.nom
                + "\nPrenom: " + this.prenom
                + "\nFormation: " + this.formation
                + "\nExperience: " + this.experience
                + "\nCompetences: ");
        for (String competence : competence) {
            System.out.print("\n" + competence);
        }
        System.out.println("\nAttentes: " + this.attente + "\n");
	}
	
	/**
	 * La méthode main créé des objets de type CV, affiche un message de bienvenue et les objets créés.
	 */
	
	public void main() {
		CV cvNicolas = new CV("Barrette", "Nicolas", "CFP des riverains", 3, Arrays.asList("Programmation", "Soutien technique"), "Passer le cours");
        CV cvAntoine = new CV("Martineau", "Antoine", "CFP des riverains", 1, Arrays.asList("Programmation", "Soutien technique"),
        					  "Apprendre à faire un logiciel de qualité.");
		
		System.out.println("Bienvenue chez Barrette!");
		
		cvNicolas.affiche();
		cvAntoine.affiche();
	}
}