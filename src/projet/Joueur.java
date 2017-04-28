package projet;

import javafx.application.Platform;

/**
 * Created by houdebil on 28/04/17.
 */
public class Joueur {
    protected Moteur moteur;
    private String nom;

    public Joueur(Moteur m, String nom) {
        this.moteur = m;
        this.nom = nom;
    }

    public int action(Point p) {
        int r = moteur.action(p);
        System.out.println("[" + nom + "] action: " + p);
        if (r == 2) {
            System.out.println("Defaite: " + this.nom);
            Platform.exit();
        }
        return r;
    }



}
