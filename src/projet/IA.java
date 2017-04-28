/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projet;

import java.util.ArrayList;
import java.util.Random;

/**
 *
 * @author houdebil
 */
public class IA extends Joueur {
    public final static int DIFF_FACILE = 0;
    public final static int DIFF_MOYEN = 1;
    public final static int DIFF_DIFFICILE = 2;
    int difficulte;

    public IA(Moteur m, String nom, int difficulte) {
        super(m, nom);
        this.difficulte = difficulte;
    }

    public int action(ArrayList<Point> pts) {
        switch (difficulte) {
            case DIFF_FACILE:
                return actionFacile(pts);
            case DIFF_MOYEN:
                return actionMoyen(pts);
            case DIFF_DIFFICILE:
                return actionDifficulte(pts);
            default:
                return 0;
        }
    }

    public int actionFacile(ArrayList<Point> pts) {
        if (pts.isEmpty()) return 0;
        Random r = new Random();
        Point p = pts.get(r.nextInt(pts.size()));

        return action(p);
    }

    public int actionMoyen(ArrayList<Point> pts) {
        if (pts.isEmpty()) return 0;
        Random r = new Random();

        if (pts.size() == 1) {
            return action(pts.get(0));
        } else {
            if (moteur.valueAt(new Point(1, 0)) > 0) {
                return action(new Point(0, 1));
            } else if (moteur.valueAt(new Point(0, 1)) > 0) {
                return action(new Point(1, 0));
            } else {
                pts.remove(0);
                return action(pts.get(r.nextInt(pts.size())));
            }
        }
    }

    public int actionDifficulte(ArrayList<Point> pts) {
        if (pts.isEmpty()) return 0;
        Random r = new Random();


        return action(pts);
    }
}
