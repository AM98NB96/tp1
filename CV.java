import java.util.*;

public class CV {
	
	Private String nom;
	Private String prenom;
	Private String formation;
	Private int experience;
	List<String> competence;
	Private String  attente;
	
	public void affiche() {
		System.out.print("Nom: " + this.nom
                + "\nPrenom: " + this.prenom
                + "\nFormation: " + this.formation
                + "\nExperience: " + this.experience
                + "\nCompetences: ");
        for (String competence : competences) {
            System.out.print("\n" + competence);
        }
        System.out.println("\nAttentes: " + this.Attentes + "\n");
	}
	
	public void main() {
		CV cvNicolas = new CV("Barrette", "Nicolas", "CFP des riverains", 3, Arrays.asList("Programmation", "Soutien technique"), "Passer le cours");
        CV cvAntoine = new CV("Martineau", "Antoine", "CFP des riverains", 1, Arrays.asList("Programmation", "Soutien technique"), "Apprendre à faire un logiciel de qualité.");
		
		System.out.println("Bienvenue chez Barrette!");
		
		cvNicolas.affiche();
		cvAntoine.affiche();
	}
}