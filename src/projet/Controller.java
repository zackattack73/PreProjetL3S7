/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projet;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;

class PButton extends Button {
    Point p;

    public PButton(String nom, Point p) {
        super(nom);
        this.p = p;
    }
}

/**
 *
 * @author houdebil
 */
public class Controller {
    private Moteur m;
    private Pane root;
    private PButton[][] pb;
    private IA ia;
    
    public Controller(Pane root) {
        m = new Moteur();
        this.root = root;
        this.ia = new IA();
        this.pb = new PButton[m.HAUTEUR][m.LARGEUR];
        
        init();
    }
    
    private void init() {
        GridPane gp = new GridPane();
        this.root.getChildren().add(gp);
        
        for (int x = 0; x < m.HAUTEUR; x++) {
            for (int y = 0; y < m.LARGEUR; y++) {
                Point p = new Point(x, y);
                this.pb[x][y] = new PButton(p.toString(), p);
                this.pb[x][y].setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        PButton button = (PButton)event.getSource();
                        //System.out.println(button.p);
                        
                        actionJeu(button.p);
                        actionJeu(ia.jouer(m.getPointsDispo()));
                    }
                });
                gp.add(this.pb[x][y], y, x);
            }
        }
    }
    
    public void actionJeu(Point p) {
        System.out.println("Action: " + p);
        switch (m.action(p)) {
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
    
    private void updateButtons() {
        for (int x = 0; x < m.HAUTEUR; x++) {
            for (int y = 0; y < m.LARGEUR; y++) {
                if (this.m.getTerrain()[x][y] > 0 && !this.pb[x][y].isDisabled()) {
                    this.pb[x][y].setDisable(true);
                }
            }
        }
    }
    
}
