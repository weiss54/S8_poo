package fr.ul.miage.weiss.s8_projet_poo;

import javafx.beans.property.BooleanProperty;
import javafx.fxml.FXML;
import javafx.scene.image.ImageView;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

public class FuiteController {

    @FXML
    private TextField input_fuite;

    @FXML
    private Text text_num, text_fuite;

    @FXML
    private ImageView image_fuite, image_reparation;

    private Fuite fuite;
    private BooleanProperty simulation;

    public FuiteController(Fuite fuite, BooleanProperty simulation) {
        this.fuite = fuite;
        this.simulation = simulation;
    }

    @FXML
    public void initialize() {
        text_num.setText("N°" + Fuite.getNbFuites());
        input_fuite.setText(String.valueOf(fuite.getDebit()));
        text_fuite.setText(String.valueOf(fuite.getDebit()));
        input_fuite.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) {
                input_fuite.setText(newValue.replaceAll("[^\\d]", ""));
            }
            if (!newValue.isEmpty()) {
                fuite.setDebit(Integer.parseInt(input_fuite.getText()));
            }
        });
        input_fuite.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue) {
                if (input_fuite.getText().isEmpty()) {
                    fuite.setDebit(10);
                    text_fuite.setText("10");
                }
            }
        });
        fuite.debitProperty().addListener((observable, oldValue, newValue) -> {
            text_fuite.setText(observable.getValue().toString());
        });
        fuite.reparerProperty().addListener((observable, oldValue, newValue) -> {
            image_reparation.setVisible(newValue);
        });
        image_fuite.onMouseClickedProperty().set(event -> {
            if (!fuite.isReparer() && simulation.get()) fuite.reparer();
        });
        visibiliteReparation(false);
        visibiliteSimulation(false);
    }

    public void suppression() {
        this.fuite.suppression();
    }

    public void visibiliteReparation(boolean b) {
        image_reparation.setVisible(b);
    }

    public void visibiliteSimulation(boolean b) {
        text_fuite.setVisible(b);
        input_fuite.setVisible(!b);
    }

}
