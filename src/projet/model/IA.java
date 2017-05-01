package projet.model;

import java.util.ArrayList;
import java.util.Random;

public class IA extends Joueur {
    public final static int DIFF_FACILE = 1;
    public final static int DIFF_MOYEN = 2;
    public final static int DIFF_DIFFICILE = 3;
    public final static int TEMPS_LATENCE_BASE = 2000;
    public final static int TEMPS_LATENCE_VAR = 500;
    private int difficulte;
    private Random random;

    public IA(String nom, int difficulte) {
        this(null, nom, difficulte);
    }

    public IA(Jeu jeu, String nom, int difficulte) {
        super(jeu, nom);
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
            if (getJeu().getTerrain().valueAt(new Point(1, 0)) > 0) {
                return new Point(0, 1);
            } else if (getJeu().getTerrain().valueAt(new Point(0, 1)) > 0) {
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

    public int getDifficulte() {
        return difficulte;
    }

    // gets name with attributes (not to write on file!)
    public String getNomFinal() {
        return super.getNomFinal() + " (IA)";
    }
}
