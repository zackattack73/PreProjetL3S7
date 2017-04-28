/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projet;

import java.util.ArrayList;
import java.util.logging.LogManager;
import java.util.logging.Logger;

/**
 *
 * @author houdebil
 */
public class Moteur {
    private final static Logger LOGGER = Logger.getLogger(Moteur.class.getName());

    private int[][] terrain;
    public int hauteur;
    public int largeur;
    private int tour = 1;

    public Moteur() {
        this(16, 9);
    }

    public Moteur(int hauteur, int largeur) {
        this.hauteur = hauteur;
        this.largeur = largeur;
        this.terrain = new int[hauteur][largeur];
        for (int i = 0; i < hauteur; i++)
            for (int j = 0; j < largeur; j++)
                this.terrain[i][j] = 0;
        LOGGER.info("Moteur initialisé");
    }

    // action
    // returns 1 (success) or 0 (fail)
    public int action(Point p) {
        if (p.x >= hauteur || p.y >= largeur) return 0;
        boolean actionValide = false;
        
        for (int i = p.x; i < hauteur; i++)
            for (int j = p.y; j < largeur; j++) {
                if (this.terrain[i][j] == 0) {
                    this.terrain[i][j] = tour;
                    actionValide = true;
                }
            }
        
        if (actionValide) {
            this.tour++;
            return (partieTerminee() ? 2 : 1);
        } else return 0;
    }
    
    private boolean partieTerminee() {
        return this.terrain[0][0] > 0;
    }

    public int valueAt(Point p) {
        return this.terrain[p.x][p.y];
    }
    
    public String toString() {
        StringBuilder a = new StringBuilder();
        
        for (int i = 0; i < hauteur; i++) {
            for (int j = 0; j < largeur; j++) {
                a.append("[").append(this.terrain[i][j]).append("]");
            }
            a.append("\n");
        }
        
        return a.toString();
    }
    
    public ArrayList<Point> getPointsDispo() {
        ArrayList<Point> pts = new ArrayList<>();
        
        for (int i = 0; i < hauteur; i++) {
            for (int j = 0; j < largeur; j++) {
                if (this.terrain[i][j] == 0)
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
                if (this.terrain[i][j] == this.tour-1)
                    this.terrain[i][j] = 0;
            }
        }

        this.tour--;
        return 1;
    }

    public int[][] getTerrain() {
        return terrain;
    }
    
}
