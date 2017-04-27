/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projet;

import java.util.ArrayList;
import java.util.logging.Logger;

/**
 *
 * @author houdebil
 */
public class Moteur {
    private int[][] terrain;
    public final int HAUTEUR = 9;
    public final int LARGEUR = 16;
    private Logger logger;
    private int tour = 1;

    public Moteur() {
        this.terrain = new int[HAUTEUR][LARGEUR];
        for (int i = 0; i < HAUTEUR; i++)
            for (int j = 0; j < LARGEUR; j++)
                this.terrain[i][j] = 0;
        //logger = new Logger("a", this);
    }
    
    
    // action
    // returns 1 (success) or 0 (fail)
    public int action(Point p) {
        if (p.x >= HAUTEUR || p.y >= LARGEUR) return 0;
        boolean actionValide = false;
        
        for (int i = p.x; i < HAUTEUR; i++)
            for (int j = p.y; j < LARGEUR; j++) {
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
    
    public String toString() {
        String a = "";
        
        for (int i = 0; i < HAUTEUR; i++) {
            for (int j = 0; j < LARGEUR; j++) {
                a += "[" + this.terrain[i][j] + "]";
            }
            a += "\n";
        }
        
        return a;
    }
    
    public ArrayList<Point> getPointsDispo() {
        ArrayList<Point> pts = new ArrayList<>();
        
        for (int i = 0; i < HAUTEUR; i++) {
            for (int j = 0; j < LARGEUR; j++) {
                if (this.terrain[i][j] == 0)
                    pts.add(new Point(i, j));
            }
        }
        
        return pts;
    }
    
    public int rollback() {
        for (int i = 0; i < HAUTEUR; i++)
            for (int j = 0; j < LARGEUR; j++) {
                if (this.terrain[i][j] == this.tour)
                    this.terrain[i][j] = 0;
            }
                
        return 1;
    }

    public int[][] getTerrain() {
        return terrain;
    }
    
}
