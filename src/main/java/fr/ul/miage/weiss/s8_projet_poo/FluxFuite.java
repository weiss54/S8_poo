package fr.ul.miage.weiss.s8_projet_poo;

import javafx.concurrent.ScheduledService;
import javafx.concurrent.Task;

import java.util.logging.Logger;

public class FluxFuite extends ScheduledService<Baignoire> {

    private Logger LOGGER = Logger.getLogger(FluxFuite.class.getName());
    private Baignoire baignoire;
    private Fuite fuite;

    public FluxFuite(Baignoire baignoire, Fuite fuite) {
        super();
        this.baignoire = baignoire;
        this.fuite = fuite;
    }

    @Override
    protected Task<Baignoire> createTask() {
        return new Task<Baignoire>() {
            @Override
            protected Baignoire call() throws Exception {
                LOGGER.info("[FluxFuite] Fuite de " + fuite.getDebit() + "L");
                baignoire.vider(fuite.getDebit());
                return baignoire;
            }
        };
    }

}
