package projet.view;

import javafx.application.Platform;
import javafx.geometry.*;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import projet.ctrl.ActionsCtrl;

import java.io.File;

/**
 * Package ${PACKAGE} / Project PreProjetL3S7.
 * Date 2017 05 01.
 * Created by Nico (00:35).
 */
public class ActionsView extends VBox {
    private final ActionsCtrl actionsCtrl;

    private final Button newGame;
    private final Button undo;
    private final Button redo;
    private final Button save;
    private final Button exit;

    public ActionsView(ActionsCtrl actionsCtrl) {
        super();

        this.setId("ActionsView");

        this.actionsCtrl = actionsCtrl;

        this.setAlignment(Pos.CENTER);
        this.setPadding(new javafx.geometry.Insets(15));
        this.setSpacing(5);

        this.newGame = new Button("New game");
        this.newGame.setMaxWidth(Double.MAX_VALUE);
        this.newGame.setOnAction(event -> this.actionsCtrl.clickNewGame());
        this.getChildren().add(newGame);

        this.undo = new Button("Undo");
        undo.setMaxWidth(Double.MAX_VALUE);
        undo.setOnAction(event -> this.actionsCtrl.clickUndo());
        this.getChildren().add(undo);

        this.redo = new Button("Redo");
        this.redo.setMaxWidth(Double.MAX_VALUE);
        this.redo.setOnAction(event -> this.actionsCtrl.clickRedo());
        this.getChildren().add(redo);

        this.save = new Button("Save");
        this.save.setMaxWidth(Double.MAX_VALUE);
        this.save.setOnAction(event -> this.actionsCtrl.clickSave());
        this.getChildren().add(save);

        this.exit = new Button("Exit");
        this.exit.setMaxWidth(Double.MAX_VALUE);
        this.exit.setOnAction(event -> this.actionsCtrl.clickExit());
        this.getChildren().add(exit);
    }
}
