package projet.view;

import javafx.geometry.Pos;
import javafx.scene.layout.GridPane;
import projet.ctrl.GaufreCtrl;
import projet.model.Jeu;
import projet.model.Point;
import projet.model.Terrain;

import java.util.Observable;
import java.util.Observer;

public class GaufreView extends GridPane implements Observer {
    // Model
    private Jeu jeu;
    private Terrain terrain;

    // Controls
    private PointButton[][] pointButton;

    // Controller
    private GaufreCtrl gaufreCtrl;

    public GaufreView(GaufreCtrl gaufreCtrl) {
        super();

        this.gaufreCtrl = gaufreCtrl;
        this.jeu = gaufreCtrl.getJeu();
        this.terrain = this.jeu.getTerrain();
        this.jeu.addObserver(this);

        this.pointButton = new PointButton[this.terrain.hauteur][this.terrain.largeur];

        this.setAlignment(Pos.CENTER);
        this.setHgap(2);
        this.setVgap(2);

        for (int x = 0; x < this.terrain.hauteur; x++) {
            for (int y = 0; y < this.terrain.largeur; y++) {
                Point p = new Point(x, y);
                PointButton pb = new PointButton(p.toString(), p);
                pb.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
                pb.setOnAction(event -> {
                    PointButton button = (PointButton)event.getSource();

                    gaufreCtrl.traiterClick(button);
                });

                this.pointButton[x][y] = pb;
                this.add(pb, y, x);
            }
        }

        this.update(null, null);
    }

    @Override
    public void update(Observable o, Object arg) {
        if (this.jeu.partieTerminee()) {
            System.out.println("Partie terminée!");
            System.out.println("Perdant: " + this.jeu.getJoueurActuel().getNom());
            this.gaufreCtrl.partieTerminee();
        }

        for (int x = 0; x < this.terrain.hauteur; x++) {
            for (int y = 0; y < this.terrain.largeur; y++) {
                if (this.terrain.getCase(new Point(x, y)) > 0) {
                    if (!this.pointButton[x][y].isDisabled()) {
                        this.pointButton[x][y].setDisable(true);
                    }
                } else {
                    if (this.pointButton[x][y].isDisabled()) {
                        this.pointButton[x][y].setDisable(false);
                    }
                }
            }
        }
    }
}
