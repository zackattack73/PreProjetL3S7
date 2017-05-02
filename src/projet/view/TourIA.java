package projet.view;

import javafx.animation.FadeTransition;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.util.Duration;
import projet.Projet;

public class TourIA extends BorderPane {
    private Projet projet;

    private Label texte;
    private final static int FADE_DURATION = 250;

    private FadeTransition fadeIn = new FadeTransition(new Duration(FADE_DURATION));

    public TourIA(Projet projet) {
        super();

        this.projet = projet;
        this.init();
    }

    private void init() {
        this.initTransition();

        this.texte = new Label("Tour de l'IA");
        BorderPane.setAlignment(this.texte, Pos.CENTER);

        this.getStyleClass().add("TourIA");
        this.setBottom(this.texte);
    }

    private void initTransition() {
        fadeIn.setNode(this);
        fadeIn.setFromValue(0.0);
        fadeIn.setToValue(1.0);
        fadeIn.setCycleCount(1);
        fadeIn.setAutoReverse(false);
    }

    public void show() { // Fade in
        this.projet.root.getChildren().add(this);
        fadeIn.playFromStart();
    }

    public void hide() { // Fade out based on timeline, because fadeIn method only works if node if present
        Timeline timeline = new Timeline();
        KeyFrame key = new KeyFrame(new Duration(FADE_DURATION * 2),
                            new KeyValue(this.opacityProperty(), 0));
        timeline.getKeyFrames().add(key);
        timeline.setOnFinished((ae) -> this.projet.root.getChildren().remove(this));
        timeline.play();
    }
}
