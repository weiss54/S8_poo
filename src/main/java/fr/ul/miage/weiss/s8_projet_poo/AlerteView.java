package fr.ul.miage.weiss.s8_projet_poo;

import javafx.scene.control.Alert;

/**
 * Classe permettant d'afficher une alerte.
 * Cette classe hérite de la classe Alert de JavaFX.
 * @author Lucas WEISS
 */
public class AlerteView extends Alert {

    /**
     * Constructeur de la classe.
     * @param message Message à afficher dans l'alerte.
     */
    public AlerteView(String message) {
        super(AlertType.INFORMATION);
        setTitle("Information");
        setHeaderText(null);
        setContentText(message);
        showAndWait();
    }

}
