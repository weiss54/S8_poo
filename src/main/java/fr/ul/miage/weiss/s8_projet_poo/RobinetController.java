package fr.ul.miage.weiss.s8_projet_poo;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;


public class RobinetController {


    @FXML
    private TextField input_robinet;

    @FXML
    private Text text_num, text_robinet;

    private Robinet robinet;

    public RobinetController(Robinet r) {
        this.robinet = r;
    }

    @FXML
    public void initialize() {
        text_num.setText("N°" + Robinet.getNbRobinets());
        input_robinet.setText(String.valueOf(robinet.getDebit()));
        text_robinet.setText(String.valueOf(robinet.getDebit()));
        input_robinet.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) {
                input_robinet.setText(newValue.replaceAll("[^\\d]", ""));
            }
            if (!newValue.isEmpty()) {
                robinet.setDebit(Integer.parseInt(input_robinet.getText()));
            }
        });
        input_robinet.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue) {
                if (input_robinet.getText().isEmpty()) {
                    robinet.setDebit(10);
                    text_robinet.setText("10");
                }
            }
        });
        robinet.debitProperty().addListener((observable, oldValue, newValue) -> {
            text_robinet.setText(observable.getValue().toString());
        });
        visibiliteSimulation(false);
    }

    public void visibiliteSimulation(boolean b) {
        text_robinet.setVisible(b);
        input_robinet.setVisible(!b);
    }

    public void suppression() {
        this.robinet.suppression();
    }


}
