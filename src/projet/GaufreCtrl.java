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
    
    public GaufreCtrl(GridPane root, Moteur moteur) {
        this.moteur = moteur;
        this.gridPane = root;
        this.ia = new IA();
        this.pb = new PButton[moteur.HAUTEUR][moteur.LARGEUR];
        
        init();
    }
    
    private void init() {
        gridPane.setAlignment(Pos.CENTER);
        gridPane.setPadding(new Insets(0));
        gridPane.setHgap(2);
        gridPane.setVgap(2);

        for (int x = 0; x < moteur.HAUTEUR; x++) {
            for (int y = 0; y < moteur.LARGEUR; y++) {
                Point p = new Point(x, y);
                PButton pb = new PButton(p.toString(), p);
                pb.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
                pb.setOnAction(event -> {
                    PButton button = (PButton)event.getSource();

                    actionJeu(button.p);
                    actionJeu(ia.jouer(moteur.getPointsDispo()));
                });
                this.pb[x][y] = pb;
                gridPane.add(pb, y, x);
            }
        }
    }
    
    public void actionJeu(Point p) {
        System.out.println("Action: " + p);
        switch (moteur.action(p)) {
            case 1:
                updateButtons();
                break;
            case 2:
                System.out.println("VICTORY!!!!");
                updateButtons();
                break;
        }
        //System.out.print(m);
    }
    
    public void updateButtons() {
        for (int x = 0; x < moteur.HAUTEUR; x++) {
            for (int y = 0; y < moteur.LARGEUR; y++) {
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
