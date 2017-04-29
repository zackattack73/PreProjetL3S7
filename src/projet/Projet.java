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
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import projet.ctrl.ActionsCtrl;
import projet.ctrl.AffichageCtrl;
import projet.ctrl.GaufreCtrl;
import projet.model.IA;
import projet.model.Jeu;
import projet.model.Joueur;
import projet.model.Terrain;

import java.io.*;

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

    private final static int HAUTEUR_MIN = 2;
    private final static int LARGEUR_MIN = 2;

    private int hauteur;
    private int largeur;
    private String nomJoueur1;
    private String nomJoueur2;
    private boolean j2ia;
    private boolean openFromFile;
    private String terrainFilename;

    private void dialog() {
        Dialog<String> dialog = new Dialog<>();
        final FileChooser fileChooser = new FileChooser();
        fileChooser.setInitialDirectory(new File("."));

        dialog.setTitle("Accueil");
        dialog.setHeaderText("Changer les paramètres");

        dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));

        Button fromFilename = new Button("Choisir un fichier de sauvegarde");
        fromFilename.setOnAction(event -> {
            File file = fileChooser.showOpenDialog(dialog.getOwner());
            if (file != null) {
                terrainFilename = file.getAbsolutePath();
                openFromFile = true;
                System.out.println("Open from " + terrainFilename);
            }
        });
        fromFilename.setMaxWidth(Double.MAX_VALUE);

        TextField largeur = new TextField();
        largeur.setPromptText("Largeur");

        TextField hauteur = new TextField();
        hauteur.setPromptText("Hauteur");

        TextField nomJoueur1 = new TextField();
        nomJoueur1.setPromptText("Nom joueur 1");

        TextField nomJoueur2 = new TextField();
        nomJoueur2.setPromptText("Nom joueur 2");
        CheckBox j2ia = new CheckBox();

        grid.add(new Label("Largeur:"), 0, 0);
        grid.add(largeur, 1, 0);
        grid.add(new Label("Hauteur:"), 0, 1);
        grid.add(hauteur, 1, 1);
        grid.add(new Label("Nom joueur 1:"), 0, 2);
        grid.add(nomJoueur1, 1, 2);
        grid.add(new Label("Nom joueur 2:"), 0, 3);
        grid.add(nomJoueur2, 1, 3);
        grid.add(new Label("IA?"), 2, 3);
        grid.add(j2ia, 3, 3);
        grid.add(fromFilename, 0, 4, 2, 1);

        dialog.getDialogPane().setContent(grid);

        Platform.runLater(largeur::requestFocus);

        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == ButtonType.OK) {
                this.nomJoueur1 = nomJoueur1.getText();
                this.nomJoueur2 = nomJoueur2.getText();
                this.j2ia = j2ia.isSelected();

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

                this.j2ia = j2ia.isSelected();
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

        if (openFromFile)
            jeu = new Jeu(terrainFilename);
        else {
            jeu = new Jeu(hauteur, largeur);

            joueurs[0] = new Joueur(jeu, nomJoueur1);
            if (j2ia) {
                joueurs[1] = new IA(jeu, nomJoueur2, IA.DIFF_MOYEN);
            } else {
                joueurs[1] = new Joueur(jeu, nomJoueur2);
            }

            for (Joueur j : joueurs) jeu.addJoueur(j);
        }

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
        return jeu.getTerrain();
    }

    public Jeu getJeu() {
        return jeu;
    }
}
