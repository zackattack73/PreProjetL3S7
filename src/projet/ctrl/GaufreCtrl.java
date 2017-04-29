package projet.ctrl;

import javafx.application.Platform;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import projet.Projet;
import projet.model.*;

import java.util.Observable;
import java.util.Observer;

public class GaufreCtrl implements Observer {
    class PButton extends Button {
        Point p;

        public PButton(String nom, Point p) {
            super(nom);
            this.p = p;
        }
    }

    private class SleeperService extends Service<Void> {
        @Override
        protected Task<Void> createTask() {
            return new Task<Void>() {
                @Override
                protected Void call() throws Exception {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) { }
                    return null;
                }
            };
        }

        @Override
        protected void succeeded() {
            jeu.jouer(((IA)jeu.getJoueurActuel()).action(terrain.getPointsDispo()));
        }
    }
    private SleeperService sleeperService = new SleeperService();

    private Projet projet;
    private Terrain terrain;

    private GridPane gridPane;
    private PButton[][] pb;

    private Jeu jeu;
    
    public GaufreCtrl(GridPane root, Projet projet) {
        this.gridPane = root;
        this.jeu = projet.getJeu();
        this.projet = projet;
        this.terrain = projet.getTerrain();
        jeu.addObserver(this);

        this.pb = new PButton[terrain.hauteur][terrain.largeur];

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

                    jeu.jouer(button.p);


                    if (jeu.getJoueurActuel() instanceof IA)
                        sleeperService.restart();
                });

                this.pb[x][y] = pb;
                gridPane.add(pb, y, x);
            }
        }

        update(null, null);
    }

    public void update(Observable obs, Object obj) {
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
}
