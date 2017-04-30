package projet.view;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import projet.ctrl.AffichageCtrl;
import projet.model.Jeu;

import java.util.Observable;
import java.util.Observer;

/**
 * Package ${PACKAGE} / Project PreProjetL3S7.
 * Date 2017 05 01.
 * Created by Nico (00:27).
 */
public class AffichageView extends HBox implements Observer {
    private final AffichageCtrl affichageCtrl;
    private final Jeu jeu;

    private final Label score;
    private final Label joueur;
    private final Label tour;

    public AffichageView(AffichageCtrl affichageCtrl) {
        super();

        this.setId("AffichageView");

        this.affichageCtrl = affichageCtrl;
        this.jeu = affichageCtrl.getJeu();
        this.jeu.addObserver(this);

        this.score = new Label("Score: 0");
        this.joueur = new Label("");
        this.tour = new Label("Tour 1");

        Region r1 = new Region();
        Region r2 = new Region();

        HBox.setHgrow(r1, Priority.ALWAYS);
        HBox.setHgrow(r2, Priority.ALWAYS);

        this.setAlignment(Pos.CENTER);
        this.setPadding(new Insets(5, 15, 0, 15));

        getChildren().add(this.score);
        this.getChildren().add(r1);
        this.getChildren().add(this.joueur);
        this.getChildren().add(r2);
        this.getChildren().add(this.tour);

        update(null, null);
    }

    @Override
    public void update(Observable o, Object arg) {
        try {
            this.score.setText("Score: " + this.jeu.getJoueurActuel().getScore());
            this.joueur.setText(this.jeu.getJoueurActuel().getNomFinal());
            this.tour.setText("Tour " + this.jeu.getTour());
        } catch (NullPointerException npe) {
            this.score.setText("Score: 0");
            this.joueur.setText("");
            this.tour.setText("Tour 1");
        }
    }
}
