package projet.ctrl;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import projet.Projet;
import projet.model.IA;
import projet.model.Joueur;
import projet.model.Point;
import projet.model.Terrain;
import projet.obs.Observer;

class PButton extends Button {
    Point p;

    public PButton(String nom, Point p) {
        super(nom);
        this.p = p;
    }
}

public class GaufreCtrl implements Observer {
    private Projet projet;
    private Terrain terrain;

    private GridPane gridPane;
    private PButton[][] pb;

    private Joueur[] joueurs;
    private int joueurActuel;
    
    public GaufreCtrl(GridPane root, Joueur[] joueurs, Projet projet) {
        this.gridPane = root;
        this.joueurs = joueurs;
        this.projet = projet;
        this.terrain = projet.getTerrain();
        this.projet.getTerrain().addObserver(this);

        this.pb = new PButton[terrain.hauteur][terrain.largeur];

        joueurActuel = 0;

        init();
    }
    
    private void init() {
        gridPane.setAlignment(Pos.CENTER);
        //gridPane.setPadding(new Insets(0));
        gridPane.setHgap(2);
        gridPane.setVgap(2);

        for (int x = 0; x < terrain.hauteur; x++) {
            for (int y = 0; y < terrain.largeur; y++) {
                Point p = new Point(x, y);
                PButton pb = new PButton(p.toString(), p);
                pb.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
                pb.setOnAction(event -> {
                    PButton button = (PButton)event.getSource();

                    if (getJoueurActuel().action(button.p) == 1) this.getJoueurActuel().plusScore(5);
                    joueurActuel = (joueurActuel + 1) % 2;

                    if (getJoueurActuel() instanceof IA) {
                        if (((IA)getJoueurActuel()).action(terrain.getPointsDispo()) == 1) this.getJoueurActuel().plusScore(5);
                        joueurActuel = (joueurActuel + 1) % 2;
                    }

                    // TODO: fix update problem
                    projet.getTerrain().notifyObserver();
                });
                this.pb[x][y] = pb;
                gridPane.add(pb, y, x);
            }
        }
    }

    public void update() {
        for (int x = 0; x < terrain.hauteur; x++) {
            for (int y = 0; y < terrain.largeur; y++) {
                if (this.terrain.getCase(new Point(x, y)) > 0) {
                    if (!this.pb[x][y].isDisabled()) {
                        this.pb[x][y].setDisable(true);
                    }
                } else {
                    if (this.pb[x][y].isDisabled()) {
                        this.pb[x][y].setDisable(false);
                    }
                }
            }
        }
    }

    public Joueur getJoueurActuel() {
        return this.joueurs[joueurActuel];
    }

    public Joueur getJoueurSuivant() {
        return this.joueurs[(joueurActuel + 1) % 2];
    }

    public void rollback() {
        if (this.terrain.getTour() > 1)
            this.joueurActuel = (this.joueurActuel == 0 ? this.joueurs.length - 1 : this.joueurActuel - 1);
    }
}
