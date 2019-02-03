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
        for (String competence : Competences) {
            System.out.print("\n" + competence);
        }
        System.out.println("\nAttentes: " + this.Attentes + "\n");
	}
}