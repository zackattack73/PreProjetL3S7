package projet.obs;

/**
 * Package ${PACKAGE} / Project PreProjetL3S7.
 * Date 2017 04 28.
 * Created by Nico (17:05).
 */
public interface Observable {
    public void addObserver(Observer observer);
    public void removerObserver();
    public void notifyObserver();
}
