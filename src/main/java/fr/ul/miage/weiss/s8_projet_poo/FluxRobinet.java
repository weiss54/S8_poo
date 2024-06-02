package fr.ul.miage.weiss.s8_projet_poo;

import javafx.concurrent.ScheduledService;
import javafx.concurrent.Task;
import java.util.logging.Logger;

/**
 * Classe représentant un flux de robinet.
 * Cette classe hérite de la classe ScheduledService de JavaFX.
 * Elle permet de simuler un flux d'eau provenant d'un robinet.
 * @author Lucas WEISS
 */
public class FluxRobinet extends ScheduledService<Baignoire> {

    /**
     * Logger de la classe.
     */
    private Logger LOGGER = Logger.getLogger(FluxRobinet.class.getName());

    /**
     * Baignoire de la simulation.
     */
    private Baignoire baignoire;

    /**
     * Robinet à laquelle est reliée le flux.
     */
    private Robinet robinet;

    /**
     * Constructeur de la classe.
     * @param baignoire
     * @param robinet
     */
    public FluxRobinet(Baignoire baignoire, Robinet robinet) {
        super();
        this.baignoire = baignoire;
        this.robinet = robinet;
    }

    /**
     * Méthode permettant de créer une tâche. Elle est appelée à chaque période de la simulation.
     * @return
     */
    @Override
    protected Task<Baignoire> createTask() {
        return new Task<Baignoire>() {
            @Override
            protected Baignoire call() throws Exception {
                if (!baignoire.estRemplie()) {
                    LOGGER.info("[FluxRobinet] Remplissage de " + robinet.getDebit() + "L");
                    baignoire.remplir(robinet.getDebit());
                }
                return baignoire;
            }
        };
    }
}
