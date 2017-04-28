package projet.ctrl;

import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import projet.Projet;

/**
 * Package ${PACKAGE} / Project PreProjetL3S7.
 * Date 2017 04 27.
 * Created by Nico (22:32).
 */
public class ActionsCtrl {
    private Projet projet;
    private VBox vBox;

    public ActionsCtrl(VBox vBox, Projet projet) {
        this.projet = projet;
        this.vBox = vBox;

        init();
    }

    private void init() {
        this.vBox.setAlignment(Pos.CENTER);
        this.vBox.setPadding(new Insets(15));
        this.vBox.setSpacing(5);

        Button rollback = new Button("Rollback");
        rollback.setMaxWidth(Double.MAX_VALUE);
        rollback.setOnAction(event -> {
            projet.getGaufreCtrl().rollback();
            projet.getTerrain().rollback();
        });
        this.vBox.getChildren().add(rollback);

        Button exit = new Button("Exit");
        exit.setMaxWidth(Double.MAX_VALUE);
        exit.setOnAction(event -> Platform.exit());
        this.vBox.getChildren().add(exit);
    }
}
