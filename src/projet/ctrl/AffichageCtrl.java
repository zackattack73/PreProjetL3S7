package projet.ctrl;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import projet.Projet;
import projet.model.Jeu;

import java.util.Observable;
import java.util.Observer;

/**
 * Package ${PACKAGE} / Project PreProjetL3S7.
 * Date 2017 04 28.
 * Created by Nico (14:35).
 */
public class AffichageCtrl implements Observer {
    private HBox hBox;
    private Projet projet;
    private Jeu jeu;

    private Label score;
    private Label joueur;
    private Label tour;

    public AffichageCtrl(HBox hBox, Projet projet) {
        this.hBox = hBox;
        this.projet = projet;
        this.jeu = projet.getJeu();
        jeu.addObserver(this);

        init();
    }

    public void init() {
        this.score = new Label("Score: 0");
        this.joueur = new Label("");
        this.tour = new Label("Tour 1");

        Region r1 = new Region();
        Region r2 = new Region();

        HBox.setHgrow(r1, Priority.ALWAYS);
        HBox.setHgrow(r2, Priority.ALWAYS);

        hBox.setAlignment(Pos.CENTER);
        hBox.setPadding(new Insets(5, 15, 0, 15));

        hBox.getChildren().add(this.score);
        hBox.getChildren().add(r1);
        hBox.getChildren().add(this.joueur);
        hBox.getChildren().add(r2);
        hBox.getChildren().add(this.tour);

        update(null, null);
    }

    public void update(Observable obs, Object obj) {
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
