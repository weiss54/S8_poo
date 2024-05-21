package fr.ul.miage.weiss.s8_projet_poo;

import javafx.concurrent.ScheduledService;
import javafx.concurrent.Task;

import java.util.logging.Logger;

public class FluxRobinet extends ScheduledService<Baignoire> {

    private Logger LOGGER = Logger.getLogger(FluxRobinet.class.getName());
    private Baignoire baignoire;
    private Robinet robinet;

    public FluxRobinet(Baignoire baignoire, Robinet robinet) {
        super();
        this.baignoire = baignoire;
        this.robinet = robinet;
    }

    @Override
    protected Task<Baignoire> createTask() {
        return new Task<Baignoire>() {
            @Override
            protected Baignoire call() throws Exception {
                LOGGER.info("[FluxRobinet] Remplissage de " + robinet.getDebit() + "L");
                baignoire.remplir(robinet.getDebit());
                return baignoire;
            }
        };
    }
}
