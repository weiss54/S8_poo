package fr.ul.miage.weiss.s8_projet_poo;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

/**
 * Classe représentant une baignoire.
 * @author Lucas WEISS
 */
public class Baignoire {

    /**
     * Logger de la classe.
     */
    private Logger LOGGER = Logger.getLogger(Baignoire.class.getName());

    /**
     * Capacité de la baignoire.
     */
    private IntegerProperty capacite;

    /**
     * Volume rempli de la baignoire.
     */
    private IntegerProperty volumeRempli;

    /**
     * Volume d'eau ajouté par les robinets.
     */
    private IntegerProperty volumeEauRobinet;

    /**
     * Volume d'eau retiré par les fuites.
     */
    private IntegerProperty volumeEauFuite;

    /**
     * Booléen qui indique si la baignoire est remplie.
     */
    private boolean estRemplie;

    /**
     * Historique de la baignoire.
     * Clé: temps de la simulation.
     * Valeur: volume de la baignoire.
     */
    private Map<Integer, Integer> historique;

    /**
     * Constructeur par défaut.
     */
    public Baignoire() {
        this.capacite = new SimpleIntegerProperty(100);
        this.volumeRempli = new SimpleIntegerProperty(0);
        this.historique = new HashMap<>();
        this.estRemplie = false;
        this.volumeEauRobinet = new SimpleIntegerProperty(0);
        this.volumeEauFuite = new SimpleIntegerProperty(0);
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
        this.volumeEauRobinet = new SimpleIntegerProperty(0);
        this.volumeEauFuite = new SimpleIntegerProperty(0);
    }

    /**
     * Permet de remplir la baignoire.
     * Méthode synchronisée pour éviter les problèmes de concurrence.
     * @param volume volume à ajouter à la baignoire.
     */
    public synchronized void remplir(int volume) {
        if (volume > 0 && this.volumeRempli.get() < this.capacite.get()) {
            this.volumeRempli.set(this.volumeRempli.get()+volume);
            this.volumeEauRobinet.set(this.volumeEauRobinet.get()+volume);
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
        if (volume > 0 && this.volumeRempli.get() > 0) {
            this.volumeRempli.set(this.volumeRempli.get()-volume);
            this.volumeEauFuite.set(this.volumeEauFuite.get()+volume);
            if (this.volumeRempli.get() < 0) {
                this.volumeRempli.set(0);
            }
        }
    }

    /**
     * Permet d'enregistrer le volume de la baignoire pour un temps donnée en paramètre
     * @param duree temps de la simulation.
     */
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

    /**
     * Permet de récupérer la capacité de la baignoire.
     * @return la capacité de la baignoire.
     */
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

    /**
     * Permet de récupérer le volume rempli de la baignoire.
     * @return le volume rempli de la baignoire.
     */
    public IntegerProperty volumeRempliProperty() {
        return volumeRempli;
    }

    /**
     * Indique a la baignoire de démarrer une simulation.
     */
    public void demarrerSimulation() {
        this.estRemplie = false;
        this.historique.clear();
        this.volumeEauRobinet.set(0);
        this.volumeEauFuite.set(0);
        this.volumeRempli.set(0);
        enregistrerVolume(0);
    }

    /**
     * Permet de définir la capacité de la baignoire.
     * @param i capacité de la baignoire.
     */
    public void setCapacite(int i) {
        this.capacite.set(i);
    }

    /**
     * Permet de comparer le volume rempli de la baignoire avec sa capacité.
     * @return true si le volume rempli est égal à la capacité, false sinon.
     */
    public boolean compareRemplissageCapacite() {
        return this.volumeRempli.get() == this.capacite.get();
    }

    /**
     * Permet de récupérer l'historique de la baignoire.
     * @return l'historique de la baignoire, sous forme de Map: le temps en clé et le volume en valeur.
     */
    public Map<Integer, Integer> getHistorique() {
        return historique;
    }

    /**
     * Permet de savoir si la baignoire est remplie ou non.
     * @return true si la baignoire est remplie, false sinon.
     */
    public boolean estRemplie() {
        return this.estRemplie;
    }

    /**
     * Permet de définir si la baignoire est remplie ou non.
     * @param b défini si la baignoire est remplie ou non.
     */
    public void setEstRemplie(boolean b) {
        this.estRemplie = b;
    }

    /**
     * Permet d'exporter les données de la baignoire dans un fichier .csv à l'emplacement donné.
     * @param chemin chemin où enregistrer le fichier.
     */
    public void exporterDonnees(File chemin) {
        try (FileWriter writer = new FileWriter(chemin)) {
            writer.append("Duree (seconde); Volume rempli de la baignoire (litre)\n");
            for (Map.Entry<Integer, Integer> entry : historique.entrySet()) {
                writer.append(String.valueOf(entry.getKey()))
                        .append(';')
                        .append(String.valueOf(entry.getValue()))
                        .append('\n');
            }
            writer.flush();
            LOGGER.info("Fichier exporté: " + chemin.getAbsolutePath());
        } catch (IOException e) {
            LOGGER.severe("Erreur detectée: " + e.getMessage());
        }
    }

    /**
     * Permet de récupérer le volume total d'eau ajouté par les robinets.
     * @return le volume total d'eau ajouté par les robinets.
     */
    public IntegerProperty volumeEauTotalRobinetProperty() {
        return this.volumeEauRobinet;
    }

    /**
     * Permet de récupérer le volume total d'eau retiré par les fuites.
     * @return le volume total d'eau retiré par les fuites.
     */
    public IntegerProperty volumeEauTotalFuiteProperty() {
        return this.volumeEauFuite;
    }
}
