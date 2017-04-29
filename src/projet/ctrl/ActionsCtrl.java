package projet.ctrl;

import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import projet.Projet;
import projet.model.Jeu;

/**
 * Package ${PACKAGE} / Project PreProjetL3S7.
 * Date 2017 04 27.
 * Created by Nico (22:32).
 */
public class ActionsCtrl {
    private Projet projet;
    private Jeu jeu;
    private VBox vBox;

    public ActionsCtrl(VBox vBox, Projet projet) {
        this.projet = projet;
        this.vBox = vBox;
        this.jeu = projet.getJeu();

        init();
    }

    private void init() {
        this.vBox.setAlignment(Pos.CENTER);
        this.vBox.setPadding(new Insets(15));
        this.vBox.setSpacing(5);

        Button undo = new Button("Undo");
        undo.setMaxWidth(Double.MAX_VALUE);
        undo.setOnAction(event -> this.jeu.undo());
        this.vBox.getChildren().add(undo);

        Button redo = new Button("Redo");
        redo.setMaxWidth(Double.MAX_VALUE);
        redo.setOnAction(event -> this.jeu.redo());
        this.vBox.getChildren().add(redo);

        Button exit = new Button("Exit");
        exit.setMaxWidth(Double.MAX_VALUE);
        exit.setOnAction(event -> Platform.exit());
        this.vBox.getChildren().add(exit);
    }
}
