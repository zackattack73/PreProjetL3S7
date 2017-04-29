package projet.model;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Observable;
import java.util.logging.Logger;

/**
 * Package ${PACKAGE} / Project PreProjetL3S7.
 * Date 2017 04 28.
 * Created by Nico (21:14).
 */
public class Jeu extends Observable {
    private class Configuration {
        private int tour;
        private Point point;
        private int[] scores;

        public Configuration(int tour, Point point, int[] scores) {
            this.tour = tour;
            this.point = point;
            this.scores = scores;
        }

        public int getTour() {
            return tour;
        }

        public Point getPoint() {
            return point;
        }

        public int[] getScores() {
            return scores;
        }
    }

    private Terrain terrain;
    private Joueur[] joueurs;
    private int joueurActuel;
    private int tour;
    private final static Logger LOGGER = Logger.getLogger(Terrain.class.getName());

    private ArrayList<Configuration> configurations;

    public Jeu(Terrain terrain, Joueur[] joueurs) {
        this.terrain = terrain;
        this.joueurs = joueurs;

        this.joueurActuel = 0;
        this.tour = 1;

        this.configurations = new ArrayList<>();
    }

    public void changeJoueur() {
        this.changeJoueur(1);
    }

    public void changeJoueur(int modifier) {
        this.joueurActuel = (this.joueurActuel + modifier) % 2;
        if (this.joueurActuel < 0) this.joueurActuel = this.joueurs.length - 1;
    }

    public void jouer(Point p) {
        if (this.terrain.action(getJoueurActuel().action(p), this.tour) == 0) return;

        int[] scores = new int[joueurs.length];
        int i = 0;
        for (Joueur j : joueurs) scores[i++] = j.getScore();

        if (this.tour <= this.configurations.size()) for (i = this.configurations.size(); i >= this.tour; i--) this.configurations.remove(i - 1);
        configurations.add(new Configuration(this.tour++, p, scores));

        this.getJoueurActuel().plusScore(5);
        this.changeJoueur();

        this.setChanged();
        this.notifyObservers();
    }

    public void undo() {
        if (this.tour <= 1) return;

        this.terrain.rollback(this.tour--);
        this.changeJoueur(-1);
        this.setScores(this.configurations.get(this.tour-1).getScores());

        this.setChanged();
        this.notifyObservers();
    }

    public void setScores(int[] scores) {
        for (int i = 0; i < scores.length; i++) {
            this.joueurs[i].setScore(scores[i]);
        }
    }

    public void redo() {
        if (this.tour > this.configurations.size()) return;

        this.terrain.action(this.configurations.get(this.tour-1).getPoint(), this.tour);
        this.changeJoueur();
        this.setScores(this.configurations.get(this.tour++-1).getScores());

        this.setChanged();
        this.notifyObservers();
    }

    public Terrain getTerrain() {
        return terrain;
    }

    public Joueur[] getJoueurs() {
        return joueurs;
    }

    public Joueur getJoueurActuel() {
        return joueurs[joueurActuel];
    }

    public int getTour() {
        return tour;
    }
}
