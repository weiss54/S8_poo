package fr.ul.miage.weiss.s8_projet_poo;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;

import java.util.HashMap;
import java.util.Map;

/**
 * Classe représentant une baignoire.
 * @author Lucas WEISS
 */
public class Baignoire {

    /**
     * Capacité de la baignoire.
     */
    private IntegerProperty capacite;

    /**
     * Volume rempli de la baignoire.
     */
    private IntegerProperty volumeRempli;

    private boolean estRemplie;

    private Map<Integer, Integer> historique;

    /**
     * Constructeur par défaut.
     */
    public Baignoire() {
        this.capacite = new SimpleIntegerProperty(100);
        this.volumeRempli = new SimpleIntegerProperty(0);
        this.historique = new HashMap<>();
        this.estRemplie = false;
    }

    /**
     * Constructeur avec un parametre.
     * @param capacite capacité de la baignoire.
     */
    public Baignoire(int capacite) {
        this.capacite = new SimpleIntegerProperty(capacite);
        this.volumeRempli = new SimpleIntegerProperty(0);
        this.historique = new HashMap<>();
        this.estRemplie = false;
    }

    /**
     * Permet de remplir la baignoire.
     * Méthode synchronisée pour éviter les problèmes de concurrence.
     * @param volume volume à ajouter à la baignoire.
     */
    public synchronized void remplir(int volume) {
        if (volume > 0) {
            this.volumeRempli.set(this.volumeRempli.get()+volume);
            if (this.volumeRempli.get() > this.capacite.get()) {
                this.volumeRempli.set(this.capacite.get());
            }
        }
    }

    /**
     * Permet de vider la baignoire.
     * Méthode synchronisée pour éviter les problèmes de concurrence.
     * @param volume volume à retirer de la baignoire.
     */
    public synchronized void vider(int volume) {
        if (volume > 0) {
            this.volumeRempli.set(this.volumeRempli.get()-volume);
            if (this.volumeRempli.get() < 0) {
                this.volumeRempli.set(0);
            }
        }
    }

    public void enregistrerVolume(int duree) {
        this.historique.put(duree, this.volumeRempli.get());
    }

    /**
     * Permet de récupérer la capacité de la baignoire.
     * @return la capacité de la baignoire.
     */
    public int getCapacite() {
        return capacite.get();
    }

    public IntegerProperty capaciteProperty() {
        return capacite;
    }

    /**
     * Permet de récupérer le volume rempli de la baignoire.
     * @return le volume rempli de la baignoire.
     */
    public int getVolumeRempli() {
        return volumeRempli.get();
    }

    //TODO javadoc
    public IntegerProperty volumeRempliProperty() {
        return volumeRempli;
    }

    public void demarrerSimulation() {
        this.volumeRempli.set(0);
        this.estRemplie = false;
        this.historique.clear();
        enregistrerVolume(0);
    }

    public void setCapacite(int i) {
        this.capacite.set(i);
    }

    public boolean compareRemplissageCapacite() {
        return this.volumeRempli.get() == this.capacite.get();
    }

    public Map<Integer, Integer> getHistorique() {
        return historique;
    }

    public boolean estRemplie() {
        return this.estRemplie;
    }

    public void setEstRemplie(boolean b) {
        this.estRemplie = b;
    }

}
