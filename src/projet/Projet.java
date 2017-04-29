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
import projet.model.Jeu;
import projet.model.Joueur;
import projet.model.Terrain;

public class Projet extends Application {
    private Stage primaryStage;
    private BorderPane root;

    // Joueurs
    private Joueur joueurs[];

    // Jeu
    private Jeu jeu;

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
        Dialog<String> dialog = new Dialog<>();
        dialog.setTitle("Accueil");
        dialog.setHeaderText("Choisir la taille du terrain");

        dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));

        TextField largeur = new TextField();
        largeur.setPromptText("Largeur");
        TextField hauteur = new TextField();
        hauteur.setPromptText("Hauteur");
        CheckBox cb = new CheckBox();

        grid.add(new Label("Largeur:"), 0, 0);
        grid.add(largeur, 1, 0);
        grid.add(new Label("Hauteur:"), 0, 1);
        grid.add(hauteur, 1, 1);
        grid.add(new Label("IA:"), 0, 2);
        grid.add(cb, 1, 2);

        dialog.getDialogPane().setContent(grid);

        //Platform.runLater(largeur::requestFocus);

        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == ButtonType.OK) {
                try {
                    this.hauteur = Integer.parseInt(hauteur.getText());
                } catch (NumberFormatException e) {
                    this.hauteur = 10;
                }

                try {
                    this.largeur = Integer.parseInt(largeur.getText());
                } catch (NumberFormatException e) {
                    this.largeur = 10;
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
            System.out.println("IA Créée");
            joueurs[1] = new IA(terrain, "IA", IA.DIFF_MOYEN);
        } else {
            joueurs[1] = new Joueur(terrain, "B");
        }

        jeu = new Jeu(terrain, joueurs);
        this.gaufreCtrl = new GaufreCtrl(gridPane, this);
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

    public Jeu getJeu() {
        return jeu;
    }
}
