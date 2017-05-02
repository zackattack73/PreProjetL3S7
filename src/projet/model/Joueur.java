package projet.model;

import java.util.Random;

public class Joueur {
    private Jeu jeu;
    private String nom;
    private int score;

    public Joueur(String nom) {
        this(null, nom);
    }

    public Joueur(Jeu jeu, String nom) {
        this.jeu = jeu;

        if (nom.length() == 0 && this.jeu != null) {
            this.nom = this.jeu.getNomDispo().get(new Random().nextInt(this.jeu.getNomDispo().size()));
            this.jeu.getNomDispo().remove(this.nom);
        } else {
            this.nom = nom;
        }

        this.score = 0;
    }

    public void setJeu(Jeu jeu) {
        this.jeu = jeu;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public void plusScore(int score) {
        this.score += score;
    }

    public String getNom() {
        return nom;
    }

    public Jeu getJeu() {
        return this.jeu;
    }

    public Point action(Point p) {
        System.out.println("[" + nom + "] action: " + p);
        return p;
    }

    public String toString() {
        return this.nom + ": " + this.score + " pts";
    }

    // gets name with attributes (not to write on file!)
    public String getNomFinal() {
        return this.nom;
    }
}
