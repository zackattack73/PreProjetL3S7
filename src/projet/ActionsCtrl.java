package projet;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;

/**
 * Package ${PACKAGE} / Project PreProjetL3S7.
 * Date 2017 04 27.
 * Created by Nico (22:32).
 */
public class ActionsCtrl {
    Moteur moteur;
    VBox vBox;

    GaufreCtrl gaufreCtrl;

    public ActionsCtrl(VBox vBox, Moteur moteur) {
        this.moteur = moteur;
        this.vBox = vBox;

        init();
    }

    public void setGaufreCtrl(GaufreCtrl gaufreCtrl) {
        this.gaufreCtrl = gaufreCtrl;
    }

    private void init() {
        this.vBox.setAlignment(Pos.CENTER);

        Button rollback = new Button("Rollback");
        rollback.setOnAction(event -> {
            if (moteur.rollback() == 1) gaufreCtrl.updateButtons();
        });
        this.vBox.getChildren().add(rollback);
    }
}
