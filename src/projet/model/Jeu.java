package projet.model;

import java.io.*;
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

        public Configuration(BufferedReader br) {
            load(br);
        }

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

        public String toString() {
            return "Tour " + this.tour + ", Point " + this.point + ", Scores " + Arrays.toString(this.scores);
        }

        public String getSaveString() {
            StringBuilder sb = new StringBuilder();

            sb.append(this.tour).append(";").append(this.point.getSaveString()).append("\n"); // "tour;point" avec point "x,y"
            for (int i = 0; i < scores.length; i++) { // "scoreJ1:scoreJ2:....."
                sb.append(scores[i]);
                if (i < scores.length-1)
                    sb.append(":");
            }

            return sb.append("\n").toString();
        }

        public void load(BufferedReader br) {
            try {
                String sCurrentLine;
                String parts[], partsPoint[];

                if ((sCurrentLine = br.readLine()) != null) {
                    parts = sCurrentLine.split(";");
                    this.tour = Integer.parseInt(parts[0]);
                    partsPoint = parts[1].split(",");
                    this.point = new Point(Integer.parseInt(partsPoint[0]), Integer.parseInt(partsPoint[1]));
                } else {
                    throw new IllegalStateException();
                }

                if ((sCurrentLine = br.readLine()) != null) {
                    parts = sCurrentLine.split(":");
                    this.scores = new int[parts.length];
                    for (int i = 0; i < parts.length; i++) this.scores[i] = Integer.parseInt(parts[i]);
                } else {
                    throw new IllegalStateException();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private Terrain terrain;
    private ArrayList<Joueur> joueurs = new ArrayList<>();
    private int joueurActuel;
    private int tour;
    private ArrayList<String> nomDispo;
    private final static String nomDispoPath = "nomsDispo.txt";
    private final static Logger LOGGER = Logger.getLogger(Terrain.class.getName());

    private ArrayList<Configuration> configurations;

    public Jeu(int hauteur, int largeur) {
        this.loadNomsDisponibles();

        this.terrain = new Terrain(hauteur, largeur);

        this.joueurActuel = 0;
        this.tour = 1;

        this.configurations = new ArrayList<>();
    }

    public Jeu(int hauteur, int largeur, Joueur[] joueurs) {
        this.loadNomsDisponibles();

        for (Joueur j : joueurs) j.setJeu(this);

        this.terrain = new Terrain(hauteur, largeur);
        this.joueurs.addAll(Arrays.asList(joueurs));

        this.joueurActuel = 0;
        this.tour = 1;

        this.configurations = new ArrayList<>();
    }

    public Jeu(String path) {
        load(path);
    }

    public void addJoueur(Joueur j) {
        this.joueurs.add(j);
        if (j.jeu == null) j.setJeu(this);
    }

    private void loadNomsDisponibles() {
        this.nomDispo = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(nomDispoPath))) {
            String sCurrentLine;

            while ((sCurrentLine = br.readLine()) != null) {
                nomDispo.add(sCurrentLine);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void changeJoueur() {
        this.changeJoueur(1);
    }

    public void changeJoueur(int modifier) {
        this.joueurActuel = (this.joueurActuel + modifier) % 2;
        if (this.joueurActuel < 0) this.joueurActuel = this.joueurs.size() - 1;
    }

    public void save(String path) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(path))) {
            bw.write(this.tour + "," + this.joueurs.size() + "\n"); // write "tour,nbJoueurs"
            for (Joueur j : this.joueurs)
                bw.write(j.getNom() + ":" + j.getScore() + ":" + ((j instanceof IA) ? ((IA)j).getDifficulte() : 0) + "\n"); // "nomJ:scoreJ:iaDiff"

            bw.write(this.terrain.getSaveString());

            for (Configuration c : this.configurations)
                bw.write(c.getSaveString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void load(String path) {
        try (BufferedReader br = new BufferedReader(new FileReader(path))) {
            String sCurrentLine;
            String parts[];
            int linen = 0, nbJ = 0;

            if ((sCurrentLine = br.readLine()) != null) { // load "tour,nbJoueurs"
                parts = sCurrentLine.split(",");
                this.tour = Integer.parseInt(parts[0]);
                this.joueurs = new ArrayList<>();
                nbJ = Integer.parseInt(parts[1]);
            }

            while (linen < nbJ && (sCurrentLine = br.readLine()) != null) {
                parts = sCurrentLine.split(":");
                Joueur j;
                int difficulte = Integer.parseInt(parts[2]);
                if (difficulte == 0)
                    j = new Joueur(this, parts[0]);
                else
                    j = new IA(this, parts[0], difficulte);

                j.setScore(Integer.parseInt(parts[1]));
                this.joueurs.add(j);
                linen++;
            }

            this.terrain = new Terrain(br);
            this.joueurActuel = (this.tour - 1) % this.joueurs.size();

            Configuration c;
            this.configurations = new ArrayList<>();
            try {
                while (true) {
                    c = new Configuration(br);
                    this.configurations.add(c);
                }
            } catch (IllegalStateException ise) {}
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void jouer(Point p) {
        if (this.terrain.action(getJoueurActuel().action(p), this.tour) == 0) return;

        if (!this.partieTerminee()) {
            int[] scores = new int[joueurs.size()];
            int i = 0;
            for (Joueur j : joueurs) scores[i++] = j.getScore();

            if (this.tour <= this.configurations.size()) for (i = this.configurations.size(); i >= this.tour; i--) this.configurations.remove(i - 1);
            configurations.add(new Configuration(this.tour++, p, scores));

            this.getJoueurActuel().plusScore(5);
            this.changeJoueur();
        }

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
            this.joueurs.get(i).setScore(scores[i]);
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

    public ArrayList<Joueur> getJoueurs() {
        return joueurs;
    }

    public Joueur getJoueurActuel() {
        return joueurs.get(joueurActuel);
    }

    public int getTour() {
        return tour;
    }

    public ArrayList<String> getNomDispo() {
        return this.nomDispo;
    }

    public boolean partieTerminee() {
        return this.terrain.partieTerminee();
    }
}
