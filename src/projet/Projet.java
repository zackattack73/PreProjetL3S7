package projet;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Pair;

import java.util.Optional;
import java.util.logging.Logger;

public class Projet extends Application {
    private final static Logger LOGGER = Logger.getLogger(Projet.class.getName());
    private final static int HAUTEUR_MIN = 2;
    private final static int LARGEUR_MIN = 2;
    private int hauteur;
    private int largeur;
    private boolean ia;

    private void dialog() {
        Dialog<Pair<String, String>> dialog = new Dialog<>();
        dialog.setTitle("Menu");
        dialog.setHeaderText("Choisir la taille du terrain");

        ButtonType loginButtonType = new ButtonType("Valider", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(loginButtonType, ButtonType.CANCEL);

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));

        TextField username = new TextField();
        username.setPromptText("Hauteur");
        TextField password = new TextField();
        password.setPromptText("Largeur");
        CheckBox cb = new CheckBox();

        grid.add(new Label("Hauteur:"), 0, 0);
        grid.add(username, 1, 0);
        grid.add(new Label("Largeur:"), 0, 1);
        grid.add(password, 1, 1);
        grid.add(new Label("IA:"), 0, 2);
        grid.add(cb, 1, 2);

        dialog.getDialogPane().setContent(grid);

        Platform.runLater(username::requestFocus);

        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == loginButtonType) {
                try {
                    hauteur = Integer.parseInt(username.getText());
                } catch (NumberFormatException e) {
                    hauteur = 10;
                }

                try {
                    largeur = Integer.parseInt(password.getText());
                } catch (NumberFormatException e) {
                    largeur = 10;
                }

                ia = cb.isSelected();
            }
            return null;
        });

        dialog.showAndWait();
    }

    @Override
    public void start(Stage primaryStage) {
        BorderPane root = new BorderPane();
        GridPane gridPane = new GridPane();
        VBox vBox = new VBox();
        Joueur j1, j2;

        root.setCenter(gridPane);
        root.setLeft(vBox);

        dialog();
        if (hauteur < HAUTEUR_MIN || largeur < LARGEUR_MIN) Platform.exit();

        Scene scene = new Scene(root, largeur * 85, hauteur * 40); // TODO: taille
        primaryStage.setTitle("Projet Gaufre S7 - PreProd");
        primaryStage.setScene(scene);
        primaryStage.show();

        Moteur moteur = new Moteur(hauteur, largeur);

        j1 = new Joueur(moteur, "A");
        if (ia) {
            j2 = new IA(moteur, "IA", IA.DIFF_MOYEN);
        } else {
            j2 = new Joueur(moteur, "B");
        }

        GaufreCtrl c = new GaufreCtrl(gridPane, moteur, j1, j2);
        ActionsCtrl actionsCtrl = new ActionsCtrl(vBox, moteur);
        actionsCtrl.setGaufreCtrl(c);
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
