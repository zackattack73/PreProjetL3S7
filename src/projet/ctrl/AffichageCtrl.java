package projet.ctrl;

import projet.Projet;
import projet.model.Jeu;
import projet.view.AffichageView;

public class AffichageCtrl {
    private final AffichageView affichageView;
    private final Projet projet;
    private final Jeu jeu;

    public AffichageCtrl(Projet projet) {
        this.projet = projet;
        this.jeu = projet.getJeu();

        this.affichageView = new AffichageView(this);
    }

    public Jeu getJeu() {
        return this.jeu;
    }

    public AffichageView getAffichageView() {
        return this.affichageView;
    }
}
