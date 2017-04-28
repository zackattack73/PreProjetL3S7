package projet.model;

import javafx.application.Platform;

public class Joueur {
    protected Terrain terrain;
    private String nom;
    private int score;

    public Joueur(Terrain terrain, String nom) {
        this.terrain = terrain;
        this.nom = nom;
        this.score = 0;
    }

    public int getScore() {
        return score;
    }

    public void plusScore(int score) {
        this.score += score;
    }

    public String getNom() {
        return nom;
    }

    public int action(Point p) {
        int r = terrain.action(p);
        System.out.println("[" + nom + "] action: " + p);
        if (r == 2) {
            System.out.println("Defaite: " + this.nom);
            Platform.exit();
        }
        return r;
    }
}
