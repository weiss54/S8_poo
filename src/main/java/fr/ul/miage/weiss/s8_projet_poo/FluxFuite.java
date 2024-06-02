package fr.ul.miage.weiss.s8_projet_poo;

import javafx.concurrent.ScheduledService;
import javafx.concurrent.Task;
import java.util.logging.Logger;

/**
 * Classe représentant un flux de fuite.
 * Cette classe hérite de la classe ScheduledService de JavaFX.
 * @author Lucas WEISS
 */
public class FluxFuite extends ScheduledService<Baignoire> {

    /**
     * Logger de la classe.
     */
    private Logger LOGGER = Logger.getLogger(FluxFuite.class.getName());

    /**
     * Baignoire de la simulation
     */
    private Baignoire baignoire;

    /**
     * Fuite à laquelle est reliée le flux.
     */
    private Fuite fuite;

    /**
     * Constructeur de la classe.
     * @param baignoire
     * @param fuite
     */
    public FluxFuite(Baignoire baignoire, Fuite fuite) {
        super();
        this.baignoire = baignoire;
        this.fuite = fuite;
    }

    /**
     * Méthode permettant de créer une tâche. Elle est appelée à chaque période de la simulation.
     * @return Tâche créée.
     */
    @Override
    protected Task<Baignoire> createTask() {
        return new Task<Baignoire>() {
            @Override
            protected Baignoire call() throws Exception {
                if (!baignoire.estRemplie()) {
                    LOGGER.info("[FluxFuite] Fuite de " + fuite.getDebit() + "L");
                    baignoire.vider(fuite.getDebit());
                }
                return baignoire;
            }
        };
    }

}
