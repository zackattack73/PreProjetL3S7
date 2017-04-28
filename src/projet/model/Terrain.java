package projet.model;

import java.util.ArrayList;

/**
 * Package ${PACKAGE} / Project PreProjetL3S7.
 * Date 2017 04 28.
 * Created by Nico (17:12).
 */
public class Terrain extends Moteur {
    private int[][] cases;
    public int hauteur;
    public int largeur;
    public int tour = 1;

    public Terrain() {
        this(16, 9);
    }

    public Terrain(int hauteur, int largeur) {
        this.hauteur = hauteur;
        this.largeur = largeur;
        this.cases = new int[hauteur][largeur];
        for (int i = 0; i < hauteur; i++)
            for (int j = 0; j < largeur; j++)
                this.cases[i][j] = 0;
    }

    public Terrain(String path) {
        // TODO: load a terrain from a filename path
    }

    public void saveTerrain(String path) {
        // TODO: save the terrain to the file at path
    }

    public int getTour() {
        return tour;
    }

    // action
    // returns 1 (success) or 0 (fail)
    public int action(Point p) {
        if (p.x >= hauteur || p.y >= largeur) return 0;
        boolean actionValide = false;

        for (int i = p.x; i < hauteur; i++)
            for (int j = p.y; j < largeur; j++) {
                if (this.cases[i][j] == 0) {
                    this.cases[i][j] = tour;
                    actionValide = true;
                }
            }

        if (actionValide) {
            this.tour++;
            this.notifyObserver();
            return (partieTerminee() ? 2 : 1);
        } else return 0;
    }

    private boolean partieTerminee() {
        return this.cases[0][0] > 0;
    }

    public int valueAt(Point p) {
        return this.cases[p.x][p.y];
    }

    public String toString() {
        StringBuilder a = new StringBuilder();

        for (int i = 0; i < hauteur; i++) {
            for (int j = 0; j < largeur; j++) {
                a.append("[").append(this.cases[i][j]).append("]");
            }
            a.append("\n");
        }

        return a.toString();
    }

    public ArrayList<Point> getPointsDispo() {
        ArrayList<Point> pts = new ArrayList<>();

        for (int i = 0; i < hauteur; i++) {
            for (int j = 0; j < largeur; j++) {
                if (this.cases[i][j] == 0)
                    pts.add(new Point(i, j));
            }
        }
        System.out.println(pts);

        return pts;
    }

    public int rollback() {
        if (tour <= 1) return 0;

        for (int i = 0; i < hauteur; i++) {
            for (int j = 0; j < largeur; j++) {
                if (this.cases[i][j] == this.tour-1)
                    this.cases[i][j] = 0;
            }
        }

        this.tour--;
        this.notifyObserver();
        return 1;
    }

    public int[][] getCases() {
        return cases;
    }

    public int getCase(Point p) {
        return this.cases[p.x][p.y];
    }
}