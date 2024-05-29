package fr.ul.miage.weiss.s8_projet_poo;

import javafx.scene.control.Alert;

public class AlerteView extends Alert {

    public AlerteView(String message) {
        super(AlertType.INFORMATION);
        setTitle("Information");
        setHeaderText(null);
        setContentText(message);
        showAndWait();
    }

}
