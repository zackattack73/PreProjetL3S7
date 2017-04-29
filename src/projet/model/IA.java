/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projet.model;

import java.util.ArrayList;
import java.util.Random;

public class IA extends Joueur {
    public final static int DIFF_FACILE = 0;
    public final static int DIFF_MOYEN = 1;
    public final static int DIFF_DIFFICILE = 2;
    private int difficulte;
    private Random random;

    public IA(Terrain terrain, String nom, int difficulte) {
        super(terrain, nom);
        this.difficulte = difficulte;
        this.random = new Random();
    }

    public Point action(ArrayList<Point> pts) {
        switch (difficulte) {
            case DIFF_FACILE:
                return actionFacile(pts);
            case DIFF_MOYEN:
                return actionMoyen(pts);
            case DIFF_DIFFICILE:
                return actionDifficulte(pts);
            default:
                return null;
        }
    }

    private Point actionFacile(ArrayList<Point> pts) {
        if (pts.isEmpty()) return null;
        return pts.get(random.nextInt(pts.size()));
    }

    private Point actionMoyen(ArrayList<Point> pts) {
        if (pts.isEmpty()) return null;

        if (pts.size() == 1) {
            return pts.get(0);
        } else {
            if (terrain.valueAt(new Point(1, 0)) > 0) {
                return new Point(0, 1);
            } else if (terrain.valueAt(new Point(0, 1)) > 0) {
                return new Point(1, 0);
            } else {
                pts.remove(0);
                return pts.get(random.nextInt(pts.size()));
            }
        }
    }

    private Point actionDifficulte(ArrayList<Point> pts) {
        if (pts.isEmpty()) return null;
        return null;
    }
}
