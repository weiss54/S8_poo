package fr.ul.miage.weiss.s8_projet_poo;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.concurrent.ScheduledService;
import javafx.util.Duration;

import java.util.List;
import java.util.Stack;
import java.util.logging.Logger;

public class Modele {

    private static final Duration PERIODE_SIMULATION = Duration.seconds(1);
    private Logger LOGGER = Logger.getLogger(Modele.class.getName());
    private BooleanProperty simulationEnCours;

    private Stack<Robinet> robinets;
    private Stack<Fuite> fuites;
    private Baignoire baignoire;
    List<ScheduledService> listThread;

    public Modele() {
        LOGGER.info("[Modèle] Initialisation du modèle");
        this.simulationEnCours = new SimpleBooleanProperty(false);
        this.baignoire = new Baignoire();
        this.robinets = new Stack<>();
        this.fuites = new Stack<>();
        this.listThread = new Stack<>();
    }

    public void demarrerSimulation() {
        LOGGER.info("[Modèle] Démarrage de la simulation");
        this.simulationEnCours.set(true);
        this.baignoire.demarrerSimulation();
        //TODO a completer proprement
        for (Robinet r:robinets) {
            FluxRobinet fluxRobinet = new FluxRobinet(baignoire, r);
            fluxRobinet.setPeriod(PERIODE_SIMULATION);
            fluxRobinet.start();
            listThread.add(fluxRobinet);
        }
        for (Fuite f:fuites) {
            FluxFuite fluxFuite = new FluxFuite(baignoire, f);
            fluxFuite.setPeriod(PERIODE_SIMULATION);
            fluxFuite.start();
            listThread.add(fluxFuite);
        }
    }

    public void arreterSimulation() {
        LOGGER.info("[Modèle] Arrêt de la simulation");
        this.simulationEnCours.set(false);
        //TODO a completer proprement
        for (ScheduledService thread : listThread) {
            thread.cancel();
        }
    }

    public Robinet ajouterRobinet() {
        if (Robinet.isAjoutPossible()) {
            Robinet robinet = new Robinet();
            robinets.add(robinet);
            return robinet;
        }
        return null;
    }

    public Fuite ajouterFuite() {
        if (Fuite.isAjoutPossible()) {
            Fuite fuite = new Fuite();
            fuites.add(fuite);
            return fuite;
        }
        return null;
    }

    public boolean supprimerRobinet() {
        if (Robinet.isSuppressionPossible()) {
            robinets.pop();
            return true;
        }
        return false;
    }


    public boolean supprimerFuite() {
        if (Fuite.isSuppressionPossible()) {
            fuites.pop();
            return true;
        }
        return false;
    }

    public BooleanProperty getSimulationEnCours() {
        return simulationEnCours;
    }

    public IntegerProperty getPropertyVolumeRempliBaignoire() {
        return baignoire.volumeRempliProperty();
    }
}
