package projet.ctrl;

import javafx.concurrent.Service;
import javafx.concurrent.Task;
import projet.Projet;
import projet.model.*;
import projet.view.GaufreView;
import projet.view.PointButton;

public class GaufreCtrl {
    private class SleeperService extends Service<Void> {
        @Override
        protected Task<Void> createTask() {
            return new Task<Void>() {
                @Override
                protected Void call() throws Exception {
                    try {
                        Double tps = IA.TEMPS_LATENCE_BASE + Math.ceil(Math.random() * (IA.TEMPS_LATENCE_VAR * 2)) - IA.TEMPS_LATENCE_VAR;
                        Thread.sleep(tps.intValue());
                    } catch (InterruptedException ignored) { }
                    return null;
                }
            };
        }

        @Override
        protected void succeeded() {
            jeu.jouer(((IA)jeu.getJoueurActuel()).action(terrain.getPointsDispo()));
            projet.hideTourIa();
        }
    }
    private SleeperService sleeperService = new SleeperService();

    private final Projet projet;
    private final Terrain terrain;

    private final GaufreView gaufreView;

    private final Jeu jeu;
    
    public GaufreCtrl(Projet projet) {
        this.projet = projet;
        this.jeu = projet.getJeu();
        this.terrain = projet.getTerrain();

        this.gaufreView = new GaufreView(this);
    }

    public Jeu getJeu() {
        return this.jeu;
    }

    public GaufreView getGaufreView() {
        return this.gaufreView;
    }

    public void traiterClick(PointButton pointButton) {
        jeu.jouer(pointButton.getPoint());

        if (jeu.getJoueurActuel() instanceof IA) {
            sleeperService.restart();
            this.projet.showTourIa();
        }
    }

    public void partieTerminee() {
        this.projet.partieTerminee();
    }
}
