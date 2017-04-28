package projet;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.logging.Logger;

public class Projet extends Application {
    private final static Logger LOGGER = Logger.getLogger(Projet.class.getName());

    @Override
    public void start(Stage primaryStage) {
        BorderPane root = new BorderPane();
        GridPane gridPane = new GridPane();
        VBox vBox = new VBox();

        root.setCenter(gridPane);
        root.setLeft(vBox);
        
        Scene scene = new Scene(root, 850, 260);
        
        primaryStage.setTitle("Projet Gaufre S7 - PreProd");
        primaryStage.setScene(scene);
        primaryStage.show();

        Moteur moteur = new Moteur();
        GaufreCtrl c = new GaufreCtrl(gridPane, moteur);
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
