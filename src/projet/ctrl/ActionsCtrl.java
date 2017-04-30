package projet.ctrl;

import javafx.application.Platform;
import javafx.stage.FileChooser;
import projet.Projet;
import projet.model.Jeu;
import projet.view.ActionsView;

import java.io.File;

/**
 * Package ${PACKAGE} / Project PreProjetL3S7.
 * Date 2017 04 27.
 * Created by Nico (22:32).
 */
public class ActionsCtrl {
    private final Projet projet;
    private final Jeu jeu;
    private final ActionsView actionsView;

    public ActionsCtrl(Projet projet) {
        this.projet = projet;
        this.jeu = projet.getJeu();

        this.actionsView = new ActionsView(this);
    }

    public void clickNewGame() {
        this.projet.newGame();
    }

    public void clickUndo() {
        this.jeu.undo();
    }

    public void clickRedo() {
        this.jeu.redo();
    }

    public void clickSave() {
        String filename;
        final FileChooser fileChooser = new FileChooser();
        fileChooser.setInitialDirectory(new File("."));
        File file = fileChooser.showSaveDialog(this.projet.primaryStage);
        if (file != null) {
            filename = file.getAbsolutePath();
            System.out.println("Save to " + filename);
            this.jeu.save(filename);
        }
    }

    public void clickExit() {
        Platform.exit();
    }

    public ActionsView getActionsView() {
        return this.actionsView;
    }
}
