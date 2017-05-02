package projet;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import projet.ctrl.ActionsCtrl;
import projet.ctrl.AffichageCtrl;
import projet.ctrl.GaufreCtrl;
import projet.model.IA;
import projet.model.Jeu;
import projet.model.Joueur;
import projet.model.Terrain;
import projet.view.ParametresDialog;
import projet.view.TourIA;

import java.util.Optional;

public class Projet extends Application {
    public Stage primaryStage;
    public StackPane root;
    private BorderPane rootPane;
    private TourIA tourIa;

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

    public int hauteur;
    public int largeur;
    public String nomJoueur1;
    public String nomJoueur2;

    public int iaDifficulte;
    public boolean openFromFile;
    public String terrainFilename;

    private ParametresDialog parametresDialog;

    private boolean parametres() {
        Optional<Boolean> result = parametresDialog.getResult();
        return (result.orElse(false));
    }

    public void showTourIa() {
        this.tourIa.show();
    }

    public void hideTourIa() {
        this.tourIa.hide();
    }

    private void initModel() {
        joueurs = new Joueur[2];
        if (openFromFile) {
            jeu = new Jeu(terrainFilename);

            largeur = jeu.getTerrain().largeur;
            hauteur = jeu.getTerrain().hauteur;
        } else {
            jeu = new Jeu(hauteur, largeur);

            joueurs[0] = new Joueur(jeu, nomJoueur1);
            if (iaDifficulte > 0) {
                joueurs[1] = new IA(jeu, nomJoueur2, iaDifficulte);
            } else {
                joueurs[1] = new Joueur(jeu, nomJoueur2);
            }

            for (Joueur j : joueurs) jeu.addJoueur(j);
        }
    }

    private void initController() {
        this.gaufreCtrl = new GaufreCtrl(this);
        this.actionsCtrl = new ActionsCtrl(this);
        this.affichageCtrl = new AffichageCtrl(this);
    }

    private void initView() {
        root = new StackPane();
        rootPane = new BorderPane();
        tourIa = new TourIA(this);

        root.getChildren().add(rootPane);

        rootPane.setCenter(this.gaufreCtrl.getGaufreView());
        rootPane.setLeft(this.actionsCtrl.getActionsView());
        rootPane.setTop(this.affichageCtrl.getAffichageView());

        Scene scene = new Scene(root, largeur * 80 + 120, hauteur * 35 + 50);
        scene.getStylesheets().add(Projet.class.getResource("Projet.css").toExternalForm());

        primaryStage.setTitle("Projet GaufreView S7 - PreProd");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public void newGame() {
        boolean ok = parametres();
        if (!ok) return;

        if (!this.verifierPrerequis()) this.exit();

        this.initModel();
        this.initController();
        this.initView();
    }

    public void partieTerminee() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Partie terminée!");
        alert.setHeaderText(null);
        alert.setContentText("Le perdant (celui qui a découvert le poison) est " + this.jeu.getJoueurActuel().getNom());

        alert.showAndWait();
        this.exit();
    }

    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        this.parametresDialog = new ParametresDialog(this);

        newGame();
    }

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

    private boolean verifierPrerequis() {
        return !(   hauteur < HAUTEUR_MIN
                ||  largeur < LARGEUR_MIN);
    }

    public void exit() {
        Platform.exit();
    }
}
