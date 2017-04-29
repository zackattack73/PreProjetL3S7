package projet.model;

import java.io.*;
import java.util.ArrayList;
import java.util.logging.Logger;

/**
 * Package ${PACKAGE} / Project PreProjetL3S7.
 * Date 2017 04 28.
 * Created by Nico (17:12).
 */
public class Terrain {
    private int[][] cases;
    public int hauteur;
    public int largeur;
    private final static Logger LOGGER = Logger.getLogger(Terrain.class.getName());

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

    public Terrain(BufferedReader br) {
        try {
            String sCurrentLine;
            String parts[];
            int linen = 0;

            LOGGER.info("Load terrain");
            if ((sCurrentLine = br.readLine()) != null) {
                parts = sCurrentLine.split(";");
                this.largeur = Integer.parseInt(parts[0]);
                this.hauteur = Integer.parseInt(parts[1]);
                LOGGER.info("L" + this.largeur + " H" + this.hauteur);
            }

            this.cases = new int[this.hauteur][this.largeur];
            while (linen < this.hauteur && (sCurrentLine = br.readLine()) != null) {
                parts = sCurrentLine.split(";");
                for (int i = 0; i < this.largeur || i < parts.length; i++)
                    this.cases[linen][i] = Integer.parseInt(parts[i]);
                linen++;
            }
            LOGGER.info(this.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getSaveString() {
        StringBuilder sb = new StringBuilder();

        sb.append(this.largeur).append(";").append(this.hauteur).append("\n");
        for (int x = 0; x < hauteur; x++) {
            for (int y = 0; y < largeur; y++) {
                sb.append(Integer.toString(this.cases[x][y]));
                if (y != largeur-1) sb.append(";");
            }
            sb.append("\n");
        }

        return sb.toString();
    }

    // action
    // returns 1 (success) or 0 (fail)
    public int action(Point p, int tour) {
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

        a.append("{").append(this.hauteur).append(",").append(this.largeur).append("}\n");
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

        return pts;
    }

    public void rollback(int tour) {
        for (int i = 0; i < hauteur; i++) {
            for (int j = 0; j < largeur; j++) {
                if (this.cases[i][j] == tour-1)
                    this.cases[i][j] = 0;
            }
        }
    }

    public int[][] getCases() {
        return cases;
    }

    public int getCase(Point p) {
        return this.cases[p.x][p.y];
    }
}
