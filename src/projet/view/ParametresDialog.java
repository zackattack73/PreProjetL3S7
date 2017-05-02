package projet.view;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import projet.Projet;
import projet.model.IA;

import java.io.File;
import java.util.Optional;

public class ParametresDialog {
    private final Projet projet;
    private final Dialog<Boolean> dialog = new Dialog<>();
    private final FileChooser fileChooser = new FileChooser();

    private GridPane gridPane;
    private Button fromFilename;
    private Label fileSelected;
    private TextField largeur, hauteur, nomJoueur1, nomJoueur2;

    private ObservableList<String> options = FXCollections.observableArrayList("Non", "Facile", "Moyen", "Difficile");
    private ComboBox<String> iaDifficulte;

    public ParametresDialog(Projet projet) {
        this.projet = projet;

        init();
    }

    private void init() {
        fileChooser.setInitialDirectory(new File("."));

        dialog.setTitle("Accueil");
        dialog.setHeaderText("Changer les paramètres");

        dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);

        gridPane = new GridPane();
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        gridPane.setPadding(new Insets(20, 150, 10, 10));

        fileSelected = new Label("Aucun fichier sélectionné");
        fileSelected.setTextFill(Color.RED);

        fromFilename = new Button("Choisir un fichier de sauvegarde");
        fromFilename.setMaxWidth(Double.MAX_VALUE);
        fromFilename.setOnAction(event -> {
            File file = fileChooser.showOpenDialog(dialog.getOwner());
            if (file != null) {
                String absfn = file.getAbsolutePath();
                setTerrainFilename(absfn);
                setOpenFromFile(true);

                fileSelected.setText("Sauvegarde: " + absfn);
                fileSelected.setTextFill(Color.GREEN);
            }
        });

        largeur = new TextField();
        largeur.setPromptText("Largeur");

        hauteur = new TextField();
        hauteur.setPromptText("Hauteur");

        nomJoueur1 = new TextField();
        nomJoueur1.setPromptText("Nom joueur 1");

        nomJoueur2 = new TextField();
        nomJoueur2.setPromptText("Nom joueur 2");

        iaDifficulte = new ComboBox<>(options);
        iaDifficulte.setValue("Non");

        gridPane.add(new Label("Largeur:"), 0, 0);
        gridPane.add(largeur, 1, 0);
        gridPane.add(new Label("Hauteur:"), 0, 1);
        gridPane.add(hauteur, 1, 1);
        gridPane.add(new Label("Nom joueur 1:"), 0, 2);
        gridPane.add(nomJoueur1, 1, 2);
        gridPane.add(new Label("Nom joueur 2:"), 0, 3);
        gridPane.add(nomJoueur2, 1, 3);
        gridPane.add(new Label("IA"), 2, 3);
        gridPane.add(iaDifficulte, 3, 3);
        gridPane.add(fromFilename, 0, 4, 2, 1);
        gridPane.add(fileSelected, 2, 4, 2, 1);

        dialog.getDialogPane().setContent(gridPane);

        Platform.runLater(largeur::requestFocus);

        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == ButtonType.OK) {
                setNomJoueur1(nomJoueur1.getText());
                setNomJoueur2(nomJoueur2.getText());
                switch (iaDifficulte.getValue()) {
                    case "Non":
                        setIaDifficulte(0);
                        break;
                    case "Facile":
                        setIaDifficulte(IA.DIFF_FACILE);
                        break;
                    case "Moyen":
                        setIaDifficulte(IA.DIFF_MOYEN);
                        break;
                    case "Difficile":
                        setIaDifficulte(IA.DIFF_DIFFICILE);
                        break;
                    default:
                        setIaDifficulte(0);
                }

                try {
                    setHauteur(Integer.parseInt(hauteur.getText()));
                } catch (NumberFormatException e) {
                    setHauteur(10);
                }

                try {
                    setLargeur(Integer.parseInt(largeur.getText()));
                } catch (NumberFormatException e) {
                    setLargeur(10);
                }

                return true;
            }

            return false;
        });
    }

    public Optional<Boolean> getResult() {
        return dialog.showAndWait();
    }

    private void setNomJoueur1(String nomJoueur1) {
        this.projet.nomJoueur1 = nomJoueur1;
    }

    private void setNomJoueur2(String nomJoueur2) {
        this.projet.nomJoueur2 = nomJoueur2;
    }

    private void setLargeur(int largeur) {
        this.projet.largeur = largeur;
    }

    private void setHauteur(int hauteur) {
        this.projet.hauteur = hauteur;
    }

    private void setIaDifficulte(int iaDifficulte) {
        this.projet.iaDifficulte = iaDifficulte;
    }

    private void setOpenFromFile(boolean openFromFile) {
        this.projet.openFromFile = openFromFile;
    }

    private void setTerrainFilename(String terrainFilename) {
        this.projet.terrainFilename = terrainFilename;
    }
}
