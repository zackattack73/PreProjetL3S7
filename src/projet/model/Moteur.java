package projet.model;

import projet.obs.Observable;
import projet.obs.Observer;

import java.util.ArrayList;

/**
 * Package ${PACKAGE} / Project PreProjetL3S7.
 * Date 2017 04 28.
 * Created by Nico (17:05).
 */
public abstract class Moteur implements Observable {
    private ArrayList<Observer> observers = new ArrayList<>();

    @Override
    public void addObserver(Observer observer) {
        if (!this.observers.contains(observer)) observers.add(observer);
    }

    @Override
    public void removerObserver() {
        for (int i = 0; i < this.observers.size(); i++) observers.remove(0);
    }

    @Override
    public void notifyObserver() {
        for (Observer observer : this.observers) observer.update();
    }
}
