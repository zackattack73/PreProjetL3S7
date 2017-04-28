package projet;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Pair;
import projet.ctrl.ActionsCtrl;
import projet.ctrl.AffichageCtrl;
import projet.ctrl.GaufreCtrl;
import projet.model.IA;
import projet.model.Joueur;
import projet.model.Terrain;

public class Projet extends Application {
    private Stage primaryStage;
    private BorderPane root;

    // Joueurs
    private Joueur joueurs[];

    // Ctrl
    private AffichageCtrl affichageCtrl;
    private GaufreCtrl gaufreCtrl;
    private ActionsCtrl actionsCtrl;

    // Terrain
    private Terrain terrain;

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
        this.primaryStage = primaryStage;
        root = new BorderPane();
        GridPane gridPane = new GridPane();
        VBox vBox = new VBox();
        HBox hBox = new HBox();

        joueurs = new Joueur[2];

        root.setCenter(gridPane);
        root.setLeft(vBox);
        root.setTop(hBox);

        dialog();
        if (hauteur < HAUTEUR_MIN || largeur < LARGEUR_MIN) Platform.exit();

        Scene scene = new Scene(root, largeur * 80 + 120, hauteur * 35 + 50);
        scene.getStylesheets().add(Projet.class.getResource("Projet.css").toExternalForm());
        primaryStage.setTitle("Projet Gaufre S7 - PreProd");
        primaryStage.setScene(scene);
        primaryStage.show();

        terrain = new Terrain(hauteur, largeur);

        joueurs[0] = new Joueur(terrain, "A");
        if (ia) {
            joueurs[1] = new IA(terrain, "IA", IA.DIFF_MOYEN);
        } else {
            joueurs[1] = new Joueur(terrain, "B");
        }

        this.gaufreCtrl = new GaufreCtrl(gridPane, joueurs, this);
        this.actionsCtrl = new ActionsCtrl(vBox, this);
        this.affichageCtrl = new AffichageCtrl(hBox, this);
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

    public AffichageCtrl getAffichageCtrl() {
        return affichageCtrl;
    }

    public GaufreCtrl getGaufreCtrl() {
        return gaufreCtrl;
    }

    public ActionsCtrl getActionsCtrl() {
        return actionsCtrl;
    }

    public Terrain getTerrain() {
        return terrain;
    }
}
