package fr.ul.miage.weiss.s8_projet_poo;

import javafx.beans.property.*;
import javafx.concurrent.ScheduledService;
import javafx.concurrent.Task;
import javafx.util.Duration;

import java.io.File;
import java.util.List;
import java.util.Map;
import java.util.Stack;
import java.util.logging.Logger;

public class Modele {

    private static final Duration PERIODE_SIMULATION = Duration.seconds(1);
    private final Logger LOGGER = Logger.getLogger(Modele.class.getName());
    private final BooleanProperty simulationEnCours;
    private final BooleanProperty simulationComplete;

    private final Stack<Robinet> robinets;
    private final Stack<Fuite> fuites;
    private final Baignoire baignoire;
    private List<ScheduledService> listThread;
    private IntegerProperty dureeSimulation;
    private StringProperty messageErreur;

    public Modele() {
        LOGGER.info("[Modèle] Initialisation du modèle");
        this.simulationEnCours = new SimpleBooleanProperty(false);
        this.simulationComplete = new SimpleBooleanProperty(false);
        this.messageErreur = new SimpleStringProperty("");
        this.baignoire = new Baignoire();
        this.robinets = new Stack<>();
        this.fuites = new Stack<>();
        this.listThread = new Stack<>();
        this.dureeSimulation = new SimpleIntegerProperty(0);
    }

    public void demarrerSimulation() {
        if (simulationLancable()) {
            LOGGER.info("[Modèle] Démarrage de la simulation");
            this.dureeSimulation.set(0);
            this.simulationEnCours.set(true);
            this.baignoire.demarrerSimulation();
            //TODO a completer proprement en se basant sur le projet
            for (Robinet r:robinets) {
                FluxRobinet fluxRobinet = new FluxRobinet(baignoire, r);
                fluxRobinet.setPeriod(PERIODE_SIMULATION);
                fluxRobinet.setDelay(PERIODE_SIMULATION);
                listThread.add(fluxRobinet);
            }
            for (Fuite f:fuites) {
                FluxFuite fluxFuite = new FluxFuite(baignoire, f);
                fluxFuite.setPeriod(PERIODE_SIMULATION);
                fluxFuite.setDelay(PERIODE_SIMULATION);
                listThread.add(fluxFuite);
            }
            this.lancerCompteARebours();
            for (ScheduledService thread : listThread) {
                thread.start();
            }
        } else {
            LOGGER.warning("[Modèle] Impossible de démarrer la simulation, aucun robinet n'a été ajouté");
            this.messageErreur.set("Impossible de démarrer la simulation, aucun robinet n'a été ajouté");
        }
    }

    private void lancerCompteARebours() {
        ScheduledService t = new ScheduledService() {
            @Override
            protected Task createTask() {
                return new Task() {
                    @Override
                    protected Object call() throws Exception {
                        synchronized (this) {
                            if (simulationEnCours.get() && !baignoire.estRemplie() ) {
                                baignoire.enregistrerVolume(dureeSimulation.get());
                                if (baignoire.compareRemplissageCapacite()) {
                                    LOGGER.info("[Modèle] Fin de la simulation");
                                    baignoire.setEstRemplie(true);
                                    simulationComplete.set(true);
                                } else {
                                    dureeSimulation.set(dureeSimulation.get() + 1);
                                }
                            }
                            return null;
                        }
                    }
                };
            }
        };
        t.setPeriod(PERIODE_SIMULATION);
        t.setDelay(PERIODE_SIMULATION);
        listThread.add(t);
    }

    public void arreterSimulation() {
        LOGGER.info("[Modèle] Arrêt de la simulation");
        //TODO a completer proprement
        // On arrête tous les threads qui gèrent les robinets et les fuites
        for (ScheduledService thread : listThread) {
            thread.cancel();
        }
        // Une fois les threads arrêtés, on réinitialise les listes des threads
        listThread = new Stack<>();
        this.simulationEnCours.set(false);
        this.simulationComplete.set(false);
        // Comme les fuites peuvent être réparées, on doit les réinitialiser
        for (Fuite f: fuites) {
            f.finSimulation();
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

    public void exporterDonnees(File chemin) {
        baignoire.exporterDonnees(chemin);
    }

    public BooleanProperty getSimulationEnCours() {
        return simulationEnCours;
    }

    public IntegerProperty getPropertyVolumeRempliBaignoire() {
        return baignoire.volumeRempliProperty();
    }

    public int getCapaciteBaignoire() {
        return this.baignoire.getCapacite();
    }

    public void setCapaciteBaignoire(int i) {
        this.baignoire.setCapacite(i);
    }

    public boolean simulationLancable() {
        return !robinets.isEmpty();
    }

    public StringProperty messageErreurProperty() {
        return messageErreur;
    }

    public IntegerProperty dureeSimulationProperty() {
        return dureeSimulation;
    }

    public Map<Integer, Integer> getHistorique() {
        return baignoire.getHistorique();
    }

    public IntegerProperty volumeEauTotalRobinetProperty() {
        return baignoire.volumeEauTotalRobinetProperty();
    }

    public IntegerProperty volumeEauTotalFuiteProperty() {
        return baignoire.volumeEauTotalFuiteProperty();
    }

    public BooleanProperty simulationCompleteProperty() {
        return simulationComplete;
    }

}
