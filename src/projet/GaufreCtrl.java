/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projet;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;

class PButton extends Button {
    Point p;

    public PButton(String nom, Point p) {
        super(nom);
        this.p = p;
    }
}

public class GaufreCtrl {
    private Moteur moteur;
    private GridPane gridPane;
    private PButton[][] pb;
    private IA ia;
    private Joueur[] joueurs;
    private int joueurActuel;
    
    public GaufreCtrl(GridPane root, Moteur moteur, Joueur j1, Joueur j2) {
        this.moteur = moteur;
        this.gridPane = root;
        this.pb = new PButton[moteur.hauteur][moteur.largeur];
        joueurs = new Joueur[2];
        joueurs[0] = j1;
        joueurs[1] = j2;

        joueurActuel = 0;

        init();
    }
    
    private void init() {
        gridPane.setAlignment(Pos.CENTER);
        gridPane.setPadding(new Insets(0));
        gridPane.setHgap(2);
        gridPane.setVgap(2);

        for (int x = 0; x < moteur.hauteur; x++) {
            for (int y = 0; y < moteur.largeur; y++) {
                Point p = new Point(x, y);
                PButton pb = new PButton(p.toString(), p);
                pb.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
                pb.setOnAction(event -> {
                    PButton button = (PButton)event.getSource();

                    if (joueurs[joueurActuel].action(button.p) == 1) updateButtons();
                    joueurActuel = (joueurActuel + 1) % 2;

                    if (joueurs[joueurActuel] instanceof IA) {
                        if (((IA)joueurs[joueurActuel]).action(moteur.getPointsDispo()) == 1) updateButtons();
                        joueurActuel = (joueurActuel + 1) % 2;
                    }


                });
                this.pb[x][y] = pb;
                gridPane.add(pb, y, x);
            }
        }
    }

    public void updateButtons() {
        for (int x = 0; x < moteur.hauteur; x++) {
            for (int y = 0; y < moteur.largeur; y++) {
                if (this.moteur.getTerrain()[x][y] > 0) {
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
    
}
